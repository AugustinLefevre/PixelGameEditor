package gui.popup.tile_editor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import controller.PrefsController;
import controller.ProjectController;
import gui.tiles.tiles_editor.TilesManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.properties.ProjectProperties;
import model.tiles.Tile;
import model.tiles.TilesType;

public class TileEditor extends BorderPane {
	private static TileEditor instance;
	private Button up;
	private Button down;
	private Button left;
	private Button right;
	private Canvas canvas;
	private static GraphicsContext gc;
	private ComboBox<TilesType> tileTypeSelector;
	private Button validate;
	private Button delete;
	private static String tilesSourcePath;
	private static int X;
	private static int Y;
	private static Tile tile;
	private static InputStream imgStream;
	private static Image image;
	private BorderPane leftDisplayer;
	private VBox rightDisplayer;
	
	public TileEditor() {
		super();
		leftDisplayer = new BorderPane();
		rightDisplayer = new VBox(20);
		left = new Button();
		right = new Button();
		up = new Button();
		down = new Button();
		canvas = new Canvas(250, 250);
		validate = new Button("validate");
		delete = new Button("delete");
		tileTypeSelector = new ComboBox<TilesType>();
		
		
		
		gc = canvas.getGraphicsContext2D();
		gc.setImageSmoothing(false);
		setStyle("-fx-background-color: whitesmoke");
		setLeft(leftDisplayer);
		setRight(rightDisplayer);
		
		String path = System.getProperty("user.dir") + "\\resources";
		InputStream arrowLeftStream;
		InputStream arrowRightStream;
		InputStream arrowUpStream;
		InputStream arrowDownStream;
		try {
			arrowLeftStream = new FileInputStream(path + "\\arrowLeft.png");
			arrowRightStream = new FileInputStream(path + "\\arrowRight.png");
			arrowUpStream = new FileInputStream(path + "\\arrowUp.png");
			arrowDownStream = new FileInputStream(path + "\\arrowDown.png");
			
			Image imageLeftArrow = new Image(arrowLeftStream);
			Image imageRightArrow = new Image(arrowRightStream);
			Image imageUpArrow = new Image(arrowUpStream);
			Image imageDownArrow = new Image(arrowDownStream);
			
			ImageView imgLeft = new ImageView(imageLeftArrow);
			ImageView imgRight = new ImageView(imageRightArrow);
			ImageView imgUp = new ImageView(imageUpArrow);
			ImageView imgDown = new ImageView(imageDownArrow);
			
			left.setGraphic(imgLeft);
			right.setGraphic(imgRight);
			up.setGraphic(imgUp);
			down.setGraphic(imgDown);
			
			leftDisplayer.setLeft(left);
			BorderPane.setAlignment(left, Pos.CENTER_LEFT);
			leftDisplayer.setRight(right);
			BorderPane.setAlignment(right, Pos.CENTER_RIGHT);
			leftDisplayer.setTop(up);
			BorderPane.setAlignment(up, Pos.TOP_CENTER);
			leftDisplayer.setBottom(down);
			BorderPane.setAlignment(down, Pos.BOTTOM_CENTER);
			
			leftDisplayer.setCenter(canvas);
			
			
			tileTypeSelector.setItems(FXCollections.observableArrayList(TilesType.values()));
			rightDisplayer.getChildren().addAll(tileTypeSelector, validate, delete);
			setActions();
			setVisible(false);
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void setActions() {
		left.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setX(X-1);
				gc.drawImage(image, X, Y, ProjectProperties.getInstance().getTileSize(), ProjectProperties.getInstance().getTileSize(), 0, 0, 250 , 250);
			}
		});
		right.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setX(X+1);
				gc.drawImage(image, X, Y, ProjectProperties.getInstance().getTileSize(), ProjectProperties.getInstance().getTileSize(), 0, 0, 250 , 250);
			}
		});
		up.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setY(Y-1);
				gc.drawImage(image, X, Y, ProjectProperties.getInstance().getTileSize(), ProjectProperties.getInstance().getTileSize(), 0, 0, 250 , 250);
			}
		});
		down.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setY(Y+1);
				gc.drawImage(image, X, Y, ProjectProperties.getInstance().getTileSize(), ProjectProperties.getInstance().getTileSize(), 0, 0, 250 , 250);
			}
		});
		validate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				TilesType type = (tileTypeSelector.getValue() == null)? TilesType.simple : tileTypeSelector.getValue();
				tile = ProjectController.getInstance().addTile(tilesSourcePath, X, Y, type);
				TileEditor.this.setVisible(false);
				try {
					TilesManager.getInstance().getTilesLibrary().refreshAll();
					PrefsController.getInstance().setConfirm(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(getTile() != null) {
					ProjectController.getInstance().removeTile(getTile());
					
					try {
						TilesManager.getInstance().getTilesLibrary().refreshAll();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				TileEditor.this.setVisible(false);
				
			}
		});
	}
	
	public static TileEditor getInstance() {
		if(instance == null) {
			instance = new TileEditor();
		}
		return instance;
	}
	public static TileEditor getInstance(Tile t) {
		if(instance == null) {
			instance = new TileEditor();
		}
		tile = t;
		setTilesSourcePath(tile.getPath());
		setX(tile.getPositionX());
		setY(tile.getPositionY());
		gc.drawImage(image, X, Y, ProjectProperties.getInstance().getTileSize(), ProjectProperties.getInstance().getTileSize(), 0, 0, 250 , 250);
		return instance;
	}
	
	public static TileEditor getInstance(String source, int X, int Y) {
		if(instance == null) {
			instance = new TileEditor();
		}
		tile = null;
		setTilesSourcePath(source);
		setX(X);
		setY(Y);
		
		
		gc.drawImage(image, X, Y, ProjectProperties.getInstance().getTileSize(), ProjectProperties.getInstance().getTileSize(), 0, 0, 250 , 250);

		return instance;
	}

	public static void setTilesSourcePath(String tsPath) {
		tilesSourcePath = tsPath;
		try {
			imgStream = new FileInputStream(tsPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = new Image(imgStream);
	}
	public static void setX(int x) {
		X = x;
	}
	public static void setY(int y) {
		Y = y;
	}
	public static String getTilesSourcePath() {
		return tilesSourcePath;
	}
	public static int getX() {
		return X;
	}
	public static int getY() {
		return Y;
	}
	public Tile getTile() {
		return tile;
	}
}
