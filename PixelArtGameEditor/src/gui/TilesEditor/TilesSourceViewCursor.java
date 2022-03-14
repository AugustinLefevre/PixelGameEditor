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
import model.properties.ProjectProperties;

public class TilesSourceViewCursor extends ImageView {
	private static TilesSourceViewCursor instance;
	private boolean clipped;
	private TilesSourceViewCursor() {
		try {
			//Robot robot =  new Robot();
			setMouseTransparent(true);
			clipped = true;
			String path = System.getProperty("user.dir") + "\\resources";
			InputStream stream = new FileInputStream(path + "\\tilesSelectorCursor256.png");
			Image image = new Image(stream);
			setImage(image);

			setFitWidth(50);
			
			setPreserveRatio(true);
			ScheduledExecutorService ses = Executors.newScheduledThreadPool(1000);
			
			Runnable sheduledTask = () -> {
				Platform.runLater(()->{
					try {
						modifyPosition();
					}catch(Exception e){
						System.out.println("error in scheduler :" + e);
					}
				});
			};
			ses.scheduleAtFixedRate(sheduledTask, 0, 50, TimeUnit.MILLISECONDS);

		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public void modifyPosition() {
		try {
			if(!clipped) {
				try {
					setX(TilesSourceView.getInstance().getMousePositionX());
					setY(TilesSourceView.getInstance().getMousePositionY());
				}catch(Exception e) {
					System.out.println("error on set cursor position" + e);
				}
			}
			else {
				double sizer = TilesSourceView.getInstance().getSizer();
	
				double clippedPosX = Math.floor(TilesSourceView.getInstance().getMousePositionX() * sizer);
				double clippedPosY = Math.floor(TilesSourceView.getInstance().getMousePositionY() * sizer);
	
				double res = TilesSourceView.getInstance().getResolution();
	
				try {
					setX(clippedPosX * (res) );
					setY(clippedPosY * (res) );
				}catch(Exception e) {
					System.out.println("error on set cursor position" + e);
				}
			}
			
			
		}catch(Exception e){
			System.out.println("error in scheduler :" + e);
		}
		
	}
	public void setSize() {
		double tempCursorSize = (TilesSourceView.getInstance().getWidth() /
				TilesSourceView.getInstance().getCurrentPXSize() * 
				ProjectProperties.getInstance().getTileSize());
		setFitWidth(tempCursorSize);
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
	public double getCursorSize() {
		return getFitWidth();
	}
	public void setCursorSize(double size) {
		setFitWidth(size);
	}
}
