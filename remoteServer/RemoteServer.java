package remoteServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.util.logging.*;

import javax.swing.*;
public class RemoteServer {
	
	public static Logger rsLog = Logger.getLogger(RemoteServer.class.getName());
	public String inputUsername; 
	public static String inputPassword;
	public double inputRate;
	public int inputPort;
	public static File savedDetailsFolder = new File(System.getProperty("user.home")+"/AppData/Roaming/RemoteServer/");
	public static File savedDetails = new File(System.getProperty("user.home")+"/AppData/Roaming/RemoteServer/details.csv");
	public static File shortFolder = new File("/Shortcuts Folder/");
	
	
	
	/*	Creates and instantiates the GUI of the program and allows
	 * 	Input from the user and modifies the main variables for port,
	 * 	username, password and refresh rate
	 */	
	public static void GUIBase() { 
			
		
		    rsLog.log(new LogRecord(Level.INFO, "Initialising GUI and variables."));
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
				rsLog.log(new LogRecord(Level.SEVERE, "<AWT_Exception> unable to add program to system tray"));
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
			JMenuItem portMenu = new JMenuItem("Port");
			JMenu shortcutsMenu = new JMenu("Shortcuts");
			JMenuItem logItem = new JMenuItem("Open Log");
			JLabel folderPathTitle = new JLabel("Shortcuts Folder Path");
			JTextArea folderPath = new JTextArea("Shortcuts Folder Path",10,2);
			JMenuItem username = new JMenuItem("Username");
			JButton updateButton = new JButton("Update");
			JMenuItem password = new JMenuItem("Password");
			JCheckBoxMenuItem shortsEnabled = new JCheckBoxMenuItem(
					"Shortcuts Enabled");
			JOptionPane inputField = new JOptionPane();
			
		    
			
			
			
				

			// Add items to popup menu
			rsLog.log(new LogRecord(Level.INFO, "Adding items to Menu"));
			popupMainMenu.add(titleItem);
			popupMainMenu.add(securityMenu);
			popupMainMenu.add(resourcesMenu);
			popupMainMenu.add(portMenu);
			popupMainMenu.add(shortcutsMenu);
			popupMainMenu.add(logItem);
			rsLog.log(new LogRecord(Level.INFO, "Finding Public IP"));
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
								rsLog.log(new LogRecord(Level.WARNING, "Unable to find IP. Connected?"));
								String externalIPAddress = "[IP Not Avalible]";
								JLabel ipAddressItem = new JLabel(externalIPAddress);
								popupMainMenu.add(ipAddressItem);
			} catch (IllegalArgumentException e2) {
				e2.printStackTrace();
				rsLog.log(new LogRecord(Level.SEVERE, "<ILLEGAL_ARGUMENT_EXCEPTION> ERROR"));
				System.exit(0);
			}
			popupMainMenu.add(exitSeparator);
			popupMainMenu.add(exitItem);
			securityMenu.add(username);
			securityMenu.add(password);
			securityMenu.add(updateButton);
			resourcesMenu.add(resourcesBool);
			resourcesMenu.add(refreshRate);
			shortcutsMenu.add(folderPathTitle);
			shortcutsMenu.add(folderPath);
			shortcutsMenu.add(shortsEnabled);
			
			
			rsLog.log(new LogRecord(Level.INFO, "Adding ActionListeners."));
			
			exitItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
				
			}); 
			password.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JPasswordField passwordField = new JPasswordField("password",25);
				    JLabel passwordTitle = new JLabel("Password: ");
				    Box passwordDialog = Box.createHorizontalBox();
				    passwordDialog.add(passwordTitle);
					passwordDialog.add(passwordField);
					int x = JOptionPane.showConfirmDialog(null, passwordDialog, "Password", JOptionPane.PLAIN_MESSAGE);

				    if (x == JOptionPane.OK_OPTION) {
				      inputPassword = passwordField.getPassword().toString();
				    }
				    				}
				
			});
			username.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					String inputUsername = (String)JOptionPane.showInputDialog(null,"Username:","Username",JOptionPane.PLAIN_MESSAGE);
					
				}
				
			});
			// Initialisation of the MouseListener and assigning it to the menuitems
		    mainTrayIcon.addMouseListener(new MouseListener() {
			       
				@Override
				public void mouseClicked(MouseEvent arg0) {}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger()) {
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						popupMainMenu.setLocation(e.getX(),screenSize.height-205);
		                popupMainMenu.setInvoker(popupMainMenu);
		                popupMainMenu.setVisible(true);
		            }
				}
		    });
			
			
		}

	
		
	// Checks to see if this instance of the program is the first instance run on the computer in which case returns true
	public static boolean isfirstCheck() {
		File savedDetails = new File(System.getProperty("user.home")+"/AppData/RemoteServer/details.csv");
		rsLog.log(new LogRecord(Level.INFO, "First run detected."));
		if(savedDetails.exists()) {
			
			return false;
		} else {
			
			return true;
			
		}
	}
	
	
	
	
	
	
	
	
	//Initialises the folders and files if this is the first time the software is run
	public static void firstInit() {
		
		rsLog.log(new LogRecord(Level.INFO, "Initialising save files..."));
		savedDetailsFolder.mkdir();
		shortFolder.mkdir();
		
		if(!shortFolder.exists()){
			rsLog.log(new LogRecord(Level.SEVERE, "<ERROR> Folder failed to create."));
			System.exit(0);
		}
		
		if(!savedDetailsFolder.exists()){
			rsLog.log(new LogRecord(Level.SEVERE, "<ERROR> Folder failed to create."));
			System.exit(0);
		} else {
			try {
				savedDetails.createNewFile();
			} catch (IOException e) {
				// TODO Make a dialog 
				e.printStackTrace();
				rsLog.log(new LogRecord(Level.SEVERE, " <IO_Exception> Failed to create files"));
				System.exit(0);
			}
		}
	}
	
	
	
	
	
	
	
	
	public static void main(String args[]) {
		
		rsLog.log(new LogRecord(Level.INFO, System.getProperty("line.separator")));
		
		Formatter mainFormatter = new rsLogFormatter();
		try {
			FileHandler fHandle = new FileHandler("log.txt",true);
			fHandle.setFormatter(mainFormatter);
			rsLog.addHandler(fHandle);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rsLog.log(new LogRecord(Level.INFO, ""));
		rsLog.log(new LogRecord(Level.INFO, ""));
		rsLog.log(new LogRecord(Level.INFO, "Remote Access Server is starting..."));
		
		if (isfirstCheck() == true) {
			firstInit();
		}
		
		
		
		if (!SystemTray.isSupported()) { // Check to see if the machine supports
											// the notification area
			System.out
					.println("This Program is not compatiable with this system");
			System.out.println("ERROR: SystemTray is not supported!");
			rsLog.log(new LogRecord(Level.SEVERE, "System Tray is not supported on this machine"));
			return;
		} else {
			GUIBase();
		}

	}

}
