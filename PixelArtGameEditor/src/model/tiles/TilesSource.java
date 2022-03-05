package model.tiles;

import java.io.Serializable;

public class TilesSource implements Serializable {
	
	private static final long serialVersionUID = 3271609716365386466L;
	private static  int id = 0;
	private int width;
	private int height;
	private String path;

	private transient TilesSourceImage tilesSourceImage;
	public TilesSource() {
		TilesSource.id++;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public TilesSourceImage getTilesSourceImage() {
		if(tilesSourceImage == null) {
			tilesSourceImage = new TilesSourceImage(this.path);
			return tilesSourceImage;
		}
		return tilesSourceImage;
	}

	public static int getId() {
		return id;
	}
	
}
