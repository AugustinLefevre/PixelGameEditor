package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main mainFrame = new Main();
		stage.setTitle("Pixel Art Game Editor");
	    stage.setScene(mainFrame.getScene());
	    //stage.setFullScreen(true);
	    stage.setMaximized(true);
	    mainFrame.setStage(stage);
	    stage.show(); 
	}

}
