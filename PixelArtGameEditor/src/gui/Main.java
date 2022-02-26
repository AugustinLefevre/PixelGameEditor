package gui;

import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main {
	private Group root;
	private Scene scene;
	private Stage stage;
	public Main() throws FileNotFoundException {
		root = new Group();
	    scene = new Scene(root, Color.WHITESMOKE);
	    
	    root.getChildren().add(MenuPanel.getInstance(root).getMenuBar());
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
}
