package gui.TilesEditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.properties.ProjectProperties;

public class TilesSourceViewCursor extends ImageView {
	private static TilesSourceViewCursor instance;
	private boolean clipped;
	private float deltaX = 0;
	private float deltaY = 0;
	private int actualPositionX = 0;
	private int actualPositionY = 0;
	private double relativPositionX;
	private double relativPositionY;
	private int posInTilesSourceX;
	private int posInTilesSourceY;
	private TilesSourceViewCursor() {
		try {
			setMouseTransparent(true);
			clipped = true;
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
						modifyPosition();
					}catch(Exception e){
						System.err.println("error in scheduler :" + e);
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
					System.err.println("error on set cursor position" + e);
				}
			}
			else {
				double sizer = TilesSourceView.getInstance().getSizer();
	
				int clippedPosX = (int)(TilesSourceView.getInstance().getMousePositionX() * sizer);
				int clippedPosY = (int)(TilesSourceView.getInstance().getMousePositionY() * sizer);
				
				int res = TilesSourceView.getInstance().getResolution();
	
				try {
					setX(clippedPosX * (res) + deltaX);
					setY(clippedPosY * (res) + deltaY);
					this.posInTilesSourceX = (int) clippedPosX - (int) (TilesSourceView.getInstance().getCurrentPositionX()/res);
					this.posInTilesSourceY = (int) clippedPosY - (int) (TilesSourceView.getInstance().getCurrentPositionY()/res);
				
					int maxDeplacement = TilesSourceView.getInstance().getCurrentPXSize() - ProjectProperties.getInstance().getTileSize();
					
					if(this.posInTilesSourceX < 0) {
						setX(TilesSourceView.getInstance().getCurrentPositionX());
						this.posInTilesSourceX = 0;
					}
					if(this.posInTilesSourceY < 0) {
						setY(TilesSourceView.getInstance().getCurrentPositionY());
						this.posInTilesSourceY = 0;
					}
					if(this.posInTilesSourceX > maxDeplacement) {
						setX(TilesSourceView.getInstance().getCurrentPositionX() + maxDeplacement * res);
						this.posInTilesSourceX = maxDeplacement;
					}
					if(this.posInTilesSourceY > maxDeplacement) {
						setY(TilesSourceView.getInstance().getCurrentPositionY() + maxDeplacement * res);
						this.posInTilesSourceY = maxDeplacement;
					}
					
				}catch(Exception e) {
					System.err.println("error on set cursor position" + e);
				}
			}
			
		}catch(Exception e){
			System.err.println("error in scheduler :" + e);
		}
		
	}
	public void setSize() {
		double tempCursorSize = (
				TilesSourceView.getInstance().getImageWidth() /
				TilesSourceView.getInstance().getCurrentPXSize() * 
				ProjectProperties.getInstance().getTileSize());
		setFitWidth(tempCursorSize);
	}
	public void switchVisibility(boolean visibility) {
		try{
			setVisible(visibility);
		}catch(Exception e) {
			System.err.println(e);
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
	public void setDelta(float X, float Y) {
		this.deltaX = (float) (X % TilesSourceView.getInstance().getResolution());
		this.deltaY = (float) (Y % TilesSourceView.getInstance().getResolution());
	}
	public float getDeltaX() {
		return deltaX;
	}
	public float getDeltaY() {
		return deltaY;
	}
	public int getActualPositionX() {
		return actualPositionX;
	}
	public int getActualPositionY() {
		return actualPositionY;
	}
	public double getRelativPositionX() {
		return relativPositionX;
	}
	public double getRelativPositionY() {
		return relativPositionY;
	}
	public int getPosInTilesSourceX() {
		return posInTilesSourceX;
	}
	public int getPosInTilesSourceY() {
		return posInTilesSourceY;
	}
}
