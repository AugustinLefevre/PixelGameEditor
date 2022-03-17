package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

import model.properties.ProjectProperties;
import model.tiles.Tile;
public class TileTest {
	@Test
	public void TileConstructorTest() {
		Tile tileTest = new Tile();
		assertNotNull(tileTest, "tilecontructor doesn't work properly");
	}
	@Test
	public void getWidth() {
		ProjectProperties.getInstance().setTileSize(32);
		Tile tileTest = new Tile();
		assertEquals(tileTest.getWidth(), 32, "the width of the tile is not what it's expected");
	}
	@Test
	public void getHeight() {
		ProjectProperties.getInstance().setTileSize(32);
		Tile tileTest = new Tile();
		assertEquals(tileTest.getHeight(), 32, "the width of the tile is not what it's expected");
	}
	@Test
	public void getSource() {
		
	}
	@Test
	public void setSource() {
		
	}
	@Test
	public void getId() {
		Tile tileTest = new Tile();
		Tile tileTest1 = new Tile();
		assertEquals(tileTest.getId() + 1, tileTest1.getId(), "Tiles id is not what it's expected");
	}
}
