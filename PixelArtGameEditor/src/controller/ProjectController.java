package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.Main;
import model.project.Project;
import model.properties.ProjectProperties;
import data.ProjectData;
import model.tiles.TilesSource;

public class ProjectController {
	private ProjectData db = ProjectData.getInstance();
	private static ProjectController instance;
	
	public TilesSource addTilesSource(String path) {
		
		TilesSource ts = new TilesSource();
		ts.setPath(path);
		Project.getInstance().addTilesSource(ts);
		return ts;
		
	}
	public List<TilesSource> getTilesSource(){
		return Project.getInstance().getTilesSource();
	}
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
		PrefsController.getInstance().saveProjectPath(file.getAbsolutePath());
	}
	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
	
	public static ProjectController getInstance() {
		if(instance == null) {
			instance = new ProjectController();
			return instance;
		}
		return instance;
	}

	public void setProject(Project project) {
		ProjectProperties.getInstance().setProperties(project.getProjectProperties());
		Project.getInstance().setProject(project);
	}
	public static void setPojectProperties() {
		Project.getInstance().setProjectProperties();
	}
	public Project getProject() {
		return Project.getInstance();
	}
}
