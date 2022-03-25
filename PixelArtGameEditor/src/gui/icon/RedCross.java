package gui.icon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RedCross extends ImageView {
	
	public static RedCross instance = null;
	
	public RedCross(Image img) {
		
		super(img);
		setFitWidth(15);
		setFitHeight(15);
		
	}
	 public static RedCross getInstance() {
		 if(instance == null) {
			 try {
					String path = System.getProperty("user.dir") + "\\resources";
					InputStream stream = new FileInputStream(path + "\\redCross.png");
					Image image = new Image(stream);
					return new RedCross(image);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		 return instance;
	 }
}
