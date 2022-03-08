package model.properties;

import java.io.Serializable;

public class Prefs implements Serializable {
	private static final long serialVersionUID = 7812480018812295809L;
	private String savedPath;
	private static Prefs instance;

	public String getSavedPath() {
		return savedPath;
	}

	public void setSavedPath(String savedPath) {
		this.savedPath = savedPath;
	}
	
	public static Prefs getInstance() {
		if(instance == null) {
			instance = new Prefs();
		}
		return instance;
		
	}
}
