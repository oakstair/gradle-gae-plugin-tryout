This project uses gradle & gradle-appengine-plugin to build, test and deploy google's example project. 
See http://googcloudlabs.appspot.com/codelabexercise8.html


There are current some issues with this example that I would like to fix:

1. I have to apply both the java and the war plugin before the appengine plugin. Why?
1.  If you run it locallly and hit http://localhost:8888 and try to upload the file data/orders1.xml it crashes due to missing classes. 
So there is something wrong with how the classpath is set up.

To build, deploy and test this app do this: (Assuming you have **git** and **gradle** installed)

1. git clone git@github.com:oakstair/gradle-gae-plugin-tryout.git
1. cd gradle-gae-plugin-tryout
1. Edit ./build.gradle and set appId and appEamil to your own values.
1. Edit war/WEB-INF(appengine.xmlChange and set the application to your application name.
1. gradle appengineUpdate          [To build and deploy your app]
1. In a web browser goto http://your-app-name.appsot.com
1. Try to upload the file data/orders1.xml






