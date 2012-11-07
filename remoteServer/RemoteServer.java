package remoteServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;
public class RemoteServer {

	// Creates a dialog box with a text field edit a variable specified by an argument

	

	public static void baseGUI() { 
		
		final JPopupMenu popupMainMenu = new JPopupMenu();
		Image mainImage = Toolkit.getDefaultToolkit().createImage(
				"mainIcon.gif");
		final TrayIcon mainTrayIcon = new TrayIcon(mainImage,"Remote Server");
		final SystemTray systemTray = SystemTray.getSystemTray();
		int portNo = 4200;
		try {
			systemTray.add(mainTrayIcon);
		} catch (AWTException e) {
			System.err.println(e);
		}
		

		// Creates and initialises all of the Pop-up menuItems as specified by the Java.awt package
		JLabel titleItem = new JLabel("Server");
		JSeparator exitSeparator =new JSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenu securityMenu = new JMenu("Security");
		JMenu resourcesMenu = new JMenu("Resources");
		JCheckBoxMenuItem resourcesBool = new JCheckBoxMenuItem(
				"Resource Broadcasting");
		JMenuItem refreshRate = new JMenuItem("Max Refresh Rate");
		JMenu portMenu = new JMenu("Port");
		JMenu shortcutsMenu = new JMenu("Shortcuts");
		JMenuItem logItem = new JMenuItem("Open Log");
		JLabel folderPathTitle = new JLabel("Shortcuts Folder Path");
		JTextArea folderPath = new JTextArea("Shortcuts Folder Path",10,2);
		JLabel username = new JLabel("Username");
		final JTextField usernameField = new JTextField("Username",25);
		usernameField.enableInputMethods(true);
		usernameField.setEditable(true);
		TextField portField = new TextField("0-65535", 15);
		JPasswordField passwordField = new JPasswordField("Password",25);
		JButton updateButton = new JButton("Update");
		JLabel password = new JLabel("Password");
		JCheckBoxMenuItem shortsEnabled = new JCheckBoxMenuItem(
				"Shortcuts Enabled");
		
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		}); 
		usernameField.addDocumentListener(new MouseListener() {

			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				usernameField.requestFocus();
				usernameField.setCaretPosition(0);
				usernameField.selectAll();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	

		// Add items to popup menu
		popupMainMenu.add(titleItem);
		popupMainMenu.add(securityMenu);
		popupMainMenu.add(resourcesMenu);
		popupMainMenu.add(portMenu);
		popupMainMenu.add(shortcutsMenu);
		popupMainMenu.add(logItem);
		try {
			URL externalIPService = new URL(
					"http://automation.whatismyip.com/n09230945.asp");
			System.setProperty("http.agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0) ");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					externalIPService.openStream()));
			String externalIPAddress = in.readLine();
			JLabel ipAddressItem = new JLabel(externalIPAddress);
			popupMainMenu.add(ipAddressItem);
		} catch (IOException e3) {
			e3.printStackTrace();
			mainTrayIcon
					.displayMessage(
							"Error",
							"Your Computer cannot access 'http://automation.whatismyip.com/n09230945.asp' either your computer does not access to the internet or the DNS cannot be resolved",
							TrayIcon.MessageType.WARNING);
							String externalIPAddress = "[IP Not Avalible]";
							JLabel ipAddressItem = new JLabel(externalIPAddress);
							popupMainMenu.add(ipAddressItem);
		} catch (IllegalArgumentException e2) {
			e2.printStackTrace();
		}
		popupMainMenu.add(exitSeparator);
		popupMainMenu.add(exitItem);
		securityMenu.add(username);
		popupMainMenu.add(usernameField);
		securityMenu.add(password);
		securityMenu.add(passwordField);
		securityMenu.add(updateButton);
		resourcesMenu.add(resourcesBool);
		resourcesMenu.add(refreshRate);
		portMenu.add(portField);
		shortcutsMenu.add(folderPathTitle);
		shortcutsMenu.add(folderPath);
		shortcutsMenu.add(shortsEnabled);
		
		
		usernameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
			
		});
		
		// Initialisation of the MouseListener and assigning it to the menuitems
	
		mainTrayIcon.addMouseListener(new MouseListener() {
		       
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Rectangle tester =  GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
					int Height = screenSize.height - tester.height;
	                popupMainMenu.setLocation(e.getX(),screenSize.height-205);
	                popupMainMenu.setInvoker(popupMainMenu);
	                popupMainMenu.setVisible(true);
	            }
			}
	    });
		
		
	}
	
	
		
	public static boolean firstCheck() {
		File savedDetails = new File(System.getProperty("user.home")+"/AppData/RemoteServer/details.csv");
		if(savedDetails.exists()) {
			
			return false;
		} else {
			
			return true;
			
		}
	}
	
	public static void firstInit() {
		File savedDetailsFolder = new File(System.getProperty("user.home")+"/AppData/Roaming/RemoteServer/");
		File savedDetails = new File(System.getProperty("user.home")+"/AppData/Roaming/RemoteServer/details.csv");
		File logFile = new File("log.txt");
		if(!savedDetailsFolder.mkdir()){
			//TODO: umm code here
		} else {
			try {
				savedDetails.createNewFile();
			} catch (IOException e) {
				// TODO Make a dialog 
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]) {
		if (firstCheck() == true) {
			firstInit();
		}
		
		if (!SystemTray.isSupported()) { // Check to see if the machine supports
											// the notification area
			System.out
					.println("This Program is not compatiable with this system");
			System.out.println("ERROR: SystemTray is not supported!");
			return;
		} else {
			baseGUI();
		}

	}

}
