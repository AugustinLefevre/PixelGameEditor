package model.properties;

public class ProjectProperties {
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
	
	public static ProjectProperties getInstance() {
		if(instance == null) {
			return new ProjectProperties();
		}else {
			return instance;
		}
	}
}
