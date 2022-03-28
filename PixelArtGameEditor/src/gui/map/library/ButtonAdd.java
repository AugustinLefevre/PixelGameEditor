package gui.map.library;

import gui.icon.PlusIcon;
import gui.popup.map_creator.MapCreator;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ButtonAdd extends BorderPane {
	public static ButtonAdd instance;
	
	public ButtonAdd() {
		super();
		setCenter(PlusIcon.getInstance(100));
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MapCreator.getInstance().display();
			}
		});
	}
	
	public static ButtonAdd getInstance() {
		if(instance == null) {
			instance = new ButtonAdd();
		}
		return instance;
	}
}
