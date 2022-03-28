package gui.map.map_editor;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class MapThumbnail extends ImageView {
	private Image img;
	public MapThumbnail(Image img) {
		super(img);
		this.img = img;
		setFitWidth(100);
		setPreserveRatio(true);
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println(img.getHeight());
				MapManager.getInstance().update(MapThumbnail.this.img);
				
			}
		});
	}

}
