package model.tiles;

import com.sun.javafx.geom.Point2D;

import javafx.scene.image.Image;

public class Tile {
	private static int id = 0;
	Point2D position;
	int width;
	int height;
	Image source;
	public Tile() {
		id++;
	}
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Image getSource() {
		return source;
	}
	public void setSource(Image source) {
		this.source = source;
	}
	public static int getId() {
		return id;
	}
}
