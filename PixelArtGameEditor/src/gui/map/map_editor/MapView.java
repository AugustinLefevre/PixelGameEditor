package gui.map.map_editor;

import gui.Main;
import gui.tiles.tiles_editor.TilesSourceViewCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MapView extends Canvas {
	private static MapView instance;
	private static GraphicsContext gc;
	private float currentPositionX = 0;
	private float currentPositionY = 0;

	private int currentPXSize = 0;
	private int currentMapWidth;
	private int currentMapHeight;
	private float pxSize;
	private Image image;
	private float imageSizeX;
	private float imageSizeY;
	private float sizer;
	private int resolution;
	
	public MapView() {
		super(12,12);
		
		gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		setAction();
		setSize();
	}
	
	public void displayMap(Image image) {
		drawBackground();
		this.image = image;
		this.currentPXSize = (int) image.getWidth();
		if((int) getWidth() - getWidth()%image.getWidth() != 0) {
			currentMapWidth =  (int) (getWidth() - getWidth()%image.getWidth())*4;
			currentMapHeight =  (int) (getHeight() - getHeight()%image.getHeight())*4;
		}else {
			currentMapWidth =(int) getWidth()*4;
			currentMapWidth =(int) getHeight()*4;
		}
		setPxSize();
		this.imageSizeX = currentMapWidth;
		this.imageSizeY = (float) (image.getHeight() * this.pxSize);
		gc.drawImage(this.image,
				0,
				0,
				this.imageSizeX,
				this.imageSizeY);
		//TilesSourceViewCursor.getInstance().setSize();
		setCursorParams();
	}
	private void setAction() {
		
	}
	/**
	 * set displayer size
	 */
	private void setSize() {
		int width = (int) Main.getInstance().getScene().getWindow().getWidth() - 250;
		int height = (int) Main.getInstance().getScene().getWindow().getHeight() - 120;
		setWidth(width);
		setHeight(height);
	}
	private void setPxSize() {
		this.pxSize = (float) (this.currentMapWidth / this.image.getWidth());
	}
	private static void drawBackground() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 2000, 2000);
	}
	
	private void setCursorParams() {
		sizer = getCurrentPXSize() / getCurrentMapWidth();
		resolution = (int) (getCurrentMapWidth() / getCurrentPXSize());
	}
	public int getCurrentPXSize() {
		return currentPXSize;
	}
	public int getCurrentMapWidth() {
		return currentMapWidth;
	}
	public static MapView getInstance() {
		if(instance == null) {
			instance = new MapView();
		}
		return instance;
	}
}
