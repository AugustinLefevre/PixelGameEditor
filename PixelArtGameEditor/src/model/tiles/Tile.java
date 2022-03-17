package model.tiles;

import java.io.Serializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.properties.ProjectProperties;

public class Tile implements Serializable {
	private static final long serialVersionUID = 1190551685062006786L;
	private static int count = 0;
	private int id;
	private int width;
	private int height;
	private TilesSourceImage tilesSourceImage;
	private String path;
	private float positionX;
	private float positionY;
	public Tile() {
		this.id = count++;
		this.width = ProjectProperties.getInstance().getTileSize();
		this.height = ProjectProperties.getInstance().getTileSize();
	}
	public Tile(String path, float positionX, float positionY) {
		//this.canvas = new Canvas(50, 50);
		this.id = count++;
		this.width = ProjectProperties.getInstance().getTileSize();
		this.height = ProjectProperties.getInstance().getTileSize();
		this.path = path;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public float getPositionX() {
		return positionX;
	}
	public float getPositionY() {
		return positionY;
	}
	public TilesSourceImage getTilesSourceImage() {
		if(tilesSourceImage == null) {
			tilesSourceImage = new TilesSourceImage(this.path);
			return tilesSourceImage;
		}
		return tilesSourceImage;
	}
	public int getId() {
		return this.id;
	}
}
