package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopupOnDeathController {

    @FXML
    private Pane text;

    @FXML
    private Button access;
    
    @FXML
    void close(ActionEvent event) {
    	currentstage.close();
    }

    private DungeonFullController main;
    private Stage currentstage;
    private String popuptext;
    
    public PopupOnDeathController (DungeonFullController main, Stage currentstage, String string) {
    	this.main = main;
    	this.currentstage = currentstage;
    	this.popuptext = string;
    }
    
    @FXML
	public void initialize() {
    	text.getChildren().add(new Text(popuptext));
    	access.setText("Restart dungeon");
    	access.setOnAction((event) -> {
    		try {
				main.restart(event);
				currentstage.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	});
    }
}
