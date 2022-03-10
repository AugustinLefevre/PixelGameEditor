package gui.TilesEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import controller.PrefsController;
import controller.ProjectController;
//import controller.TilesSourceController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.tiles.TilesSource;

public class TilesManager{
	//private TilesSourceController tilesSourceController;
	private ProjectController projectController;
	private VBox leftDisplayer;
	private VBox tilesSourcesDisplayer;
	private static Group tilesSourceCanvas;
	private Button buttonImport;
	private static BorderPane bpane;
	private static TilesManager instance;
	/**
	 * here we display items and menu for the tiles manager
	 * @throws FileNotFoundException
	 */
	private TilesManager() throws FileNotFoundException {
		FileChooser fileChooser = new FileChooser();
		//tilesSourceController = TilesSourceController.getInstance();
		projectController = ProjectController.getInstance();
		fileChooser.setTitle("Import tiles source");
		
		bpane = new BorderPane();
		bpane.setPadding(new Insets(50, 20, 10, 10));
		bpane.setPrefSize(800, 600);
		bpane.setStyle("-fx-background-color: #2f4f4f");
		
		tilesSourceCanvas = new Group();
		tilesSourceCanvas.setStyle("-fx-background-color: #8f8f8f");
		
		BorderPane tilesSourceDisplayer = new BorderPane();
		tilesSourceDisplayer.getChildren().addAll(tilesSourceCanvas, TilesSourceViewCursor.getInstance());
		ScrollPane mainScrollPane = new ScrollPane();
		mainScrollPane.setContent(tilesSourceDisplayer);
		bpane.setCenter(mainScrollPane);
		
		this.leftDisplayer = new VBox();
		this.leftDisplayer.setSpacing(10);
		this.leftDisplayer.setStyle("-fx-background-color: #2f2f4f");
		
		this.tilesSourcesDisplayer = new VBox();
		this.tilesSourcesDisplayer.setSpacing(10);
		this.tilesSourcesDisplayer.setPrefHeight(250);
		this.tilesSourcesDisplayer.setMaxHeight(250);
		this.tilesSourcesDisplayer.setStyle("-fx-background-color: #8f8f8f");
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(this.tilesSourcesDisplayer);
		
		this.buttonImport = new Button("import source");
		buttonImport.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
						if(PrefsController.getInstance().getSavedTilesSourcePath() != null) {
							fileChooser.setInitialDirectory(new File(PrefsController.getInstance().getSavedTilesSourcePath()).getParentFile());
						}
					} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File file = fileChooser.showOpenDialog(null);
				try {
					if(file != null) {
						TilesSource ts = projectController.addTilesSource(file.getAbsolutePath());
						PrefsController.getInstance().saveTilesSourcePath(file.getAbsolutePath());
						setView(ts);
						leftColumnRefresh();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		this.leftDisplayer.getChildren().add(buttonImport);
		this.leftDisplayer.getChildren().add(scrollPane);
		bpane.setLeft(leftDisplayer);
		bpane.setVisible(false);
		leftColumnRefresh();
	}
	/**
	 * refresh the tiles container menu on the left of the tiles manager
	 * @throws FileNotFoundException
	 */
	public void leftColumnRefresh() throws FileNotFoundException {
		
		if(this.projectController.getTilesSource() != null) {
			this.tilesSourcesDisplayer.getChildren().clear();
			List<TilesSource> tilesSources = this.projectController.getTilesSource();
			for(TilesSource ts : tilesSources) {
				this.tilesSourcesDisplayer.getChildren().add(new ThumbnailTilesSource(ts));
			}
		}	
		
	}
	/**
	 * get the borderPane of the Tiles Manager
	 * @return 
	 */
	public BorderPane getBorderPane() {
		return bpane;
	}
	/**
	 * display the Tiles manager or not
	 * @param bool Diplay if true
	 */
	public void setVisibility(boolean bool) {
		TilesManager.bpane.setVisible(bool);
	}
	/**
	 * get the controller
	 * @return
	 */
	public ProjectController getProjectController() {
		return this.projectController;
	}
	/**
	 * modify the Tiles Source view
	 * @param ts
	 * @throws FileNotFoundException
	 */
	public static void setView(TilesSource ts) throws FileNotFoundException {
		tilesSourceCanvas.getChildren().clear();
		TilesSourceView tsv = TilesSourceView.getInstance();
		tsv.displaySource(ts.getTilesSourceImage().getImage());
		tilesSourceCanvas.getChildren().add(tsv);
	}
	
	public static TilesManager getInstance() {
		if(instance == null) {
			try {
				instance = new TilesManager();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
}
