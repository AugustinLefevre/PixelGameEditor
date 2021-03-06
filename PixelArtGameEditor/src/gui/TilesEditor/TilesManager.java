package gui.TilesEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import controller.TilesSourceController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.TilesSource;

public class TilesManager{
	private BorderPane borderPane;
	private TilesSourceController tilesSourceController;
	private VBox leftDisplayer;
	private VBox tilesSourcesDisplayer;
	private static VBox mainDisplayer;
	private Button buttonImport;
	private static BorderPane bpane;
	private static TilesManager instance;
	/**
	 * here we display items and menu for the tiles manager
	 * @throws FileNotFoundException
	 */
	private TilesManager() throws FileNotFoundException {
		FileChooser fileChooser = new FileChooser();
		tilesSourceController = TilesSourceController.getInstance();
		fileChooser.setTitle("Import tiles source");
		
		bpane = new BorderPane();
		bpane.setPadding(new Insets(50, 20, 10, 10));
		bpane.setPrefSize(800, 600);
		bpane.setStyle("-fx-background-color: #2f4f4f");
		
		mainDisplayer = new VBox();
		mainDisplayer.setPrefSize(500, 500);
		mainDisplayer.setMaxSize(500, 500);
		mainDisplayer.setStyle("-fx-background-color: #8f8f8f");
		ScrollPane mainScrollPane = new ScrollPane();
		mainScrollPane.setContent(mainDisplayer);
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
				File file = fileChooser.showOpenDialog(null);
				try {
					//TilesSource ts = TilesManager.this.tileSourceManager.addSource(file.getAbsolutePath());
					TilesSource ts = tilesSourceController.addTilesSource(file.getAbsolutePath());
					setView(ts);
					leftColumnRefresh();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		this.leftDisplayer.getChildren().add(buttonImport);
		this.leftDisplayer.getChildren().add(scrollPane);
		bpane.setLeft(leftDisplayer);
		bpane.setVisible(true);
		leftColumnRefresh();
		this.borderPane = bpane;
	}
	/**
	 * refresh the tiles container menu on the left of the tiles manager
	 * @throws FileNotFoundException
	 */
	public void leftColumnRefresh() throws FileNotFoundException {
		this.tilesSourcesDisplayer.getChildren().clear();
		
		List<TilesSource> tilesSources = this.tilesSourceController.getTilesSources();
		for(TilesSource ts : tilesSources) {
			this.tilesSourcesDisplayer.getChildren().add(new ThumbnailTilesSource(ts));
		}
	}
	/**
	 * get the borderPane of the Tiles Manager
	 * @return 
	 */
	public BorderPane getBorderPane() {
		return borderPane;
	}
	/**
	 * display the Tiles manager or not
	 * @param bool Diplay if true
	 */
	public void setVisibility(boolean bool) {
		this.borderPane.setVisible(bool);
	}
	/**
	 * get the controller
	 * @return
	 */
	public TilesSourceController getTilesSourcesController() {
		return this.tilesSourceController;
	}
	/**
	 * modify the Tiles Source view
	 * @param ts
	 * @throws FileNotFoundException
	 */
	public static void setView(TilesSource ts) throws FileNotFoundException {
		mainDisplayer.getChildren().clear();
		TilesSourceView tsv = TilesSourceView.getInstance();
		tsv.displaySource(ts.getTilesSourceImage().getImage());
		mainDisplayer.getChildren().add(tsv);
	}
	
	public static TilesManager getInstance() {
		if(instance == null) {
			try {
				return new TilesManager();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
}
