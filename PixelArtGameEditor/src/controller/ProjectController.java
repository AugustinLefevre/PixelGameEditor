package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import model.project.Project;
import model.properties.ProjectProperties;
import data.ProjectData;
import gui.tiles.tiles_editor.TilesManager;
import gui.map.library.MapLibrary;
import gui.popup.tile_editor.TileEditor;
import model.tiles.Tile;
import model.tiles.TilesSource;
import model.tiles.TilesType;

public class ProjectController {
	private ProjectData db = ProjectData.getInstance();
	private static ProjectController instance;
	
	public TilesSource addTilesSource(String path) {
		TilesSource ts = new TilesSource();
		ts.setPath(path);
		Project.getInstance().addTilesSource(ts);
		return ts;
	}
	public void removeTilesSource(TilesSource ts) {
		Project.getInstance().removeTilesSource(ts);
	}
	
	public Tile addTile(String src, int positionX, int positionY, TilesType type) {
		Tile tile = TileEditor.getInstance().getTile();
		if(tile == null) {
			tile = new Tile(src, positionX, positionY, type);
		}else {
			tile.setParams(src, positionX, positionY, type);
		}
		Project.getInstance().addTile(tile);
		
		return tile;
	}
	public void removeTile(Tile tile) {
		Project.getInstance().removeTile(tile);
	}
	public List<TilesSource> getTilesSource(){
		return Project.getInstance().getTilesSource();
	}
	public List<Tile> getTiles(){
		return Project.getInstance().getTiles();
	}
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
		PrefsController.getInstance().saveProjectPath(file.getAbsolutePath());
		PrefsController.getInstance().saveMapsPath(file.getParent() + "\\maps");
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
		Project.setProject(project);
		Project.getInstance().setTilesIds();
	}
	public static void setPojectProperties() {
		Project.getInstance().setProjectProperties();
	}
	public Project getProject() {
		return Project.getInstance();
	}
	public void emptyProject() {
		Project.getInstance().emptyProject();
		try {
			TilesManager.getInstance().tilesSourceThumbnailColumnRefresh();
			TilesManager.getInstance().getTilesLibrary().refreshAll();
			MapLibrary.getInstance().refresh();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public List<Integer> getTilesId(){
		return Project.getInstance().getTilesId();
	}
	public void addId(int id) {
		Project.getInstance().addId(id);
		
	}
}
