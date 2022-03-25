package gui;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

import controller.PrefsController;
import controller.ProjectController;
import gui.TilesEditor.TilesManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
		PrefsController.getInstance().openAutoProject();
		TilesManager.getInstance().tilesSourceThumbnailColumnRefresh();
		TilesManager.getInstance().tilesColumnRefresh();
		
		stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				try {
					boolean needConfirm = PrefsController.getInstance().getConfirm();
					if(needConfirm) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Save");
						alert.setHeaderText("There is unsaved change in the project");
						alert.setContentText("Do you want to save ?");
						ButtonType buttonSave = new ButtonType("Save");
						ButtonType buttonSaveAs = new ButtonType("Save as");
						ButtonType buttonDoNotSave = new ButtonType("Don't save");
						ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
						alert.getButtonTypes().setAll(buttonSave, buttonSaveAs, buttonDoNotSave, buttonCancel);
						Optional<ButtonType> result = alert.showAndWait();
						
						FileChooser fileChooser = new FileChooser();
						
						if(result.get() == buttonSave) {
							File file = null;
							if(PrefsController.getInstance().getProjectPath() != null) {
								file = new File(PrefsController.getInstance().getProjectPath());
							}else {
								file = fileChooser.showSaveDialog(null);
							}
							if(file != null) {
								try {
									ProjectController.getInstance().saveToFile(file.getAbsoluteFile());
								} catch (IOException e1) {
									System.err.println(e1.getMessage());
								}
							}
						}
						else if(result.get() == buttonSaveAs) {
							File file = null;
							file = fileChooser.showSaveDialog(null);
							if(file != null) {
								try {
									ProjectController.getInstance().saveToFile(file.getAbsoluteFile());
								} catch (IOException e1) {
									System.err.println(e1.getMessage());
								}
							}else {
								event.consume();
							}
						}
						else if(result.get() == buttonDoNotSave) {
							//do nothing
						}
						else {
							event.consume();
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
