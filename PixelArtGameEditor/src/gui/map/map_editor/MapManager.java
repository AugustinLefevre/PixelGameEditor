package gui.map.map_editor;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
		setCenter(MapView.getInstance());
		setRight(MapLibrary.getInstance());
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
	public void update(Image img) {
		MapView.getInstance().displayMap(img);
	}
}
