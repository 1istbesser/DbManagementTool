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
<li>Choose "Projects from folder or archive" and hit next.</li>
<li>Check "Directory" and find your folder.</li>
<li>Hit finish</li>
</ul>
</ol>

<h2>Prerequisite</h2>
<p>The application is operating with databases, thus, you need to have a MysQL database set.</p>
</p>

<h2>The structure</h2>
<p><b>MainClass.java</b>, contains the main method and a call to the logging window. If you want to run the project, this is the file you should attempt to run.<br/>
<b>LoginWindow</b>, the interface for the login window, mostly swing components. It contains little to no functionality.<br/>
<b>ApplicationWindow</b>, the interface for the main window where you'll find tables, records, everything. While running the app you get access to this window once you pass the logging verification in the login window.<br/><br/>
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
<p>Version 2.1 - http://tameraltintop.com/download/Javami_DBTool_V2.1.jar</p>
<p>Version 1.2 - http://tameraltintop.com/download/Tamerincode_DBTool_V1.2.jar</p>
<p>Version 1.1 -  http://tameraltintop.com/download/Tamerincode_DBTool_V1.1.jar</p>
<p>Version 1.0 -  http://tameraltintop.com/download/Tamerincode_DBTool_V1.0.jar</p>
