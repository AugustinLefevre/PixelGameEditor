package gui.TilesEditor;

import gui.Main;
import gui.popup.tile_editor.TileEditor;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
/**
 * @author augustin.lefevre
 * Singleton of the Tiles source viewer displayed in the tiles manager
 */
class TilesSourceView extends Canvas{
	private static TilesSourceView instance;
	private static GraphicsContext gc;
	private Image image;
	private String currentSource;
	private int currentPXSize = 0;
	private int currentTilesSourceSize;
	private float mousePositionX;
	private float mousePositionY;
	private float sizer = 0;
	private int resolution = 0;
	public boolean isScrolling = false;
	private float additivPositionX = 0;
	private float positionX = 0;
	private float additivPositionY = 0;
	private float currentPositionX = 0;
	private float currentPositionY = 0;
	private float positionY = 0;
	private boolean allowClick = true;
	private float imageSizeX;
	private float imageSizeY;
	private float pxSize;
	
	public TilesSourceView() {
		
		super(512, 512);
		
		gc = getGraphicsContext2D();
		gc.setImageSmoothing(false);
		setAction();
		setSize();
	}
	/**
	 * set the mouse action in the current view
	 */
	private void setAction(){
		addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mousePositionX = (float) e.getX();
				mousePositionY = (float) e.getY();	
				
			}
		});
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(allowClick) {
					TileEditor.getInstance(
							TilesSourceView.this.currentSource,
							TilesSourceViewCursor.getInstance().getPosInTilesSourceX(),
							TilesSourceViewCursor.getInstance().getPosInTilesSourceY())
					.setVisible(true);
				}
				
			}
			
		});
		
		addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				allowClick = false;
				TilesSourceView.drawBackground();
				TilesSourceView.this.additivPositionX = (float) (e.getX() - TilesSourceView.this.positionX);
				TilesSourceView.this.additivPositionY = (float) (e.getY() - TilesSourceView.this.positionY);
				modifyPosition(TilesSourceView.this.additivPositionX + TilesSourceView.this.currentPositionX , TilesSourceView.this.additivPositionY + TilesSourceView.this.currentPositionY );
			}
		});
	
		addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(allowClick  == false) {
					TilesSourceView.this.currentPositionX += (float) TilesSourceView.this.additivPositionX;
					TilesSourceView.this.currentPositionY += (float) TilesSourceView.this.additivPositionY;
					TilesSourceViewCursor.getInstance().setDelta(TilesSourceView.this.currentPositionX, TilesSourceView.this.currentPositionY);
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							allowClick = true;
							
						}
					});
					
				}
				
			}
		});
		addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TilesSourceView.this.positionX = (float) e.getX();
				TilesSourceView.this.positionY = (float) e.getY();
			}
		});
		
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getScene().setCursor(Cursor.NONE);
				
			}
		});
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getScene().setCursor(Cursor.DEFAULT);
			}
		});
		//this event doesnt work
//		addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent e) {
//				float x = TilesSourceView.getInstance().getCurrentPositionX();
//				float y = TilesSourceView.getInstance().getCurrentPositionY();
//				if(e.getCode() == KeyCode.UP) {
//					modifyPosition(x , y+10);
//				}
//				if(e.getCode() == KeyCode.DOWN) {
//					modifyPosition(x , y -10);
//				}
//				if(e.getCode() == KeyCode.LEFT) {
//					modifyPosition(x -10 , y);
//				}
//				if(e.getCode() == KeyCode.RIGHT) {
//					modifyPosition(x +10, y);
//				}
//				
//			}
//			
//		});
		addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent e) {
				boolean canZoom = (pxSize > 4 )? true: e.getDeltaY() > 0;
				boolean canDezoom = (pxSize < 50 )? true: e.getDeltaY() < 0;
				if(canZoom && canDezoom) {
					double zoomFactor = 1.05;
					double deltaY = e.getDeltaY();
					if(deltaY < 0)
						zoomFactor = 2 - zoomFactor;
					double substractSizeX = (Math.floor(TilesSourceView.this.imageSizeX) * zoomFactor) % image.getWidth();
					double substractSizeY = (Math.floor(TilesSourceView.this.imageSizeY)* zoomFactor) % image.getHeight();
					if(Math.floor(TilesSourceView.this.imageSizeX) == Math.floor(TilesSourceView.this.imageSizeX * zoomFactor - substractSizeX)) {
						substractSizeX = -24;
						substractSizeY = -24;
					}
					double newSizeX = TilesSourceView.this.imageSizeX * zoomFactor - substractSizeX;
					double newSizeY = TilesSourceView.this.imageSizeY * zoomFactor - substractSizeY;
					
					TilesSourceView.this.imageSizeX = (float) newSizeX;
					TilesSourceView.this.imageSizeY = (float) newSizeY;
					drawBackground();
					TilesSourceView.gc.drawImage(
							TilesSourceView.this.image,
							TilesSourceView.this.additivPositionX + TilesSourceView.this.currentPositionX,
							TilesSourceView.this.additivPositionY + TilesSourceView.this.currentPositionY,
							newSizeX,
							newSizeY);
					TilesSourceView.this.currentTilesSourceSize = (int) newSizeX;
					setPxSize();
					TilesSourceViewCursor.getInstance().setSize();
					
					setCursorParams();
					float deltax = TilesSourceView.this.currentPositionX + (float) TilesSourceView.this.additivPositionX;
					float deltay = TilesSourceView.this.currentPositionY + (float) TilesSourceView.this.additivPositionY;
					TilesSourceViewCursor.getInstance().setDelta(deltax, deltay);
				}
			}
		});
	}
	/**
	 * display the tiles source image in the tiles source viewer 
	 * @param image
	 */
	public void displaySource(Image image) {
		drawBackground();
		this.image = image;
		this.currentPXSize = (int) image.getWidth();
		if((int) getWidth() - getWidth()%image.getWidth() != 0) {
			currentTilesSourceSize =  (int) (getWidth() - getWidth()%image.getWidth())*4;
		}else {
			currentTilesSourceSize =(int) getWidth()*4;
		}
		setPxSize();
		this.imageSizeX = currentTilesSourceSize;
		this.imageSizeY = (float) (image.getHeight() * this.pxSize);
		gc.drawImage(this.image,
				0,
				0,
				this.imageSizeX,
				this.imageSizeY);
		TilesSourceViewCursor.getInstance().setSize();
		setCursorParams();
	}
	public void setCurrentSource(String currentSource) {
		this.currentSource = currentSource;
	}
	public void setCursorParams() {
		sizer = getCurrentPXSize() / getCurrentTilesSourceSize();
		resolution = (int) (getCurrentTilesSourceSize() / getCurrentPXSize());

	}
	public void setPxSize() {
		this.pxSize = (float) (this.currentTilesSourceSize / this.image.getWidth());
	}
	public double getSizer() {
		return sizer;
	}
	public int getResolution() {
		return resolution;
	}
	/**
	 * Get the tile source viewer instance
	 * @return TilesSourceView
	 */
	public static TilesSourceView getInstance() {
		if(instance == null) {
			instance = new TilesSourceView();
		}
		return instance;
	}
	/**
	 * Update position image in the canvas
	 * @param positionX
	 * @param positionY
	 */
	private void modifyPosition(double positionX, double positionY) {
		gc.drawImage(image,
				positionX,
				positionY,
				currentTilesSourceSize,
				currentTilesSourceSize);
	}
	/**
	 * get the width in pixels unit of the current TilesSource
	 * @return an integer represant width in pixels of the tiles source
	 */
	public int getCurrentPXSize() {
		return currentPXSize;
	}
	/**
	 * the width of the tiles source displayer
	 * @return the width of the dispalyed canvas
	 */
	public float getCurrentTilesSourceSize() {
		return currentTilesSourceSize;
	}
	/**
	 * the mouse position X in the displayer
	 * @return 
	 */
	public float getMousePositionX() {
		return mousePositionX;
	}
	/**
	 * the mouse position Y int the displayer
	 * @return
	 */
	public float getMousePositionY() {
		return mousePositionY;
	}
	/**
	 * the position X of the canvas in the displayer
	 * @return
	 */
	public float getPositionX() {
		return positionX;
	}
	/**
	 * the position Y of the canvas in the dispayer
	 * @return
	 */
	public float getPositionY() {
		return positionY;
	}
	/**
	 * used for reset the displayer before update canvas image
	 */
	public static void drawBackground() {
		gc.setFill(Color.LIGHTGRAY);
		gc.fillRect(0, 0, 2000, 2000);
	}
	/**
	 * get canvas position
	 * @return
	 */
	public float getCurrentPositionX() {
		return currentPositionX;
	}
	public float getCurrentPositionY() {
		return currentPositionY;
	}
	public int getImageWidth() {
		return (int) (this.image.getWidth() * this.pxSize);
		
	}
	public int getImageHeight() {
		return (int) (this.image.getHeight() * this.pxSize);
		
	}
	public void setSize() {
		
		int width = (int) Main.getInstance().getScene().getWindow().getWidth() - 200;
		int height = (int) Main.getInstance().getScene().getWindow().getHeight() - 120;
		setWidth(width);
		setHeight(height);
		
	}
}
