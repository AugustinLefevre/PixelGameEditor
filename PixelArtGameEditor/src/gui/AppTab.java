package gui;

import java.util.Arrays;
import java.util.List;

import gui.TilesEditor.TilesManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class AppTab extends TabPane{
	private static AppTab instance;
	private List<Tab> tabs;
	
	private AppTab() {
		super();
		tabs = Arrays.asList(
	        new Tab("Map editor", new Label("Show Map Editor")),
	        new Tab("Tiles manager"  , TilesManager.getInstance()),
	        new Tab("Tile animator", new Label("show tile animator")),
	        new Tab("Characters editor" , new Label("Show Characters editor")),
	        new Tab("Particules editor" , new Label("Show Particules editor")),
	        new Tab("WordlMap editor" , new Label("Show WorldMap editor"))
		);
		for(Tab t : tabs) {
			t.setOnClosed(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					MenuPanel.getInstance().setCheckMenuItemDeselect(t.getText());
					
				}
			});
		}
		getTabs().add(tabs.get(0));
		getTabs().add(tabs.get(1));
	}
	
	public static AppTab getInstance() {
		if(instance == null) {
			instance = new AppTab();
		}
		return instance;
	}

	private Tab getTabByName(String name) {
		Tab tab = null;
		for(Tab t : tabs) {
			if(t.getText().equals(name)) {
				tab = t;
			}
		}
		return tab;
	}
	/**
	 * add element in the tab panel
	 * @param str
	 */
	public void removeTab(String str) {
		getTabs().remove(getTabByName(str));
	}
	/**
	 * remove element from the tab panel
	 * @param str
	 */
	public void addTab(String str) {
		getTabs().add(getTabByName(str));
	}
	
	
}
