import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alee.laf.button.WebButtonStyle;

/**
* Tamerincode ~ This database tool is an application that
* provides you with a dynamic way of adding, deleting and updating
* records in a MySQL database.
* @author  Tamer Altintop
* @version 1.2
* @since 01/06/2017
* * Web: www.tamerinblog.com
* GitHub: github.com/1istbesser
*/

public class MainClass {
	public static void main(String[] args) {
		try { 
			Color custm = new Color(204, 204, 204); 
			WebButtonStyle.topSelectedBgColor = custm; 
			WebButtonStyle.bottomSelectedBgColor = Color.white; 
			UIManager.setLookAndFeel ( "com.alee.laf.WebLookAndFeel" );
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { 
			ex.printStackTrace(); 
		} 

		SwingUtilities.invokeLater(new Runnable() 
		{ 
			@Override 
			public void run() 
			{ 
				new LoginWindow(); 
			} 
		}); 
	}
}