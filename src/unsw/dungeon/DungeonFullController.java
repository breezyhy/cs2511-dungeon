package unsw.dungeon;

import java.util.List;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class DungeonFullController {

    @FXML
    private Pane main;

    @FXML
    private GridPane squares;

    @FXML
    private MenuItem mainmenu;

    @FXML
    private MenuItem restart;

    @FXML
    private MenuItem exit;


    public DungeonFullController(List<String> dungeonPaths) {
    	// dungeonPaths will take all dungeons and make the required levels on the dungeon selector
    }
    
    @FXML
    public void handleKeyPress(KeyEvent event) {
    	// If there's no dungeon, do nothing
    	
    	switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
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
}
