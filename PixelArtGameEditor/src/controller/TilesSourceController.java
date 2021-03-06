package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import model.TilesSource;
import model.TilesSourceDatabase;

public class TilesSourceController {
	private TilesSourceDatabase db = new TilesSourceDatabase();
	private static TilesSourceController instance;
	public List<TilesSource> getTilesSources(){
		return db.getTilesSources();
	}
	
	public void removeTilesSource(int index) {
		db.removeTilesSource(index);
	}
	
	public TilesSource addTilesSource(String path) {
		
		TilesSource ts = new TilesSource();
		//ts.setHeight(height);
		ts.setPath(path);
		//ts.setWidth(width);

		db.addTilesSource(ts);
		return ts;
		
	}
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}
	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
	public static TilesSourceController getInstance() {
		if(instance == null) {
			return new TilesSourceController();
		}
		return instance;
	}
}
