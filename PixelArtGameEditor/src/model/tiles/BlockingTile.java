package model.tiles;

import javafx.scene.image.Image;

public class BlockingTile extends Tile{
	private TileType type;

	public BlockingTile() {
		super();
	}
	
	public BlockingTile(Image image) {
		super(image);
	}

	@Override
	public void setType() {
		this.type = TileType.blocking;
		
	}

	@Override
	public TileType getTileType() {
		
		return this.type;
	}

}
