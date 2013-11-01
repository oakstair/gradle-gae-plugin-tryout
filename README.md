This project uses gradle & gradle-appengine-plugin to build, test and deploy google's example project. 
See http://googcloudlabs.appspot.com/codelabexercise8.html


There are current some issues with this example that I would like to fix:

1. I can't even run gradle due to error: **Could not find property 'compileJava'**

2.  If you run it locallly and hit http://localhost:8888 and try to upload the file data/orders1.xml it crashes due to missing classes. 
So there is something wrong with how the classpath is set up.

3. I failed to execute gradle gradleUpdate which is strange since I have done that in otehrs project without any problems. 
I guess a typo somewhere ...



