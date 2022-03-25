package model.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.properties.ProjectProperties;
import model.tiles.Tile;
import model.tiles.TilesSource;

public class Project implements Serializable{
	private static final long serialVersionUID = 4078651635515023249L;
	private List<TilesSource> tilesSource;
	private List<Tile> tiles;
	private List<Integer> tilesId;
	private ProjectProperties projectProperties;
	private static Project instance;
	
	private Project() {
		this.tilesSource = new LinkedList<TilesSource>();
		this.tiles = new LinkedList<Tile>();
		this.tilesId = new ArrayList<Integer>();
	}
	public static void setProject(Project project) {
		instance = project;
		
	}
	public static Project getInstance() {
		if(instance == null) {
			instance = new Project();
		}
		return instance;
		
	}
	public ProjectProperties getProjectProperties() {
		return projectProperties;
	}
	public void setProjectProperties() {
		this.projectProperties = ProjectProperties.getInstance();
	}
	public void addTilesSource(TilesSource ts) {
		this.tilesSource.add(ts);
	}
	public void removeTilesSource(TilesSource ts) {
		this.tilesSource.remove(ts);
	}
	public List<TilesSource> getTilesSource(){
		return this.tilesSource;
	}
	public void setTilesSource(List<TilesSource> ts) {
		this.tilesSource = ts;
	}
	public void addTile(Tile tile) {
		if(tileExist(tile)) {
			int index = tiles.indexOf(tile);
			tiles.remove(tile);
			tiles.add(index, tile);
		}else {
			this.tiles.add(tile);
		}
		
		
	}
	public void setTilesIds() {
		tiles = getTiles();
		for(Tile t :tiles) {
			tilesId.add(t.getId());
		}
	}
	private boolean tileExist(Tile tile) {
		for(Tile t : tiles) {
			if(t.getId() == tile.getId()) {
				return true;
			}
		}
		return false;
	}
	public void removeTile(Tile tile) {
		this.tiles.remove(tile);
		//tilesId.remove((Integer) tile.getId());
	}
	public List<Tile> getTiles(){
		return this.tiles;
	}
	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
		for(Tile t : tiles) {
			tilesId.add(t.getId());
		}
	}
	public void emptyProject() {
		this.tilesSource = new LinkedList<TilesSource>();
		this.tiles = new LinkedList<Tile>();
	}
	public List<Integer> getTilesId() {
		return tilesId;
	}
	public void addId(int id) {
		tilesId.add(id);
	}
}
 