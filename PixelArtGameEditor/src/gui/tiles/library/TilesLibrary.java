package gui.tiles.library;

import java.io.FileNotFoundException;
import java.util.List;

import controller.ProjectController;
import gui.map.map_editor.MapManager;
import gui.tiles.tiles_editor.TilesManager;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import model.tiles.Tile;

public abstract class TilesLibrary extends ScrollPane{
	protected GridPane grid;
	
	public TilesLibrary() {
		super();
		setPrefWidth(115);
		grid = new GridPane();
		grid.setPrefHeight(500);
		grid.setMaxHeight(500);
		grid.setStyle("-fx-background-color: #8f8f8f");
		setContent(grid);
	}
	
	public void refresh() throws FileNotFoundException {
		
		if(ProjectController.getInstance().getTiles() != null) {
			this.grid.getChildren().clear();
			List<Tile> tiles = ProjectController.getInstance().getTiles();
			int count = 1;
			int count2 = 0;
			for(Tile tile : tiles) {
				ThumbnailTile thumbnail = new ThumbnailTile(tile);
				addListener(thumbnail);
				if(count++ % 2 == 0) {
					this.grid.add(thumbnail, 1, count2);
					count2++;
				}else {
					this.grid.add(thumbnail, 0, count2);
				}
			}
		}
	}
	
	public void refreshAll() throws FileNotFoundException {
		MapManager.getInstance().getTilesLibrary().refresh();
		TilesManager.getInstance().getTilesLibrary().refresh();
	}
	
	public abstract void addListener(ThumbnailTile thumbnail);
}