package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PopupController {

    @FXML
    private Pane text;

    @FXML
    void close(ActionEvent event) {
    	popup.close();
    }

    private Stage popup;
    private String popuptext;
    
    public PopupController (Stage popupstage, String string) {
    	this.popup = popupstage;
    	this.popuptext = string;
    }
    
    @FXML
	public void initialize() {
    	text.getChildren().add(new Text(popuptext));
    }
}
