package com.atlaspuplabs.comportnotifications;

import java.util.ArrayList;

import com.fazecast.jSerialComm.SerialPort;

public class PortMan {
	

	public static boolean running = true;
	private static boolean initialScan = true;
	
	public static ArrayList<SerialPort> ports = new ArrayList<>();
	

	public static boolean connectionNotifications = Prefs.getBool("connectionNotifications");
	public static boolean disconnectionNotifications = Prefs.getBool("disconnectionNotifications");
    
	
	public static void startListener() {
		refreshPorts();
		Notifications.updateConnectedDevices(ports);
		while(running) {
			refreshPorts();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void refreshPorts() {
		
		connectionNotifications = Prefs.getBool("connectionNotifications");
		disconnectionNotifications = Prefs.getBool("disconnectionNotifications");
		
		ArrayList<SerialPort> lastPorts = (ArrayList<SerialPort>) ports.clone();
		
		ports.clear();
		for(SerialPort port : SerialPort.getCommPorts()) {
			ports.add(port);
		}
		
		if(!initialScan) {
			for(SerialPort portCheck : lastPorts) {
				SerialPort newPortComparison = has(ports,portCheck);
				if(newPortComparison == null) {
					System.out.println("Detected port "+portCheck.getDescriptivePortName()+" was disconnected");
					Notifications.updateConnectedDevices(ports);
					if(disconnectionNotifications) {
						Notifications.notify(portCheck.getSystemPortName()+" Disconnected", portCheck.getDescriptivePortName()+" is no longer connected.");
					}
				}
				
			}
			for(SerialPort portCheck : ports) {
				SerialPort oldPortComparison = has(lastPorts,portCheck);
				if(oldPortComparison == null) {
					System.out.println("Detected port "+portCheck.getDescriptivePortName()+" was connected");
					Notifications.updateConnectedDevices(ports);
					if(connectionNotifications) {
						Notifications.notify(portCheck.getSystemPortName()+" Connected", portCheck.getDescriptivePortName()+" is now ready for use.");
					}
				}
			}
		}
		initialScan = false;
	}
	
	private static SerialPort has(ArrayList<SerialPort> portArr, SerialPort search) {
		for(SerialPort compare : portArr) {
			if(compare.getSystemPortName().equals(search.getSystemPortName())) {
				return compare;
			}
		}
		return null;
	}
}
