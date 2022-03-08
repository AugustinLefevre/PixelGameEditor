package model.project;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import model.properties.ProjectProperties;
import model.tiles.TilesSource;

public class Project implements Serializable{
	private static final long serialVersionUID = 4078651635515023249L;
	private List<TilesSource> tilesSource;
	private ProjectProperties projectProperties;
	//private TilesSourceDatabase tilesSourceDataBase;
	private static Project instance;
	
	private Project() {
		//this.tilesSourceDataBase = TilesSourceDatabase.getInstance();
		this.tilesSource = new LinkedList<TilesSource>();
	}
	public void setProject(Project project) {
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
	public List<TilesSource> getTilesSource(){
		return this.tilesSource;
	}
	public void setTilesSource(List<TilesSource> ts) {
		this.tilesSource = ts;
	}
}