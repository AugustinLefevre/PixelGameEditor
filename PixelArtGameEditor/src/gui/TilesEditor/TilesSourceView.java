package gui.TilesEditor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
/**
 * @author augustin.lefevre
 * Singleton of the Tiles source viewer displayed in the tiles manager
 */
final class TilesSourceView extends Canvas{
	private Robot robot;
	private TilesSourceViewCursor tilesSourceViewCursor;
	private static boolean  mouseIsIn = false; //check if the mouse is in the tile manager
	private static TilesSourceView instance;//tiles source viewer instance
	private static GraphicsContext gc;
	private Image image;
	public TilesSourceView() {
		super(512, 512);
		robot = new Robot();
		gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		this.tilesSourceViewCursor = TilesSourceViewCursor.getInstance();
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// set tile popup
				
			}
			
		});
		addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent e) {
				if(mouseIsIn) {
					double zoomFactor = 1.05;
					double deltaY = e.getDeltaY();
					if(deltaY < 0)
						zoomFactor = 2 - zoomFactor;
					double zoom = TilesSourceView.this.getWidth() * zoomFactor;
					TilesSourceView.this.setWidth(TilesSourceView.this.getWidth() * zoomFactor);
					TilesSourceView.this.setHeight(TilesSourceView.this.getHeight() * zoomFactor);
					TilesSourceView.this.gc.drawImage(TilesSourceView.this.image, 0, 0, zoom, zoom);
				}

			}
		});
	}
	
	/**
	 * display the tiles source image in the tiles source viewer 
	 * @param image
	 */
	public void displaySource(Image image) {
		this.image = image;
		gc.drawImage(image, 0, 0, 512, 512);
	}
	/**
	 * Get the tile source viewer instance
	 * @return TilesSourceView
	 */
	public static TilesSourceView getInstance() {
		if(instance == null) {
			return new TilesSourceView();
		}
		return instance;
	}
}
