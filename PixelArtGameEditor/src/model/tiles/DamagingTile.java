package model.tiles;

import javafx.scene.image.Image;

public class DamagingTile extends Tile{
	private TileType type;
	private int damage;
	private int delay;
	private int timing;
	
	public DamagingTile() {
		super();
	}
	
	public DamagingTile(Image image) {
		super(image);
	}
	
	@Override
	public void setType() {
		this.type = TileType.damaging;
	}

	@Override
	public TileType getTileType() {
		return this.type;
	}
	/**
	 * Get the force of the damage
	 * @return
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * Set the force of the damage
	 * @param damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	/**
	 * get time between each damage
	 * @return time between each damage
	 */
	public int getDelay() {
		return delay;
	}
	/**
	 * Set time between each damage
	 * @param int delay
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
	/**
	 * how many time the damage will continue
	 * @return int timing
	 */
	public int getTiming() {
		return timing;
	}
	/**
	 * set damage expiration time
	 * @param timing
	 */
	public void setTiming(int timing) {
		this.timing = timing;
	}

}
