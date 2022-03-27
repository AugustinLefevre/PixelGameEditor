package gui.tiles.library;

import gui.map.map_editor.MapManager;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class TilesLibrarySelectable extends TilesLibrary {
	public TilesLibrarySelectable() {
		super();
	}

	@Override
	public void addListener(ThumbnailTile thumbnail) {
		thumbnail.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				MapManager.setCurrentTile(thumbnail.tile);
			}
		});
	}
}
