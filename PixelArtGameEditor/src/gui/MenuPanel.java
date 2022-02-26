package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import gui.TilesEditor.TilesManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.FileChooser;

public class MenuPanel{

	private MenuBar menuBar;
	private Menu menuFile;
	private MenuItem newFile;
	private MenuItem openFile;
	private MenuItem saveFile;
	private TilesManager tilesManager;
	private MenuItem saveAs;
	private CheckMenuItem tilesManagerItem;
	private static MenuPanel instance;
	
	private MenuPanel(Group root) throws FileNotFoundException {
		this.tilesManager = TilesManager.getInstance();
		//SeparatorMenuItem separator = new SeparatorMenuItem();
		root.getChildren().add(this.tilesManager.getBorderPane());
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
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Tiles source file", "*.tsf"));
		this.saveFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				File file = fileChooser.showSaveDialog(null);
				if(file != null) {
					try {
						MenuPanel.this.tilesManager.getTilesSourcesController().saveToFile(file.getAbsoluteFile());
					} catch (IOException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		});
		this.saveAs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				File file = fileChooser.showSaveDialog(null);
				if(file != null) {
					try {
						MenuPanel.this.tilesManager.getTilesSourcesController().saveToFile(file.getAbsoluteFile());
					} catch (IOException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		});
		this.openFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				File file = fileChooser.showOpenDialog(null);
				if(file != null) {
					try {
						MenuPanel.this.tilesManager.getTilesSourcesController().loadFromFile(file);
						MenuPanel.this.tilesManager.leftColumnRefresh();
					} catch (IOException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		});
		this.tilesManagerItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				tilesManager.setVisibility(tilesManagerItem.isSelected());
			}
		});
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	public static MenuPanel getInstance(Group root) {
		if(instance == null) {
			try {
				return new MenuPanel(root);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return instance;
		
	}
}
