package gui.TilesEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import controller.PrefsController;
import controller.ProjectController;
import gui.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.tiles.Tile;
import model.tiles.TilesSource;

public class TilesManager extends BorderPane{
	private VBox leftDisplayer;
	private VBox tilesSourcesDisplayer;
	private GridPane tilesDisplayer;
	private static Group tilesSourceCanvas;
	private Button buttonImport;
	private static TilesManager instance;
	/**
	 * here we display items and menu for the tiles manager
	 * @throws FileNotFoundException
	 */
	private TilesManager(){
		super();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import tiles source");
		
		setPadding(new Insets(10, 10, 60, 10));
		int sizeX;
		int sizeY;
		
		sizeX = (int)Main.getInstance().getScene().getWidth();
		sizeY = (int)Main.getInstance().getScene().getHeight();
		setPrefSize(sizeX, sizeY);
		
		
		setStyle("-fx-background-color: #2f4f4f");
	
		tilesSourceCanvas = new Group();
		tilesSourceCanvas.setStyle("-fx-background-color: #8f8f8f");
		
		BorderPane mainDisplayer = new BorderPane();
		mainDisplayer.getChildren().addAll(tilesSourceCanvas, TilesSourceViewCursor.getInstance());
		
		setCenter(mainDisplayer);
		this.leftDisplayer = new VBox();
		this.leftDisplayer.setSpacing(10);
		this.leftDisplayer.setStyle("-fx-background-color: #2f2f4f");
		
		this.tilesSourcesDisplayer = new VBox();
		this.tilesSourcesDisplayer.setSpacing(10);
		this.tilesSourcesDisplayer.setPrefHeight(500);
		this.tilesSourcesDisplayer.setMaxHeight(500);
		this.tilesSourcesDisplayer.setStyle("-fx-background-color: #8f8f8f");
		
		this.tilesDisplayer = new GridPane();
		this.tilesDisplayer.setPrefHeight(500);
		this.tilesDisplayer.setMaxHeight(500);
		this.tilesDisplayer.setStyle("-fx-background-color: #8f8f8f");
		
		ScrollPane tilesSourceScrollPane = new ScrollPane();
		tilesSourceScrollPane.setPrefWidth(115);
		tilesSourceScrollPane.setContent(this.tilesSourcesDisplayer);
		
		ScrollPane tilesScrollPane = new ScrollPane();
		tilesScrollPane.setContent(this.tilesDisplayer);
		
		this.buttonImport = new Button("import source");
		buttonImport.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				try {
						if(PrefsController.getInstance().getSavedTilesSourcePath() != null) {
							fileChooser.setInitialDirectory(new File(PrefsController.getInstance().getSavedTilesSourcePath()).getParentFile());
						}
					} catch (IOException e1) {
					e1.printStackTrace();
				}
				File file = fileChooser.showOpenDialog(null);
				try {
					if(file != null) {
						TilesSource ts = ProjectController.getInstance().addTilesSource(file.getAbsolutePath());
						PrefsController.getInstance().saveTilesSourcePath(file.getAbsolutePath());
						setView(ts);
						tilesSourceThumbnailColumnRefresh();
						PrefsController.getInstance().setConfirm(true);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		this.leftDisplayer.getChildren().add(buttonImport);
		this.leftDisplayer.getChildren().add(tilesSourceScrollPane);
		this.leftDisplayer.getChildren().add(tilesScrollPane);
		setLeft(leftDisplayer);
		
		try {
			tilesSourceThumbnailColumnRefresh();
			tilesColumnRefresh();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	/**
	 * refresh the tiles container menu on the left of the tiles manager
	 * @throws FileNotFoundException
	 */
	public void tilesSourceThumbnailColumnRefresh() throws FileNotFoundException {
		
		if(ProjectController.getInstance().getTilesSource() != null) {
			this.tilesSourcesDisplayer.getChildren().clear();
			List<TilesSource> tilesSources = ProjectController.getInstance().getTilesSource();
			for(TilesSource ts : tilesSources) {
				this.tilesSourcesDisplayer.getChildren().add(new ThumbnailTilesSourceDisplayer(ts));
			}
		}	
		
	}
	public void tilesColumnRefresh() throws FileNotFoundException {
		
		if(ProjectController.getInstance().getTiles() != null) {
			this.tilesDisplayer.getChildren().clear();
			List<Tile> tiles = ProjectController.getInstance().getTiles();
			int count = 1;
			int count2 = 0;
			for(Tile tile : tiles) {
				if(count++ % 2 == 0) {
					this.tilesDisplayer.add(new ThumbnailTile(tile), 1, count2);
					count2++;
				}else {
					this.tilesDisplayer.add(new ThumbnailTile(tile), 0, count2);
				}
				
			}
		}	
		
	}
	/**
	 * modify the Tiles Source view
	 * @param ts
	 * @throws FileNotFoundException
	 */
	public static void setView(TilesSource ts) throws FileNotFoundException {
		tilesSourceCanvas.getChildren().clear();
		TilesSourceView tsv = TilesSourceView.getInstance();
		tsv.setCurrentSource(ts.getPath());
		tsv.displaySource(ts.getTilesSourceImage().getImage());
		tilesSourceCanvas.getChildren().add(tsv);
	}
	
	public static TilesManager getInstance() {
		if(instance == null) {
			instance = new TilesManager();
			
		}
		return instance;
	}
	
}
