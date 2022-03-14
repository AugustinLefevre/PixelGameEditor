package gui.TilesEditor;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.jdi.event.Event;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.robot.Robot;
import model.properties.ProjectProperties;
/**
 * @author augustin.lefevre
 * Singleton of the Tiles source viewer displayed in the tiles manager
 */
class TilesSourceView extends Canvas{
	//private Robot robot;
	//private TilesSourceViewCursor tilesSourceViewCursor;
	private static boolean  mouseIsIn = false; //check if the mouse is in the tile manager
	private static TilesSourceView instance;//tiles source viewer instance
	private static GraphicsContext gc;
	private Image image;
	private int currentPXSize = 0;
	private int currentTilesSourceSize;
	private int mousePositionX;
	private int mousePositionY;
	private float sizer = 0;
	private int resolution = 0;
	public boolean isScrolling = false;
	private float additivPositionX = 0;
	private float positionX = 0;
	private float additivPositionY = 0;
	private float currentPositionX = 0;
	private float currentPositionY = 0;
	private float positionY = 0;
	
	public TilesSourceView() {
		
		super(512, 512);
		gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		
		addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mousePositionX = (int) e.getX();
				mousePositionY = (int) e.getY();	
			}
		});
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//System.out.println("click _________________________________________" + e.getX());
			}
			
		});
		
		addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				TilesSourceView.this.additivPositionX = (float) (e.getX() - TilesSourceView.this.positionX);
				TilesSourceView.this.additivPositionY = (float) (e.getY() - TilesSourceView.this.positionY);
				modifyPosition(TilesSourceView.this.additivPositionX + TilesSourceView.this.currentPositionX , TilesSourceView.this.additivPositionY + TilesSourceView.this.currentPositionY );
			}
		});
	
		addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TilesSourceView.this.currentPositionX += (float) TilesSourceView.this.additivPositionX;
				TilesSourceView.this.currentPositionY += (float) TilesSourceView.this.additivPositionY;
			}
		});
		addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TilesSourceView.this.positionX = (float) e.getX();
				TilesSourceView.this.positionY = (float) e.getY();
			}
		});
		
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mouseIsIn = true;
				
			}
		});
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				mouseIsIn = false;
			}
		});
		addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent e) {
				boolean canZoom = (getWidth() > 512 )? true: e.getDeltaY() > 0;
				boolean canDezoom = (getWidth() < 1512 )? true: e.getDeltaY() < 0;
				if(mouseIsIn && canZoom && canDezoom) {
					double zoomFactor = 1.05;
					double deltaY = e.getDeltaY();
					if(deltaY < 0)
						zoomFactor = 2 - zoomFactor;
					
					double newSizeX = TilesSourceView.this.getWidth() * zoomFactor
							- ((
									Math.round(TilesSourceView.this.getWidth() * 100) / 100
									
									* zoomFactor)
							% image.getWidth());
					
					double newSizeY = TilesSourceView.this.getHeight() * zoomFactor
							- ((
									Math.round(TilesSourceView.this.getHeight() * 100) / 100
									* zoomFactor)
							% image.getHeight());
					
					TilesSourceView.this.setWidth(newSizeX);
					TilesSourceView.this.setHeight(newSizeY);
					TilesSourceView.this.gc.drawImage(TilesSourceView.this.image, 0, 0, newSizeX, newSizeY);
					TilesSourceView.this.currentTilesSourceSize = (int) newSizeX;
					TilesSourceViewCursor.getInstance().setSize();
					
					setCursorParams();
				}

			}
		});
	}
	public double getSizer() {
		return sizer;
	}
	public double getResolution() {
		return resolution;
	}
	
	/**
	 * display the tiles source image in the tiles source viewer 
	 * @param image
	 */
	public void displaySource(Image image) {
		this.image = image;
		this.currentPXSize = (int) image.getWidth();
		currentTilesSourceSize =  (int) (getWidth() - getWidth()%image.getWidth());
		gc.drawImage(image, 0, 0, currentTilesSourceSize, currentTilesSourceSize);
		TilesSourceViewCursor.getInstance().setSize();
		setCursorParams();
	}
	public void setCursorParams() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				float width = (float)getWidth();
		
				sizer = currentPXSize / width;
				sizer =  (Math.round(sizer*1000)/1000f);
				resolution = (int) (width / currentPXSize);
				
			}
		});
		
	}
	/**
	 * Get the tile source viewer instance
	 * @return TilesSourceView
	 */
	public static TilesSourceView getInstance() {
		if(instance == null) {
			instance = new TilesSourceView();
		}
		return instance;
	}
	private void modifyPosition(double positionX, double positionY) {
		gc.drawImage(image, positionX, positionY, currentTilesSourceSize, currentTilesSourceSize);
	}
	public int getCurrentPXSize() {
		return currentPXSize;
	}
	public float getCurrentTilesSourceSize() {
		return currentTilesSourceSize;
	}
	public int getMousePositionX() {
		return mousePositionX;
	}
	public int getMousePositionY() {
		return mousePositionY;
	}

}
