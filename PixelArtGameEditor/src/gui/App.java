package gui;

import java.io.IOException;

import controller.PrefsController;
import gui.TilesEditor.TilesManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main mainFrame = Main.getInstance();
		stage.setTitle("Pixel Art Game Editor");
	    stage.setScene(mainFrame.getScene());
	    stage.setMaximized(true);
	    mainFrame.setStage(stage);
	    stage.show();	
		PrefsController.getInstance(mainFrame.getRoot()).openAutoProject();
		TilesManager.getInstance().leftColumnRefresh();

	}

}
