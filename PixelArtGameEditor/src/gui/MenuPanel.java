package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controller.PrefsController;
import controller.ProjectController;
import gui.tiles.tiles_editor.TilesManager;
import gui.popup.new_project.NewProject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import model.properties.Prefs;

public class MenuPanel extends MenuBar{

	private Menu menuFile;
	private MenuItem newFile;
	private MenuItem openFile;
	private MenuItem saveFile;
	private MenuItem saveAs;
	Map<String, CheckMenuItem> checkMenuPanels;
	
	private static MenuPanel instance;
	
	private MenuPanel() throws FileNotFoundException {
		super();
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
		
		checkMenuPanels = new HashMap<String, CheckMenuItem>();
		checkMenuPanels.put("Tiles manager", new CheckMenuItem("Tiles manager"));
		checkMenuPanels.put("Tile animator", new CheckMenuItem("Tile animator"));
		checkMenuPanels.put("Characters manager", new CheckMenuItem("Characters manager"));
		checkMenuPanels.put("Particules editor", new CheckMenuItem("Particules editor"));
		checkMenuPanels.put("WordlMap editor", new CheckMenuItem("WordlMap editor"));
		
		checkMenuPanels.forEach((name, menuItem) -> {
			openWindow.getItems().add(menuItem);
			
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if(menuItem.isSelected()) {
						AppTab.getInstance().addTab(name);
					}else {
						AppTab.getInstance().removeTab(name);
					}
						
				}
			});
		});
		
		checkMenuPanels.get("Tiles manager").setSelected(true);

		menuWindow.getItems().add(openWindow);

	    getMenus().add(menuFile);
	    getMenus().add(menuWindow);
	    getMenus().add(menuProject);
	    setAction();
	}
	/**
	 * here we set the actions for the menubar elements
	 */
	private void setAction() {
		FileChooser fileChooser = new FileChooser();
		// mpag = My Pixel Art Game
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pixel Art Game Editor", "*.mpag"));
		this.newFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				NewProject.getInstance().display();
				NewProject.getInstance().setCancelButtonVisibility(true);
				
			}
		});
		this.saveFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				File file = null;
				try {
					if(PrefsController.getInstance().getProjectPath() != null) {
						file = new File(Prefs.getInstance().getProjectPath());
					}else {
						file = fileChooser.showSaveDialog(null);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(file != null) {
					try {
						ProjectController.getInstance().saveToFile(file.getAbsoluteFile());
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}
				try {
					PrefsController.getInstance().setConfirm(false);
				} catch (IOException e) {
					e.printStackTrace();
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
						ProjectController.getInstance().saveToFile(file.getAbsoluteFile());
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
					
				}
				try {
					PrefsController.getInstance().setConfirm(false);
				} catch (IOException e) {
					e.printStackTrace();
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
						//TilesManager.getInstance().tilesColumnRefresh();
						TilesManager.getInstance().getTilesLibrary().refreshAll();
						PrefsController.getInstance().setConfirm(false);
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}
			}
		});
		
	}
	public void setCheckMenuItemDeselect(String name) {
		checkMenuPanels.get(name).setSelected(false);
	}

	public static MenuPanel getInstance() {
		if(instance == null) {
			try {
				instance = new MenuPanel();
				return instance;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return instance;
		
	}
}
