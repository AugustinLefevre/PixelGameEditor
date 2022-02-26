package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;

public class TilesSourceImage {
	private Image image;
	
	public TilesSourceImage(String src) {
		try {
			InputStream stream = new FileInputStream(src);
			this.image = new Image(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
