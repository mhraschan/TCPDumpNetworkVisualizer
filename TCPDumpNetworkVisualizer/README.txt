------------------------------------------ Readme ------------------------------------------------------

------------- Getting started ------------- 

1) Get jnetpcap library from http://jnetpcap.com/
	Important: Get version 1.3 since version 1.4 was still in development mode while developing the application (some things didn't work).
2) Make sure that you put the libjnetpcap.so into the java.library.path. Either check where it is pointing (mostly to /usr/lib/), or start
	the webserver with -Djava.library.path=/path/to/libjnetpcap.so
3) Also make sure that libpcap is also in the java.library.path
	Unfortunately jnetpcap 1.3 was hardlinked to libpcap 0.9 so any other version will not work (will result in an error when accessing the jnetpcap functionality).
4) Get gwt-2.4.0 from http://code.google.com/webtoolkit/gettingstarted.html
5) Check if you've set your JAVA_HOME system environment path.
5) We've provided an ant script to build and run the project. However, you have to specifiy the following paths in the build.xml file:
	gwt.sdk - Path to your gwt-sdk.
	jni.path - Path where java looks for the libraries (jnetpcap and libpcap).
6) To compile the project and run the server in devmode, use "ant build devmode" (this starts the application in devmode which may not be fast. To get a faster version,
	compile the project and copy the contents of the war folder to e.g. tomcat).

------------- Using the program -------------
Since this is only a prototype, you have to be "careful" how to use it (it may crash or do nothing if not handled correctly... :))

First you have to upload a file. Wait for the message in the log where it says how many packets were parsed etc. (until that message, dont risk to click anything else!!)
After that, you have to enter a client-ip address (the ip-address of the home node e.g. the ip of the node which captured the transfer).
Then you can use the slider to look at the package transfer. Do not do anything while the client is communicating to the server...

------------- Dependencies -------------
jnetpcap - jnetpcap API
gwtupload-0.6.3 - Upload widget
gwt-slider-bar - Slider for gwt
commons-io + commons-fileupload - Required for Upload widget

------------- Setting up Eclipse -------------
To setup the project and run in Eclipse, the google plugin for eclipse is required: http://code.google.com/eclipse/docs/getting_started.html

The archive should already contain the .project and .classpath file. You should be able to import the project as an existing eclipse project. If everything was
setup correctly, you should'nt have any problems (GWT-Plugin + correctly setup gwt-sdk path in the preferences).
If gwt is reporting errors due to missing gwt libraries, try to disable and enable the gwt support for the project.

------------- Project Structure -------------

./src
Contains the java sources of the project. 

All classes in com.uh.nwvz.client.* are the sources for the java code on the client side (which is later converted to javascript).

All classes in com.uh.nwvz.server.* are the sources for the server side.
In the package com.uh.nwvz.server.pcap is a class SimplePacketHandler which we used in the last design of the project. The PacketHandler
gets more details from the packets, but was later dropped (but remained in the project, maybe for later use?).

The classes in com.uh.nwzv.shared contains all classes which are used on server and client side. Those are mostly data transfer objects (DTO) and
utility classes.

./resources
Contains the resources which we required (currently only consists of the images required for the time-slider).

./doc
Contaisn the presentation for the lecture.

./lib
Contains all libraries required to build and run the project.

./war
Contains all files required for the webapp 
TCPDumpNetworkVisualizer.html - The html-page which defines the basic structure.
TCPDumpNetworkVisualizer.css - The stylesheet.
	./WEB-INF
	web.xml - Configuration file.