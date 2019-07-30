package unsw.dungeon;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class DungeonFullController {

    @FXML
    private GridPane squares;

    @FXML
    private MenuItem mainmenu;

    @FXML
    private MenuItem restart;

    @FXML
    private MenuItem exit;

    @FXML
    private Menu levelmenu;


    public DungeonFullController(List<String> dungeonPaths) {
    	// dungeonPaths will take all dungeons and make the required levels on the dungeon selector
    	System.out.println(levelmenu);
    	levelmenu = new Menu("levelmenu");
    	for (int i = 0; i < dungeonPaths.size(); i++) {
    		String name = dungeonPaths.get(i);
    		String rname = name.replaceAll(".json$", "");
    		System.out.println("here " + rname);
    		MenuItem r = new MenuItem(rname);
    		levelmenu.getItems().add(r);
    	}
    }
    
    
    @FXML
	public void initialize() {

	}
    
    @FXML
    void handleKeyPress(KeyEvent event) {
    	switch (event.getCode()) {
        case UP:
            // player.moveUp();
            break;
        case DOWN:
            // player.moveDown();
            break;
        case LEFT:
            // player.moveLeft();
            break;
        case RIGHT:
            // player.moveRight();
            break;
        case R:
            // Restart the game
        case N:
            // Get to the next level
        default:
            break;
        }
    }

    @FXML
    void mainmenu(ActionEvent event) {

    }

    @FXML
    void restart(ActionEvent event) {

    }
    
    @FXML
    void exitApp(ActionEvent event) {

    }


}

