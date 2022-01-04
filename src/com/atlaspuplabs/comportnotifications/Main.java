package com.atlaspuplabs.comportnotifications;

import java.awt.SystemTray;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.UIManager;

import com.fazecast.jSerialComm.SerialPort;

public class Main {
	
	public static void main(String[] args) {
		
		
		try {
			UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName());
	    }catch (Exception e) {}
		
		
		
		if (!SystemTray.isSupported()){
            System.err.println("System tray not supported!");
            System.exit(1);
        }else {
			Notifications.tray.setupTray();
        }
		
		
		
		PortMan.startListener();
		
	}
	
}