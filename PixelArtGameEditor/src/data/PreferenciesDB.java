package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import model.properties.Prefs;
import model.tiles.TilesSource;


public class PreferenciesDB {
	private static PreferenciesDB instance;
	//private Prefs prefs;
	private File file;
	private String resourcePath;
	//private OutputStream stream;
	
	private PreferenciesDB() throws FileNotFoundException {
		this.resourcePath = System.getProperty("user.dir") + "\\resources";
		this.file = new File(this.resourcePath + "\\preferencies.prefs");
	}
	
	public void savePrefs(Prefs prefs) throws IOException {
		FileOutputStream fos = new FileOutputStream(this.file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(prefs);
		oos.close();
	}
	public static PreferenciesDB getInstance() throws FileNotFoundException {
		if(instance == null) {
			instance = new PreferenciesDB();
		}
		return instance;
		
	}


	
	public Prefs loadPrefs() throws IOException {
		FileInputStream fis = new FileInputStream(this.file);
		
        if (this.file.length() > 0) {
			ObjectInputStream ois = new ObjectInputStream(fis);
			try {
				Prefs prefs = (Prefs)ois.readObject();
				ois.close();
				return prefs;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				ois.close();
				return null;
			}
		
		}
        fis.close();
		return null;
		
	}
}
