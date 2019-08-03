package unsw.dungeon;

import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopupOnFinishController {

    @FXML
    private Pane text;

    private DungeonFullController main;
    private Stage currentstage;
    private String popuptext;
    
    public PopupOnFinishController (DungeonFullController main, Stage currentstage, String string) {
    	this.main = main;
    	this.currentstage = currentstage;
    	this.popuptext = string;
    }
    
    @FXML
	public void initialize() {
    	text.getChildren().add(new Text(popuptext));
    }
    
    @FXML
    void close(ActionEvent event) {
    	currentstage.close();
    }

    @FXML
    void mainMenu(ActionEvent event) {
    	main.mainmenu(event);
    	currentstage.close();
    }

    @FXML
    void newDungeon(ActionEvent event) throws FileNotFoundException {
    	main.newRandomDungeon();
    	currentstage.close();
    }

}
