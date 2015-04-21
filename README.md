This project uses gradle & gradle-appengine-plugin to build, test and deploy a google example project. 
See http://googcloudlabs.appspot.com/codelabexercise8.html

To build, deploy and test locally: (Assuming you have **git** and **gradle** installed)

1. git clone git@github.com:oakstair/gradle-gae-plugin-tryout.git
1. cd gradle-gae-plugin-tryout
1. gradle appengineRun        
1. In a web browser goto http://localhost:8888
1. Upload the file data/orders1.xml. These orders will show up as pending orders.
1. Select "Pending" orders and confirm some of them. And the will show up as "Processing"
1. Klick "Process Confimrd Orders" to chnage their status to "Processed".


To build, deploy and test on appengine:

1. git clone git@github.com:oakstair/gradle-gae-plugin-tryout.git
1. cd gradle-gae-plugin-tryout
1. Edit ./build.gradle and set appId and appEmail to your own values.
1. Edit src/main/webapp/WEB-INF/appengine-web.xml  Change application to your application name.
1. Edit ajax.util.js line 199 since there is an url where you have to change to application name as well.
1. gradle appengineUpdate  
1. gradle appengineUpdateBackend  
1. In a web browser goto http://your-app-name.appsot.com
1. Upload the file data/orders1.xml. These orders will show up as pending orders.
1. Select "Pending" orders and confirm some of them. And the will show up as "Processing"
1. Klick "Process Confimrd Orders" to chnage their status to "Processed".
