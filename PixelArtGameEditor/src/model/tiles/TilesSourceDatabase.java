package model.tiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TilesSourceDatabase {
	private List<TilesSource> tilesSources;
	
	public TilesSourceDatabase() {
		tilesSources = new LinkedList<TilesSource>();
	}
	public void addTilesSource(TilesSource ts) {
		tilesSources.add(ts);
	}
	public void removeTilesSource(int index) {
		tilesSources.remove(index);
	}
	
	public List<TilesSource> getTilesSources(){
		return Collections.unmodifiableList(tilesSources);
	}
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		TilesSource[] TSources = tilesSources.toArray(new TilesSource[tilesSources.size()]);
		
		oos.writeObject(TSources);
		oos.close();
	}
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			TilesSource[] TSources = (TilesSource[])ois.readObject();
			tilesSources.clear();
			tilesSources.addAll(Arrays.asList(TSources));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ois.close();
	}
}
