This project uses gradle & gradle-appengine-plugin to build, test and deploy google's example project. 
See http://googcloudlabs.appspot.com/codelabexercise8.html


There are current some issues with this example that I would like to fix:

0. I have to apply both the java and the war plugin before the appengine plugin. Why?

1. gradle appengineUpdate fails with error message **app_id GET query string parameter must be supplied.** By my gradle build file specifies an app id as stated in the plugin doc.

2.  If you run it locallly and hit http://localhost:8888 and try to upload the file data/orders1.xml it crashes due to missing classes. 
So there is something wrong with how the classpath is set up.

3. I failed to execute gradle gradleUpdate which is strange since I have done that in otehrs project without any problems. 
I guess a typo somewhere ...



