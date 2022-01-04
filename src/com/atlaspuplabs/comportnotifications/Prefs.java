package com.atlaspuplabs.comportnotifications;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Prefs {

	private static Preferences prefs = Preferences.userRoot().node(Prefs.class.getName());
	
	public static void setBool(String name, boolean value) {
		prefs.putBoolean(name, value);
	}
	
	public static boolean getBool(String name) {
		return prefs.getBoolean(name, true);
	}
	
}
