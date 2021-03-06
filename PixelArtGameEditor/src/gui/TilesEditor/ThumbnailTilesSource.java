package gui.TilesEditor;

import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.TilesSource;

public class ThumbnailTilesSource extends ImageView {
	public ThumbnailTilesSource(TilesSource ts) throws FileNotFoundException {
		
		Image image = ts.getTilesSourceImage().getImage();
		setImage(image);
		setFitWidth(100);
		setPreserveRatio(true);
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					TilesManager.setView(ts);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
