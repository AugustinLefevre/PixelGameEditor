package gui.TilesEditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TilesSourceViewCursor extends ImageView {
	private static TilesSourceViewCursor instance;
	private TilesSourceViewCursor() {
		try {
			String path = System.getProperty("user.dir") + "\\resources";
			InputStream stream = new FileInputStream(path + "\\tilesSelectorCursor512.png");
			Image image = new Image(stream);
			setImage(image);
			setFitWidth(50);
			setPreserveRatio(true);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void setPosition(Point2D position) {
//		setX(position.getX());
//		setY(position.getY());
		System.out.println("pos");
	}
	public void switchVisibility(boolean visibility) {
		//setVisible(visibility);
		System.out.println("visibility :" + visibility);
	}
	public static TilesSourceViewCursor getInstance() {
		if(instance == null) {
			return new TilesSourceViewCursor();
		}
		return instance;
	}
}
