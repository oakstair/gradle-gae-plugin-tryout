/**
 * Copyright 2011 Google
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.appengine.codelab;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.codelab.generated.Order;
import com.google.appengine.codelab.generated.OrderBooking;

/**
 * This servlet responds to the request corresponding to orders. The Class
 * places the order.
 * 
 * @author
 */
@SuppressWarnings("serial")
public class OrderServlet extends BaseServlet {

  private static final Logger logger = Logger.getLogger(OrderServlet.class.getCanonicalName());
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  /**
	* Redirect the call to the corresponding method.
	*/
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
    String action = req.getParameter("action");
    if(action.equalsIgnoreCase("process")){
      logger.log(Level.WARNING, "Processing orders");
      int i=0;
      //get Ids for the orders confirmed for processing
      String orderid[] = new String[req.getParameterMap().size()-1];
      while(req.getParameter("id"+i)!=null){
        orderid[i]=req.getParameter("id"+i);
        i++;
      }
      int ordersConfirmed = confirmOrder(orderid);
      resp.getWriter().print(ordersConfirmed+" order(s) have been confirmed for processing");
    }
  }
  
  /**
   * Gets the orders from the Orders datastore and converts them to XML format(marshal) and 
   * put them in the queue for processing.
   * 
   * @param orderid : array of orders that are processed
   * @throws IOException
   */
  public static int confirmOrder(String[] orderid) throws IOException {
	  XmlParser parser = new XmlParser();
	  OrderBooking booking = new OrderBooking();
	  List<Order> orders = new ArrayList<Order>();
	  int order_num = 0;
	  logger.log(Level.INFO, "got the order ids {0} to be processed", orderid);
	  String logMessage ="";
	  try {
	    for(String id : orderid){
	      Key k = KeyFactory.createKey("order", Long.valueOf(id));
	      Entity order = Util.getDatastoreServiceInstance().get(k);
	      Order o = new Order();
	      o.setCustomer(order.getProperty("customerName").toString());
	      o.setItem(order.getProperty("itemName").toString());
	      o.setStatus(order.getProperty("status").toString());
	      o.setQuantity(BigInteger.valueOf(Long.valueOf(order.getProperty("quantity").toString())));
	      o.setUserOrderid(order.getProperty("user_orderid").toString());
	      if(order.getKey().getName() != null)
	        o.setId(Long.valueOf(order.getKey().getName()));
	      else
	        o.setId(Long.valueOf(order.getKey().getId()));
	      orders.add(o);
	      order.setProperty("status", "processing");
	      Util.getDatastoreServiceInstance().put(order);
	      order_num++;
	      logMessage+=k.toString()+" order has been confirmed for processing";
	      //log the message in "logs" entity
	      Util.Logging(logMessage);
	      logMessage="";
	    }
	    booking.setOrder(orders);
	    String xml = parser.marshalXml(booking);
	    addTaskToPullQueue(xml);
	  } catch (Exception e) {
		  logger.log(Level.WARNING, Util.getErrorResponse(e));
		}
		return order_num;
  }

  private static void addTaskToPullQueue(String data) {
    Queue queue = QueueFactory.getQueue("ProcessedOrderQueue");
    // Create Task and insert into PULL Queue
    queue.add(TaskOptions.Builder.withMethod(Method.PULL).payload(data));
  }

	/**
	 * Get the requested orders and the line items in JSON format
	 */
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
    super.doGet(req, resp);
    logger.log(Level.INFO, "Getting orders from listing");
    String status = req.getParameter("q1");
    String id = req.getParameter("q2");
    List<Entity> entities = new ArrayList<Entity>();
    List<Key> keys = new ArrayList<Key>();
    PrintWriter out = resp.getWriter();
    Query q = new Query("order");
    if (status == null || status.equals("")) {
      q.addFilter("status", FilterOperator.EQUAL, "pending");
    } else {
      q.addFilter("status", FilterOperator.EQUAL, status);
    }
    q.addSort("createdTime", SortDirection.DESCENDING);
    PreparedQuery pq = datastore.prepare(q);
    entities = pq.asList(FetchOptions.Builder.withLimit(10000));
    if(id!= null && !"".equals(id)){
      String str_keys[] = id.split(",");
      DatastoreService datastore = Util.getDatastoreServiceInstance(); 
      for(String s : str_keys){
        Key key = KeyFactory.createKey("order", Long.valueOf(s));
        keys.add(key);
      }
      Collection<Entity> en = datastore.get(keys).values();
      entities.retainAll(en); //Retrieve results based on both, Status & ID
    } 
    out.println(Util.writeJSON(entities));
  }
}