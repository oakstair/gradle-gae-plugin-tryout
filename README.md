This project uses gradle & gradle-appengine-plugin to build, test and deploy a google example project. 
See http://googcloudlabs.appspot.com/codelabexercise8.html

There are current some issues with this example since crashes both locally and on appengine.

    Unresolved compilation problems: 
    The import com.google.appengine.api cannot be resolved
    
So there is something wrong with how the classpath is set up.

To build, deploy and test locally: (Assuming you have **git** and **gradle** installed)

1. git clone git@github.com:oakstair/gradle-gae-plugin-tryout.git
1. cd gradle-gae-plugin-tryout
1. gradle appengineRun        
1. In a web browser goto http://localhost:8888
1. Try to upload the file data/orders1.xml
1. **Kaboom!**

To build, deploy and test on appengine:

1. git clone git@github.com:oakstair/gradle-gae-plugin-tryout.git
1. cd gradle-gae-plugin-tryout
1. Edit ./build.gradle and set appId and appEamil to your own values.
1. Edit war/WEB-INF/appengine-web.xml  Change application to your application name.
1. gradle appengineUpdate  
1. In a web browser goto http://your-app-name.appsot.com
1. Try to upload the file data/orders1.xml
1. **Kaboom!**
