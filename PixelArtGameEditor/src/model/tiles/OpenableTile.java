package model.tiles;

import javafx.scene.image.Image;

public class OpenableTile extends Tile {
	private TileType type;
	private TileLinkType linkType;
	// ajouter link
	public OpenableTile() {
		super();
	}
	
	public OpenableTile(Image image) {
		super(image);
	}

	@Override
	public void setType() {
		this.type = TileType.linked;
	}

	@Override
	public TileType getTileType() {
		return this.type;
	}

	public TileLinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(TileLinkType linkType) {
		this.linkType = linkType;
	}
}
