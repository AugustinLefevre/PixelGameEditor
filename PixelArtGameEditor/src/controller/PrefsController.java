package controller;

import java.io.File;
import java.io.IOException;

import gui.tiles.tiles_editor.TilesManager;
import gui.popup.new_project.NewProject;
import model.properties.Prefs;
import data.PreferenciesDB;

public class PrefsController {
	private static PrefsController instance;
	private File prefsFile;
	
	public PrefsController() throws IOException {
		if(PreferenciesDB.getInstance().loadPrefs() != null) {
			this.prefsFile = new File(PreferenciesDB.getInstance().loadPrefs().getProjectPath());
		}
	}
	public static PrefsController getInstance() throws IOException {
		if(instance == null) {
			instance = new PrefsController();
			return instance;
		}else {
			return instance;
		}
	}
	/**
	 * a fixer
	 * @throws IOException
	 */
	public void openAutoProject() throws IOException {
		if(PreferenciesDB.getInstance().loadPrefs() != null) {
			String projectPath = PreferenciesDB.getInstance().loadPrefs().getProjectPath();
			this.prefsFile = new File(projectPath);
			if(this.prefsFile != null) {
				NewProject.getInstance().setVisibility(false);
				ProjectController.getInstance().loadFromFile(this.prefsFile);
				saveProjectPath(projectPath);
				if(PreferenciesDB.getInstance().loadPrefs().getTilesSourcePath() != null) {
					saveTilesSourcePath(PreferenciesDB.getInstance().loadPrefs().getTilesSourcePath());
				}
				TilesManager.getInstance().tilesSourceThumbnailColumnRefresh();
				TilesManager.getInstance().getTilesLibrary().refreshAll();
			}
		}else {
			NewProject.getInstance().setVisible(true);
		
		}
	}
	public String getSavedTilesSourcePath() {
		return Prefs.getInstance().getTilesSourcePath();
	}
	public void saveProjectPath(String path) throws IOException {
		Prefs.getInstance().setProjectPath(path);
		PreferenciesDB.getInstance().savePrefs(Prefs.getInstance());
	}
	public void saveTilesSourcePath(String path) throws IOException {
		Prefs.getInstance().setTilesSourcePath(path);
		PreferenciesDB.getInstance().savePrefs(Prefs.getInstance());
	}
	public boolean getConfirm() {
		return Prefs.getInstance().getConfirm();
	}
	public void setConfirm(boolean confirm) {
		Prefs.getInstance().setConfirm(confirm);
	}
	public String getProjectPath() {
		return Prefs.getInstance().getProjectPath();
	}
}
