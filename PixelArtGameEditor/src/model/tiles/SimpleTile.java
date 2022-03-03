package model.tiles;

import javafx.scene.image.Image;

public class SimpleTile extends Tile{
	private TileType type;
	
	public SimpleTile() {
		super();
	}
	public SimpleTile(Image image) {
		super(image);
	}

	@Override
	public void setType() {
		this.type = TileType.simple;
	}

	@Override
	public TileType getTileType() {
		// TODO Auto-generated method stub
		return this.type;
	}

}
