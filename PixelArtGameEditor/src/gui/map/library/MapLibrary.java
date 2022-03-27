package gui.map.library;

import gui.icon.PlusIcon;
import gui.icon.RedCross;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MapLibrary extends ScrollPane {
	private VBox displayer;
	private static MapLibrary instance;
	private PlusIcon buttonAdd;
	
	private MapLibrary() {
		super();
		setPrefWidth(115);
		this.displayer = new VBox();
		this.buttonAdd = PlusIcon.getInstance(100);
		this.displayer.getChildren().add(this.buttonAdd);
		setContent(this.displayer);
	}
	
	public static MapLibrary getInstance() {
		if(instance == null) {
			instance = new MapLibrary();
		}
		return instance;
	}
}
