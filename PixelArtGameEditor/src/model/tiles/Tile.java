package model.tiles;

import java.io.Serializable;
import java.util.List;

import controller.ProjectController;
import model.properties.ProjectProperties;

public class Tile implements Serializable {
	private static final long serialVersionUID = 1190551685062006786L;
	private static int count = 0;
	private int id;
	private int width;
	private int height;
	private transient TilesSourceImage tilesSourceImage;
	private String path;
	private int positionX;
	private int positionY;
	private TilesType type;
	public Tile() {
		
		this.id = count++;
		List<Integer> ids = ProjectController.getInstance().getTilesId();
		while(ids.contains(this.id)) {
			this.id++;
		}
		this.width = ProjectProperties.getInstance().getTileSize();
		this.height = ProjectProperties.getInstance().getTileSize();
	}
	public Tile(String path, int positionX, int positionY, TilesType type) {
		this.id = count++;
		List<Integer> ids = ProjectController.getInstance().getTilesId();
		
		while(ids.contains(this.id)) {
			this.id++;
		}
		ProjectController.getInstance().addId(this.id);
		this.width = ProjectProperties.getInstance().getTileSize();
		this.height = ProjectProperties.getInstance().getTileSize();
		this.path = path;
		this.positionX = positionX;
		this.positionY = positionY;
		this.type = type;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getPositionX() {
		return positionX;
	}
	public int getPositionY() {
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
	public String getPath() {
		return path;
	}
	public TilesType getType() {
		return this.type;
	}
	public void setParams(String src, int positionX, int positionY, TilesType type) {
		this.path = src;
		this.positionX = positionX;
		this.positionY = positionY;
		this.type = type;
	}
}
