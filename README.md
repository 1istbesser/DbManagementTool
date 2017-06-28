# DbManagementTool
<h2>Summary</h2>
<p>I have to deal with databases on a daily basis when working with websites. It's often a lot of playing around and test the data from the database. That is why I decided to create this simple database management tool in Java - to make it easier to add/ edit and delete records. I haven't tested it with other types of databases except for MySQL, therefore, I only know for sure it works with MySQL databases.</p>
<p>If you want to run the application, make sure you have Java installed on your machine. If you want to open up this project yourself, make sure you have a IDE ready, I recommand Eclipse with JDK8.</p>
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
Then you have to download all the libraries from the "Prerequisite" section below and add them to the project. To do this in Eclipse, you can simply right click on the project, go to build path -> configure build path -> add external jars and select all the jar files from the archive.</p>

<h2>Prerequisite</h2>
<p>The application is operating with databases, thus, you need to have a MysQL database set. However, if you do not have one or don't know how to set one, you can use the following database which is set up for testing purposes:</p><br/>
<ul>
<li>Host: 160.153.128.32</li>
<li>Database: SuperVShop</li>
<li>Username: dbtest</li>
<li>Password: dbtest</li>
</ul><br/>
<p>You will need three external libraries for the latest version of this application(1.2): The Hikari connection pool, Weblaf look and feel and the simple logging facade for java. All of them are external libraries and you only have to add them to the project, no other operations are needed. <br/><br/>

I zipped all of them and you can download the archive here: http://tamerinblog.com/download/java-libraries.zip<br/><br/>
</p>

<h2>The structure</h2>
<p><b>MainClass.java</b>, it contains the main method and a call to the logging window. If you want to run the project, this is the file you should attempt to run.<br/>
<b>LoginWindow</b>, the interface for the login window. It contains little to no functionality.<br/>
<b>ApplicationWindow</b>, the interface for the main window where you'll find tables, records, everything. You get access to this window once you pass the logging verification in the login window.<br/><br/>
<strike><b>NewRow.java</b>, one separated function that will add empty rows to your table upon triggering the button. It will probably be moved into the <b>DatabaseOperations.java</b> in future versions.<br/></strike><br/>
<b>DatabaseOperations.java</b>, here all the magic happens. It contains most of the functionality of the app.<br/>
<b>DatabaseHandler.java</b>, here the hikari connection pool is implemented. This class handles all the database connections across the app.</p>
<b>NewTableDialog.java</b>, here is both the interface and the functionality for creating new tables, will be refactored in future versions.</p>
<h2>The Version history</h2>
</p> Current version: 2.1</p><br/>
<ul>
<li> Version 1.0, both the GUI and the methods connecting and accessing the database are in one class, the swing components and the functionability are mixed together.</li>
<li> Version 1.1, there are new classes where most of the methods that are handling the database processes.<ul><li>However, it still has a lot of connection methods and information shared across the app. Also it appears to be working slightly slower than the previous version.</li></ul></li>
<li>Version 1.2 - The NewRow class was removed and its content was moved into <b>DatabaseOperations.java</b>, the connections were removed from all classes and the whole database connection activity is handled in the <b>DatabaseHandler.java</b> by a connection pool. The look and feel of the app was changed to give it a more modern appearance. </li>
<li>Version 2.1 - The button to open a table was changed to the button which allowes you to create a table through a JDialog box. You can open any table by double-click, there is no need for an extra button. Also there is a refresh button because after you create a new table it won't reload automatically. </li></ul><br/><br/>
<h2>Demo</h2>
<p>The project is also available under the compiled version:</p><br/>
<p>Version 2.1 - http://tamerinblog.com/download/Javami_database_tool_V2.1.jar</p>
<p>Version 1.2 - http://tamerinblog.com/download/Tamerincode_DBTool_V1.2.jar</p>
<p>Version 1.1 -  http://tamerinblog.com/download/TamerincodeDBV1.1Runnable.jar</p>
<p>Version 1.0 -  http://tamerinblog.com/download/Tamerincode_DBTool.jar</p>
