package controller;

import java.io.File;
import java.io.IOException;

import gui.popup.new_project.NewProject;
import javafx.scene.Group;
import model.properties.PreferenciesDB;

public class PrefsController {
	private PreferenciesDB prefs;
	private static PrefsController instance;
	private File prefsFile;
	private NewProject newProjectPopup;
	
	public PrefsController(Group root) throws IOException {
		this.newProjectPopup = NewProject.getInstance();
		root.getChildren().add(this.newProjectPopup);
		this.prefs = PreferenciesDB.getInstance();
		if(this.prefs.loadPathFromPrefs() != null) {
			this.prefsFile = new File(this.prefs.loadPathFromPrefs());
		}
	}
	public static PrefsController getInstance(Group root) throws IOException {
		if(instance == null) {
			return new PrefsController(root);
		}else {
			return instance;
		}
	}
	/**
	 * a fixer
	 * @throws IOException
	 */
	public void loadProject() throws IOException {
		// prefs File is not empty or and process is not on error
		if(this.prefsFile != null) {
			newProjectPopup.setVisibility(false);
			TilesSourceController.getInstance().loadFromFile(this.prefsFile);
		}else {
			
			System.out.println("Popup new project");
			newProjectPopup.setVisible(true);
		}
		
	}
	public void savePath(String path) throws IOException {
		this.prefs.savePathInPrefs(path);
	}
}
