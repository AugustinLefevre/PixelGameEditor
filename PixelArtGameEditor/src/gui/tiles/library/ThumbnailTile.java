package gui.tiles.library;

import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.tiles.Tile;

public class ThumbnailTile extends Canvas {
	Tile tile;
	public ThumbnailTile(Tile tile) throws FileNotFoundException {
		super(50, 50);
		this.tile = tile;
		Image image = tile.getTilesSourceImage().getImage();
		GraphicsContext gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		gc.drawImage(image, tile.getPositionX(), tile.getPositionY(), tile.getWidth(), tile.getHeight(), 0, 0, 50 , 50);
	}
}