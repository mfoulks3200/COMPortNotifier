package com.atlaspuplabs.comportnotifications;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.fazecast.jSerialComm.SerialPort;

public class Notifications {
	
    public static Notifications tray = new Notifications();
    private static TrayIcon trayIcon;
    private static boolean traySetupComplete = false;
    private static boolean trayMenuVisible = false;
    private static Menu connectedDevices;
    
    private static boolean startupRegKeyExists() {
    	Process regQuery;
		try {
			regQuery = Runtime.getRuntime().exec("reg query HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v \"COM Port Notifier\"");
			Thread.sleep(50);
			regQuery.destroy();
			return regQuery.exitValue() == 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
    }
	
	public void setupTray() {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        //Image image = Toolkit.getDefaultToolkit().createImage("usb.png");
        //Alternative (if the icon is on the classpath):
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("usb.png"));

        trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("COM Port Notifier");
        
        
        
        
        final PopupMenu popup = new PopupMenu();
       
        // Create a pop-up menu components
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutWindow();
			}
        	
        });
        
        CheckboxMenuItem runAtStartup = new CheckboxMenuItem("Run At Startup");
        boolean startup = startupRegKeyExists();
        runAtStartup.setState(startup);
		runAtStartup.setEnabled(!startup);
		
        runAtStartup.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(startupRegKeyExists()) {
					runAtStartup.setState(true);
					runAtStartup.setEnabled(false);
					return;
				}
				String homeDirectory = System.getProperty("user.home");
				try {
					
					System.out.println("Creating directory "+homeDirectory+"\\.COMPortNotifier");
					new File(homeDirectory+"\\.COMPortNotifier").mkdir();
					String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
					
					if(jarPath.contains(".jar")) {
						System.out.println("Copying JAR to "+homeDirectory+"\\.COMPortNotifier\\COMPortNotifier.jar");
						Path copied = Paths.get(homeDirectory+"\\.COMPortNotifier\\COMPortNotifier.jar");
					    Path originalPath = Paths.get(jarPath);
					    Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
					}else {
						System.out.println("No JAR To Copy");
					}
					
					if(startupRegKeyExists()) {
						System.out.println("Creating Registry Key");
						Runtime.getRuntime().exec("reg add HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v \"COM Port Notifier\" /t REG_SZ /f /d \"java -jar \\\""+homeDirectory+"\\.COMPortNotifier\\COMPortNotifier.jar\"\"");
					}else {
						System.out.println("Registry Key Already Exists! Skipping...");
					}
					System.out.println("Restarting app...");
					Runtime.getRuntime().exec("cmd /c \"java -jar "+homeDirectory+"\\.COMPortNotifier\\COMPortNotifier.jar\"");
					System.exit(0);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        	
        });
        
        MenuItem persistantIcon = new MenuItem("Make Icon Persistant");
        persistantIcon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("ms-settings:taskbar"));
				} catch (Exception ex) {
				}
				Notifications.notify("Setup Icon Persistance","Click the link that says \"Select which items appear on the taskbar\", and enable COM Port Notifier");
			}
        	
        });
        

        Menu notificationSettings = new Menu("Notification Settings");
        
        CheckboxMenuItem portConnected = new CheckboxMenuItem("Port Connected");
        portConnected.setState(Prefs.getBool("connectionNotifications"));
        portConnected.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				Prefs.setBool("connectionNotifications", portConnected.getState());
        		System.out.println("Set connectionNotifications to "+Prefs.getBool("connectionNotifications"));
			}
          });
        
        CheckboxMenuItem portDisconnected = new CheckboxMenuItem("Port Disconnected");
        portDisconnected.setState(Prefs.getBool("disconnectionNotifications"));
        portDisconnected.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				Prefs.setBool("disconnectionNotifications", portDisconnected.getState());
        		System.out.println("Set disconnectionNotifications to "+Prefs.getBool("disconnectionNotifications"));
			}
          });
        
        connectedDevices = new Menu("Connected Devices");
        
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        	
        });
       
        //Add components to pop-up menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(runAtStartup);
        popup.add(persistantIcon);
        popup.add(connectedDevices);
        popup.add(notificationSettings);
        notificationSettings.add(portConnected);
        notificationSettings.add(portDisconnected);
        popup.addSeparator();
        popup.add(exitItem);

       
        trayIcon.setPopupMenu(popup);
        trayIcon.addMouseListener(new MouseListener() {

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
				Notifications.updateConnectedDevices(PortMan.ports);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
        
        
        
        try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        traySetupComplete = true;
    }
	
	public static void updateConnectedDevices(ArrayList<SerialPort> ports) {
		ArrayList<SerialPort> portCache = (ArrayList<SerialPort>) ports.clone();
		connectedDevices.removeAll();
		for(SerialPort port : ports) {
	        Menu portMenuItem = new Menu("");
	        connectedDevices.add(portMenuItem);
	        portMenuItem.setLabel(port.getSystemPortName()+" "+port.getPortDescription());
	        addBaudOptions(portMenuItem, port);
		}
	}
	
	private static void addBaudOptions(Menu item, SerialPort port) {
		MenuItem info = new MenuItem("Putty - 8,N,1,N");
		info.setEnabled(false);
		item.add(info);
		
		MenuItem state = new MenuItem("");
		state.setLabel(port.isOpen() ? "In Use" : "Not In Use");
		state.setEnabled(false);
		item.add(state);
		
		item.add(addBaudOption(port, "1200"));
		item.add(addBaudOption(port, "2400"));
		item.add(addBaudOption(port, "4800"));
		item.add(addBaudOption(port, "9600"));
		item.add(addBaudOption(port, "115200"));
		item.add(addBaudOption(port, "921600"));
	}
	
	private static MenuItem addBaudOption(SerialPort port, String baud) {
		String homeDirectory = System.getProperty("user.home");
		MenuItem baudItem = new MenuItem(baud);
		baudItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean puttyInstalled = true;
					try {
						Process process = Runtime.getRuntime().exec("putty");
						process.destroy();
					}catch(Exception e) {
						puttyInstalled = false;
					}
					if(puttyInstalled) {
						Runtime.getRuntime().exec(String.format("putty "+port.getSystemPortName()+" -serial -sercfg "+baud+",8,n,1,N", homeDirectory));
					}else {
						new PuttyInstall();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
          });
		return baudItem;
	}
	
	public static void notify(String title, String message) {
		if(!traySetupComplete) {
			tray.setupTray();
		}
		trayIcon.displayMessage(title, message, MessageType.NONE);
	}
	
}
