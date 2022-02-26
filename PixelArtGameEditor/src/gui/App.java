package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Main mainFrame = new Main(1000, 1000);
		stage.setTitle("JavaFX Scene Graph Demo");
	    stage.setScene(mainFrame.getScene());
	    mainFrame.setStage(stage);
	    stage.show(); 
	}

}
