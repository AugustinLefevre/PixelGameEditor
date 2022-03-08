package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.Main;
import gui.TilesEditor.TilesManager;
import model.tiles.TilesSource;
import model.tiles.TilesSourceDatabase;

public class TilesSourceController {
	private TilesSourceDatabase db = TilesSourceDatabase.getInstance();
	private static TilesSourceController instance;
	
	public List<TilesSource> getTilesSources(){
		return db.getTilesSources();
	}
	
	public void removeTilesSource(int index) {
		db.removeTilesSource(index);
	}
	
	public TilesSource addTilesSource(String path) {
		
		TilesSource ts = new TilesSource();
		ts.setPath(path);

		db.addTilesSource(ts);
		return ts;
		
	}
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
		PrefsController.getInstance(Main.getInstance().getRoot()).savePath(file.getAbsolutePath());
	}
	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
	
	public static TilesSourceController getInstance() {
		if(instance == null) {
			instance = new TilesSourceController();
			return instance;
		}
		return instance;
	}
}
