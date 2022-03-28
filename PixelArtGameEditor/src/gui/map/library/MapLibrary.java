package gui.map.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.PrefsController;
import gui.map.map_editor.MapManager;
import gui.map.map_editor.MapThumbnail;
import gui.map.map_editor.MapThumbnailDisplayer;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MapLibrary extends ScrollPane {
	private VBox displayer;
	private static MapLibrary instance;
	
	private MapLibrary() {
		super();
		setPrefWidth(115);
//		setPrefHeight(500);
//		setMaxHeight(500);
		this.displayer = new VBox();
		this.displayer.getChildren().add(ButtonAdd.getInstance());
		this.displayer.setSpacing(10);
		setContent(this.displayer);
	}
	
	public static MapLibrary getInstance() {
		if(instance == null) {
			instance = new MapLibrary();
		}
		return instance;
	}
	public void refresh() {
		this.displayer.getChildren().clear();
		this.displayer.getChildren().add(ButtonAdd.getInstance());
		try {
			File[] maps = new File(PrefsController.getInstance().getMapsPath()).listFiles();
			for(File map: maps) {
				System.out.println(map.getAbsolutePath());
					Image img = new Image(new FileInputStream(map.getAbsolutePath()));

					this.displayer.getChildren().add(new MapThumbnailDisplayer(img));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
