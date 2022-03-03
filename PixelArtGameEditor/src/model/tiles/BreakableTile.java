package model.tiles;

import javafx.scene.image.Image;

public class BreakableTile extends Tile {
	private TileType type;
	private int nbOfHit;
	// ajouter animation et images intermédiaires.
	
	public BreakableTile() {
		super();
	}
	
	public BreakableTile(Image image) {
		super(image);
	}
	
	@Override
	public void setType() {
		this.type = TileType.breakableTile;
	}

	@Override
	public TileType getTileType() {
		return this.type;
	}

	public int getNbOfHit() {
		return nbOfHit;
	}

	public void setNbOfHit(int nbOfHit) {
		this.nbOfHit = nbOfHit;
	}
	
	public void hit() {
		this.nbOfHit = this.nbOfHit - 1;
	}

	
}
