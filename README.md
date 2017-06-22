# DbManagementTool
<h2>Summary</h2>
<p>I have to deal with databases on a daily basis because when you are working on websites, you often have to play around and test the data. That is why I decided to create this simple database management tool in Java, to make it easier to add/ edit and delete records. I haven't tested it with other types of databases than MySQL, therefore, I only know for sure it works with MySQL databases.</p>
<p>The application is created in Java and it uses an external library for the MySQL connection part, a library named "Mysql Connector Java". Therefore, if you want to open up this project yourself, make sure you have a Java IDE ready, I recommand Eclipse with JDK8.</p>
<br/>
<h2>The setup</h2>
<ol>
<li>Download a copy of this repository.</li>
<li>Open up your IDE and import the project as a new project.<br/>
<p>If you are using Eclipse, you can do it as follows:</p>
<ul>
<li>Click on File -> Import.</li>
<li>If "General" folder isn't opened up for you, double click on "General" to see the options.</li>
<li>Choose "Existing projects into workspace" and hit next.</li>
<li>Check "Select archive file" and find your archive through the "Browse" button.</li>
<li>Hit finish</li>
</ul>
</ol>
<p>
Then you have to download all the libraries from the "Prerequisite" and add them to the project. To do this in Eclipse, you can simply right click on the project, go to build path -> configure build path -> add external jars and select all the jar files from the archive.</p>

<h2>Prerequisite</h2>
<p>The application is operating with databases, thus, you need to have a MysQL database set. However, if you do not have one or don't know how to set one, the application has placeholder data that allows you to connect to a database that I set up for testing purposes.</p>
<p>Also you will need three external libraries for the latest version of this application(1.2): Hikari connection pool, weblaf look and feel and the simple logging facade for java. <br/><br/>

I zipped all of them and you can download the archive here: http://tamerinblog.com/download/java-libraries.zip<br/><br/>
</p>

<h2>The structure</h2>
<p><b>MainClass.java</b>, the main class with the main method and it contains a call to the logging window. If you want to run the project, this is the file you should attempt to run.<br/>
<b>LoginWindow</b>, has a JFrame and all the other Java Swing components that you will find into the login window when booting up the app. It has a button listener that triggers a function inside <b>DatabaseOperations.java</b> to check the connection credentials provided in the input fields.<br/>
<b>ApplicationWindow</b>, here is the main window that you see once you are connected to a database. Tables, records, operations, everything is laid out here, but they are just blueprints, the graphic part for the functions behind them are called from <b>DatabaseOperations.java</b><br/><br/>
<strike><b>NewRow.java</b>, one separated function that will add empty rows to your table upon triggering the button. It will probably be moved into the <b>DatabaseOperations.java</b> in future versions.<br/></strike><br/>
<b>DatabaseOperations.java</b>, here all the magic happens.<br/>
<b>DatabaseHandler.java</b>, this class implements the hikari connection pool which handles the database connections across the whole app.</p>
<h2>The Version history</h2>
</p> Current version: 1.2</p><br/>
<ul>
<li> Version 1.0, both the GUI and the methods connecting and accessing the database were in one class, the swing components and the functionability were mixed together.</li>
<li> Version 1.1, there are new classes where most of the methods that are handling the database processes were moved.<ul><li>However, it appears to be working slightly slower than the previous version.</li></ul></li>
<li>Version 1.2 - The NewRow class was removed and it content was moved into <b>DatabaseOperations.java</b>, the connections were removed from all files and they now are found in the <b>DatabaseHandler.java</b> which is an implementation of the hikari connection pool library. The look and feel of the app was changed and now weblaf is used to give it a more modern appearance. </li></ul><br/><br/>
<h2>Demo</h2>
<p>The project is also available under the compiled version:</p><br/>
<p>Version 1.2 - http://tamerinblog.com/download/Tamerincode_DBTool_V1.2.jar</p>
<p>Version 1.1 -  http://tamerinblog.com/download/TamerincodeDBV1.1Runnable.jar</p>
<p>Version 1.0 -  http://tamerinblog.com/download/Tamerincode_DBTool.jar</p>
