package gui.tiles.library;

import gui.popup.tile_editor.TileEditor;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class TilesLibraryEditable extends TilesLibrary {
	public TilesLibraryEditable() {
		super();
	}

	@Override
	public void addListener(ThumbnailTile thumbnail) {
		thumbnail.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				TileEditor.getInstance(thumbnail.tile).setVisible(true);
			}
		});
	}

	
}
