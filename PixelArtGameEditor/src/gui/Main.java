package gui;

import java.io.IOException;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main {
	private Group root;
	private Scene scene;
	private Stage stage;
	private static Main instance;
	public Main() throws IOException {
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
	public static Main getInstance() throws IOException {
		if(instance == null) {
			instance = new Main();
			return instance;
		}else {
			return instance;
		}
	}
	public static void setOnRoot(Node node) throws IOException {
		Main.getInstance().getRoot().getChildren().add(node);
	}
}
