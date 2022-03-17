package model.properties;

import java.io.Serializable;

public class ProjectProperties implements Serializable {
	private static final long serialVersionUID = 3846525459406027524L;
	private static ProjectProperties instance;
	private String projectName;
	private int tileSize;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getTileSize() {
		return tileSize;
	}
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}
	public void setProperties(ProjectProperties pp) {
		instance = pp;
	}
	public static ProjectProperties getInstance() {
		if(instance == null) {
			instance = new ProjectProperties();
		}
		return instance;
		
	}
}
