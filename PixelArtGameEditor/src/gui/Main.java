package gui;

import java.io.IOException;

import gui.popup.new_project.NewProject;
import gui.popup.tile_editor.TileEditor;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main {
	private Group root;
	private Scene scene;
	private Stage stage;
	private static Main instance;
	private Main() throws IOException {
		root = new Group();
	    scene = new Scene(root, Color.WHITESMOKE);
	    Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				//root.getChildren().add(TilesManager.getInstance());
				//root.getChildren().add(MenuPanel.getInstance().getMenuBar());
				
		        VBox vbox = new VBox();
		        vbox.getChildren().addAll(MenuPanel.getInstance(), AppTab.getInstance());
		        root.getChildren().addAll(vbox);
				root.getChildren().add(NewProject.getInstance());
				root.getChildren().add(TileEditor.getInstance());
			}
		});
	    
	    
	}
	public Group getRoot() {
		return root;
	}
	public Scene getScene() {
		return scene;
	}
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public static Main getInstance(){
		if(instance == null) {
			try {
				instance = new Main();
			} catch (IOException e) {
				System.err.println(e);
			}
		}
		return instance;
		
	}
	public static void setOnRoot(Node node) throws IOException {
		Main.getInstance().getRoot().getChildren().add(node);
	}
}
