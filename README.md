# DbManagementTool
This is a simple database management tool in Java that makes it easy for you to add/ delete/ change records of MySQL databases. 

I'm dealing often with updating and adding records into databases for websites and while there are good tools available out there, I wanted to be able to customize it for my needs.

To get the project set up, you need to import the Java classes into your project and make sure you put the db.png from the images folder into a source folder called "images".

You have to make sure that you have the mysql-connector-java-5.1.40 library included in your project. If you don't have the library, you can download it from here: http://www.tamerinblog.com/download/mysql-connector-java-5.1.40.zip and go to the build path where you can find the option to add external jars.

The application has placeholder data that allows you to connect to a database that I set up for testing purposes. You can use that one or you can connect to your own.

The structure of the project will be changed in future commits as I am splitting the functionability from the GUI.

This is the version 1.1.<br/>
In the version 1.0, both the GUI and the methods connecting and accessing the database were in the same class.<br/>
In the version 1.1, there are new classes where most of the methods that are handling the database processes were moved. However, it appears to be working slightly slower than the previous version.<br/>
In the future version 1.2, I am going to create a connection pool and reduce the number of connections issued towards the database.<br/><br/>
The project is also available under the compiled version:<br/>
Version 1.1 -  http://tamerinblog.com/download/Tamerincode_DBToolV1.1.jar<br/>
Version 1.0 -  http://tamerinblog.com/download/Tamerincode_DBTool.jar
