package gui.TilesEditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.robot.Robot;

public class TilesSourceViewCursor extends ImageView {
	private static TilesSourceViewCursor instance;
	private TilesSourceViewCursor() {
		try {
			Robot robot =  new Robot();
			String path = System.getProperty("user.dir") + "\\resources";
			InputStream stream = new FileInputStream(path + "\\tilesSelectorCursor256.png");
			Image image = new Image(stream);
			setImage(image);
			setFitWidth(50);
			
			setPreserveRatio(true);
			ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
			
			Runnable sheduledTask = () -> {
				Platform.runLater(()->{
					try {
						setPosition(robot.getMousePosition());
					}catch(Exception e){
						System.out.println("error in scheduler" + e);
					}
				});
			};
			ses.scheduleAtFixedRate(sheduledTask, 0, 50, TimeUnit.MILLISECONDS);

		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void setPosition(Point2D position) {
		try {
			double windowPosX = (getScene()==null)?0 : getScene().getWindow().getX();
			double windowPosY = (getScene()==null)?0 : getScene().getWindow().getY();
			double scenePosX = (getScene()==null)?0 : getScene().getX();
			double scenePosY = (getScene()==null)?0 : getScene().getY();
			double posX = position.getX() - windowPosX - localToScene(0, 0).getX() - scenePosX;
			double posY = position.getY() - windowPosY - localToScene(0, 0).getY() - scenePosY;
			setX(posX);
			setY(posY);
		}catch(Exception e) {
			System.out.println("error on set cursor position" + e);
		}
	}
	public void switchVisibility(boolean visibility) {
		try{
			setVisible(visibility);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static TilesSourceViewCursor getInstance() {
		if(instance == null) {
			instance = new TilesSourceViewCursor();
			return instance;
		}
		return instance;
	}
}
