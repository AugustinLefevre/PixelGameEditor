package gui.map.map_editor;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import model.tiles.Tile;

import java.io.FileNotFoundException;

import gui.map.library.MapLibrary;
import gui.tiles.library.TilesLibrarySelectable;;

public class MapManager extends BorderPane {
	private static Tile currentTile;
	private static MapManager instance;
	private TilesLibrarySelectable tilesLibrary;

	public MapManager() {
		super();
		tilesLibrary = new TilesLibrarySelectable();
		setLeft(tilesLibrary);
		setRight(MapLibrary.getInstance());
//		ScrollPane test = new ScrollPane();
//		test.setContent(new Label("hey"));
		//setRight(test);
		try {
			tilesLibrary.refresh();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static MapManager getInstance() {
		if(instance == null) {
			instance = new MapManager();
		}
		return instance;
	}
	public TilesLibrarySelectable getTilesLibrary() {
		return tilesLibrary;
	}
	public static void setCurrentTile(Tile tile) {
		currentTile = tile;
	}
}
