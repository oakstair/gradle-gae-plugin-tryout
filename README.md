This project uses gradle & gradle-gae-plugin to build, test and deploy google's example project. 
See http://googcloudlabs.appspot.com/codelabexercise8.html


There are current some issues with this example that I would like to fix:

1) If you run it locallly and hit http://localhost:8888 and try to upload the file data/orders1.xml it crashes due to missing classes. 
So there is something wrong with how the classpath is set up.

2) I failed to execute gradle gaeUpdate which is strange since I have done that in otehrs project without any problems. 
I guess a typo somewhere ...



