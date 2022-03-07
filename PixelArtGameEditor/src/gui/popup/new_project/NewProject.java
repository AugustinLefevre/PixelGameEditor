package gui.popup.new_project;

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
	
	private NewProject() {
		super();
		container = new VBox();
		projectNameLabel = new Label("Project name :");
		projectNameTextField = new TextField();
		tilesSizeSelectorLabel = new Label("Tiles size :");
		tilesSizeSelector = new ComboBox<Integer>();
		tilesSizeSelector.getItems().addAll(16, 32, 64, 128);
		createButton = new Button("Create");
		openProjectButton = new Button("Open Project");
		
		container.getChildren().addAll(
				projectNameLabel, 
				projectNameTextField,
				tilesSizeSelectorLabel,
				tilesSizeSelector,
				createButton,
				openProjectButton
				);
		
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
	
	private void setAction() {
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
					NewProject.this.setVisible(false);
				}
				
			}
		});
		
		openProjectButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
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
