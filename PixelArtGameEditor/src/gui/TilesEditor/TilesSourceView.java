package gui.TilesEditor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
/**
 * @author augustin.lefevre
 * Singleton of the Tiles source viewer displayed in the tiles manager
 */
final class TilesSourceView extends ImageView{
	private static boolean  mouseIsIn = false; //check if the mouse is in the tile manager
	private static TilesSourceView instance;//tiles source viewer instance
	private TilesSourceView() {	
		
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		
		Runnable sheduledTask = () -> {
			addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					mouseIsIn = true;
				}
			});
			addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					mouseIsIn = false;
				}
			});
		};
		ses.scheduleAtFixedRate(sheduledTask, 0, 10000, TimeUnit.MILLISECONDS);
		
		addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent e) {
				if(mouseIsIn) {
					double zoomFactor = 1.05;
					double deltaY = e.getDeltaY();
					if(deltaY < 0)
						zoomFactor = 2 - zoomFactor;
					TilesSourceView.this.setFitWidth(TilesSourceView.this.getFitWidth() * zoomFactor);

				}

			}
		});
	}
	/**
	 * display the tiles source image in the tiles source viewer 
	 * @param image
	 */
	public void displaySource(Image image) {
		setImage(image);
		setFitWidth(500);
		setPreserveRatio(true);
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
