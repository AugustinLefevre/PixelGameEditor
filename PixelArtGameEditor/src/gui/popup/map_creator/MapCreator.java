package gui.popup.map_creator;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.PrefsController;
import controller.ProjectController;
import gui.Tools.FieldValidator;
import gui.map.library.MapLibrary;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.properties.ProjectProperties;
import javafx.scene.control.Button;

public class MapCreator extends Stage implements FieldValidator {
	private static MapCreator instance;
	private Label mapNameLabel;
	private TextField mapName;
	private Spinner<Integer> width;
	private Spinner<Integer> height;
	private MapCreator() {
		super();
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Create new map");
		mapNameLabel = new Label("Map name :");
		mapName = new TextField();
		width = new Spinner<>(5, 500, 0, 1);
		height = new Spinner<>(5, 500, 0, 1);

		
		HBox mapNameField = new HBox();
		HBox mapWidth = new HBox();
		HBox mapHeight = new HBox();
		
		mapNameField.getChildren().addAll(mapNameLabel, mapName);
		mapWidth.getChildren().addAll(new Label("Width :"), width);
		mapHeight.getChildren().addAll(new Label("Height :"), height);

		Button buttonClose= new Button("Cancel"); 
		buttonClose.setOnAction(e -> close());
		
		Button buttonValidate= new Button("OK"); 
		buttonValidate.setOnAction(e -> createMap());
		
		HBox submit = new HBox();
		submit.getChildren().addAll(buttonValidate, buttonClose);
		submit.setPadding(new Insets(0, 0 ,25 ,50));
		submit.setSpacing(50);
		
		VBox form = new VBox();
		form.setPadding(new Insets(50, 0 ,0 ,50));
		form.setSpacing(20);
		form.getChildren().addAll(
				mapNameField,
				mapWidth,
				mapHeight
				);
		
		BorderPane layout = new BorderPane();
		layout.setCenter(form);
		layout.setBottom(submit);
		
		Scene scene1= new Scene(layout, 300, 250);
		setScene(scene1);
	}
	
	private void createMap() {
		String name = mapName.getText();
		if(!textIsValid(name)) {
			mapNameLabel.setTextFill(Color.RED);
		}else {
			BufferedImage bi = new BufferedImage(
					width.getValue() * ProjectProperties.getInstance().getTileSize(),
					height.getValue() * ProjectProperties.getInstance().getTileSize(), 
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D ig2 = bi.createGraphics();
			ig2.setColor(java.awt.Color.lightGray);
			ig2.fillRect(0, 0, width.getValue() * ProjectProperties.getInstance().getTileSize(), height.getValue() * ProjectProperties.getInstance().getTileSize());
			
			try {
				ImageIO.write(bi, "PNG", new File(PrefsController.getInstance().getMapsPath() + "\\" + mapName.getText() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			mapName.setText("");
			mapNameLabel.setTextFill(Color.BLACK);
			close();
			MapLibrary.getInstance().refresh();
		}
	}

	public void display() {
		showAndWait();
	}
	
	public static MapCreator getInstance() {
		if(instance == null) {
			instance = new MapCreator();
		}
		return instance;
	}
	
}
