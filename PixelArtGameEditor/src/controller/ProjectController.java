package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.Main;
import model.project.Project;
import model.project.ProjectData;
import model.tiles.TilesSource;

public class ProjectController {
	private ProjectData db = ProjectData.getInstance();
	private static ProjectController instance;
	
	public Project getTilesSources(){
		return db.getProject();
	}
	
	public TilesSource addTilesSource(String path) {
		
		TilesSource ts = new TilesSource();
		ts.setPath(path);
		db.addTilesSource(ts);
		return ts;
		
	}
	public List<TilesSource> getTilesSource(){
		return db.getTilesSource();
	}
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
		PrefsController.getInstance(Main.getInstance().getRoot()).savePath(file.getAbsolutePath());
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
}
