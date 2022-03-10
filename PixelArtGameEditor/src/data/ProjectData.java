package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import controller.ProjectController;
import model.project.Project;
import model.tiles.TilesSource;

public class ProjectData {
	private Project project;
	private static ProjectData instance;
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			project = (Project) ois.readObject();
			ProjectController.getInstance().setProject(project);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ois.close();
	}
	public List<TilesSource> getTilesSource(){
		if(ProjectController.getInstance().getTilesSource() != null) {
			return ProjectController.getInstance().getTilesSource();
		}
		return null;
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		ProjectController.setPojectProperties();
		
		oos.writeObject(ProjectController.getInstance().getProject());
		oos.close();
	}
	public static ProjectData getInstance() {
		if(instance == null) {
			instance = new ProjectData();
		}
		return instance;
	}

}
