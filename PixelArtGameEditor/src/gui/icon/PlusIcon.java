package gui.icon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlusIcon extends ImageView {
	
	public static PlusIcon instance = null;
	
	private PlusIcon(Image img, int size) {
		super(img);
		setFitWidth(size);
		setFitHeight(size);
		
	}
	 public static PlusIcon getInstance(int size) {
		 if(instance == null) {
			 try {
					String path = System.getProperty("user.dir") + "\\resources";
					InputStream stream = new FileInputStream(path + "\\plus.png");
					Image image = new Image(stream);
					return new PlusIcon(image, size);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		 }
		 return instance;
	 }
}
