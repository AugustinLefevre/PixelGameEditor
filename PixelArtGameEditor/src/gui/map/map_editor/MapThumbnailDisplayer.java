package gui.map.map_editor;

import java.io.FileNotFoundException;
import java.io.IOException;

import controller.PrefsController;
import controller.ProjectController;
import gui.icon.RedCross;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.tiles.TilesSource;

public class MapThumbnailDisplayer extends BorderPane{

	private BorderPane buttonDelete;
	
	public MapThumbnailDisplayer(Image img) {
		
		//try {
			buttonDelete = new BorderPane();
			buttonDelete.setPadding(new Insets(0));
			buttonDelete.setCenter(RedCross.getInstance());
			setCenter(new MapThumbnail(img));
			getChildren().add(buttonDelete);
			
			buttonDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					//ProjectController.getInstance().removeTilesSource(ThumbnailTilesSourceDisplayer.this.ts);
					try {
						//TilesManager.getInstance().tilesSourceThumbnailColumnRefresh();
						PrefsController.getInstance().setConfirm(true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
	}
	
}
