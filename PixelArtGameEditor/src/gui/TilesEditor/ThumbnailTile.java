package gui.TilesEditor;

import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.tiles.Tile;

public class ThumbnailTile extends Canvas {
	public ThumbnailTile(Tile tile) throws FileNotFoundException {
		super(50, 50);
		Image image = tile.getTilesSourceImage().getImage();
		GraphicsContext gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		gc.drawImage(image, tile.getPositionX(), tile.getPositionY(), tile.getWidth(), tile.getHeight(), 0, 0, 50 , 50);
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
			}
		});
	}
}