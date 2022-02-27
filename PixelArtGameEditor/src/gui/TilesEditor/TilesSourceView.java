package gui.TilesEditor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.EventHandler;
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
		gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		tilesSourceViewCursor = TilesSourceViewCursor.getInstance();
		mouseInScene();
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		
		Runnable sheduledTask = () -> {
			System.out.println("jkjjk");
		//	if(mouseIsIn) {
				
				//tilesSourceViewCursor.setPosition(robot.getMousePosition());
		//	}
			
		};
		ses.scheduleAtFixedRate(sheduledTask, 0, 1000, TimeUnit.MILLISECONDS);
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("hello");
				
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
					//System.out.println("hello" + zoom);
					TilesSourceView.this.setWidth(TilesSourceView.this.getWidth() * zoomFactor);
					TilesSourceView.this.setHeight(TilesSourceView.this.getHeight() * zoomFactor);
					TilesSourceView.this.gc.drawImage(TilesSourceView.this.image, 0, 0, zoom, zoom);
				}

			}
		});
	}
	/**
	 * Update boolean mouseIsIn
	 */
	public void mouseInScene() {
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("enter");
				tilesSourceViewCursor.switchVisibility(true);
				mouseIsIn = true;
			}
		});
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				System.out.println("exit");
				tilesSourceViewCursor.switchVisibility(false);
				mouseIsIn = true;
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
