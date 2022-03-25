package model.properties;

import java.io.Serializable;

public class Prefs implements Serializable {
	private static final long serialVersionUID = 7812480018812295809L;
	private String projectPath;
	private String tilesSourcePath;
	private boolean confirm;
	private static Prefs instance;
	
	public String getTilesSourcePath() {
		return tilesSourcePath;
	}
	public void setTilesSourcePath(String tilesSourcePath) {
		this.tilesSourcePath = tilesSourcePath;
	}
	public String getProjectPath() {
		return projectPath;
	}
	public void setProjectPath(String savedPath) {
		this.projectPath = savedPath;
	}
	public void setPrefs(Prefs prefs) {
		instance = prefs;
	}
	public static Prefs getInstance() {
		if(instance == null) {
			instance = new Prefs();
		}
		return instance;
	}
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	public boolean getConfirm() {
		return this.confirm;
	}
	
}
