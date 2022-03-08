package model.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import model.properties.ProjectProperties;
import model.tiles.TilesSource;

public class ProjectData {
	private Project project;
	private static ProjectData instance;
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			project = (Project) ois.readObject();
			ProjectProperties.getInstance().setProperties(project.getProjectProperties());
			Project.getInstance().setProject(project);
			//TilesManager.getInstance().leftColumnRefresh();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ois.close();
	}
	public void addTilesSource(TilesSource ts) {
		Project.getInstance().addTilesSource(ts);
	}
	public List<TilesSource> getTilesSource(){
		//comprendre pour quoi project est null
		if(project != null) {
			return project.getTilesSource();
		}
		return null;
	}
	public void setTilesSource(List<TilesSource> ts) {
		Project.getInstance().setTilesSource(ts);
	}
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Project.getInstance().setProjectProperties();
		project = Project.getInstance();
		
		oos.writeObject(project);
		oos.close();
	}
	public static ProjectData getInstance() {
		if(instance == null) {
			instance = new ProjectData();
		}
		return instance;
	}
	public Project getProject(){
		return project;
	}
}
