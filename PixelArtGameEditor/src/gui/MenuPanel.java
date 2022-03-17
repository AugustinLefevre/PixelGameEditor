package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.PrefsController;
import controller.ProjectController;
import gui.TilesEditor.TilesManager;
import gui.popup.new_project.NewProject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;
import model.properties.Prefs;

public class MenuPanel{

	private MenuBar menuBar;
	private Menu menuFile;
	private MenuItem newFile;
	private MenuItem openFile;
	private MenuItem saveFile;
	private TilesManager tilesManager;
//	private NewProject np;
	private MenuItem saveAs;
	private CheckMenuItem tilesManagerItem;
	private static MenuPanel instance;
	
	private MenuPanel(Group root) throws FileNotFoundException {
		this.tilesManager = TilesManager.getInstance();
		//this.np = NewProject.getInstance();
		root.getChildren().add(this.tilesManager.getBorderPane());
		//root.getChildren().add(this.np);
		this.menuFile = new Menu("File");
		this.newFile = new MenuItem("New");
		this.openFile = new MenuItem("Open File");
		this.saveFile = new MenuItem("Save");
		
		this.saveAs = new MenuItem("Save as");
		menuFile.getItems().add(newFile);
		menuFile.getItems().add(openFile);
		menuFile.getItems().add(saveFile);
		menuFile.getItems().add(saveAs);

		Menu menuProject = new Menu("Project");
		MenuItem properties = new MenuItem("Properties");
		menuProject.getItems().add(properties);

		Menu menuWindow = new Menu("Window");
		Menu openWindow = new Menu("Open window");
		this.tilesManagerItem = new CheckMenuItem("Tiles manager");
		
		openWindow.getItems().add(tilesManagerItem);
		menuWindow.getItems().add(openWindow);

	    menuBar = new MenuBar();
	    menuBar.getMenus().add(menuFile);
	    menuBar.getMenus().add(menuWindow);
	    menuBar.getMenus().add(menuProject);
	    setAction();
	}
	/**
	 * here we set the actions for the menubar elements
	 */
	private void setAction() {
		FileChooser fileChooser = new FileChooser();
		// mpag My Pixel Art Game
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pixel Art Game Editor", "*.mpag"));
		this.newFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				NewProject.getInstance().setVisibility(true);
			}
		});
		this.saveFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				File file = null;
				if(Prefs.getInstance().getProjectPath() != null) {
					file = new File(Prefs.getInstance().getProjectPath());
				}else {
					file = fileChooser.showSaveDialog(null);
				}
				if(file != null) {
					try {
						MenuPanel.this.tilesManager.getProjectController().saveToFile(file.getAbsoluteFile());
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}
			}
		});
		this.saveAs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if(Prefs.getInstance().getProjectPath() != null) {
					File parent = new File(Prefs.getInstance().getProjectPath());
					fileChooser.setInitialDirectory(new File(parent.getParent()));
				}
				
				File file = fileChooser.showSaveDialog(null);
				if(file != null) {
					try {
						MenuPanel.this.tilesManager.getProjectController().saveToFile(file.getAbsoluteFile());
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}
			}
		});
		this.openFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				fileChooser.setInitialDirectory(new File(Prefs.getInstance().getProjectPath()).getParentFile());
				File file = fileChooser.showOpenDialog(null);
				if(file != null) {
					try {
						ProjectController.getInstance().loadFromFile(file);
						PrefsController.getInstance().saveProjectPath(file.getAbsolutePath());
						TilesManager.getInstance().tilesSourceThumbnailColumnRefresh();
						TilesManager.getInstance().tilesColumnRefresh();
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}
			}
		});
		this.tilesManagerItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				tilesManager.setVisibility(tilesManagerItem.isSelected());
				//np.setVisible(false);
			}
		});
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public static MenuPanel getInstance(Group root) {
		if(instance == null) {
			try {
				instance = new MenuPanel(root);
				return instance;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return instance;
		
	}
}
