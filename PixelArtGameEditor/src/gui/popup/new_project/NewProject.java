package gui.popup.new_project;

import java.io.File;
import java.io.IOException;

import controller.PrefsController;
import controller.ProjectController;
import gui.tiles.tiles_editor.TilesManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.properties.ProjectProperties;

public class NewProject extends BorderPane{
	
	private static NewProject instance;
	private VBox container;
	private Label projectNameLabel;
	private TextField projectNameTextField;
	private Label tilesSizeSelectorLabel;
	private ComboBox<Integer> tilesSizeSelector;
	private Button createButton;
	private Button openProjectButton;
	private Button cancelButton;
	
	private NewProject() {
		super();
		container = new VBox();
		projectNameLabel = new Label("Project name :");
		projectNameTextField = new TextField();
		tilesSizeSelectorLabel = new Label("Tiles size :");
		tilesSizeSelector = new ComboBox<Integer>();
		tilesSizeSelector.getItems().addAll(2,16, 32, 64, 128);
		createButton = new Button("Create");
		openProjectButton = new Button("Open Project");
		cancelButton = new Button("Cancel");
		
		container.getChildren().addAll(
				projectNameLabel, 
				projectNameTextField,
				tilesSizeSelectorLabel,
				tilesSizeSelector,
				createButton,
				openProjectButton,
				cancelButton
				);
		cancelButton.setVisible(false);
		
		setAction();
		
		setCenter(container);
		
		setStyle();
		
	}
	
	private void setStyle() {
		container.setPadding(new Insets(5, 5, 5, 5));
		container.setPrefSize(300, 300);
		container.setSpacing(10);
		
		setStyle("-fx-background-color: whitesmoke");
	}
	public void setCancelButtonVisibility(boolean bool) {
		cancelButton.setVisible(bool);
	}
	private void setAction() {
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				setVisible(false);
			}
		});
		createButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				boolean projecteNameIsValid = textIsValid(projectNameTextField.getText());
				boolean tilesSizeIsValid = comboBoxISValid(tilesSizeSelector);
				if(!projecteNameIsValid) {
					projectNameLabel.setTextFill(Color.RED);
				}else {
					projectNameLabel.setTextFill(Color.BLACK);
				}
				if(!tilesSizeIsValid) {
					tilesSizeSelectorLabel.setTextFill(Color.RED);
				}else {
					tilesSizeSelectorLabel.setTextFill(Color.BLACK);
				}
				if (tilesSizeIsValid && projecteNameIsValid){
					ProjectProperties pp = ProjectProperties.getInstance();
					pp.setProjectName(projectNameTextField.getText());
					pp.setTileSize(tilesSizeSelector.getValue());
					//properties has set on Propeject properties
					DirectoryChooser directoryChooser = new DirectoryChooser();
					directoryChooser.setTitle("Choose emplacement project");
					
					File file = directoryChooser.showDialog(null);
					String path = file.getAbsolutePath() + "\\" + projectNameTextField.getText() + ".mpag";
					File newProject = new File(path);
					try {
						ProjectController.getInstance().saveToFile(newProject);
						ProjectController.getInstance().emptyProject();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					NewProject.this.setVisible(false);
				}
				
			}
		});
		
		openProjectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser chooser = new FileChooser();
				chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Pixel Art Game Editor", "*.mpag"));
				File file = chooser.showOpenDialog(null);
				if(file != null) {
					try {
						ProjectController.getInstance().loadFromFile(file);
						PrefsController.getInstance().saveProjectPath(file.getAbsolutePath());
						TilesManager.getInstance().tilesSourceThumbnailColumnRefresh();
						TilesManager.getInstance().getTilesLibrary().refreshAll();
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}

				NewProject.this.setVisibility(false);
			}
		});
	}
	private boolean comboBoxISValid(ComboBox<Integer> cb){
		if(cb.getValue() == null) {
			return false;
		}else {
			return true;
		}
	}
	private boolean textIsValid(String str) {
		// special char a completer
		CharSequence[] speChar = {"/", " ", "\\", "&", "\"", "|"}; 
		for(CharSequence c : speChar) {
			if(str.contains(c)) {
				return false;
			}
		}
		if(str.length() <= 1 || str == null) {
			return false;
		}
		return true;
	}
	public static NewProject getInstance() {
		if(instance == null) {
			instance = new NewProject();
			return instance;
		}
		return instance;
	}
	public void setVisibility(boolean bool) {
		setVisible(bool);
	}
}
