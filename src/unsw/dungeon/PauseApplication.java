package unsw.dungeon;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * An application used to display a pause screen to the client
 * @author z5161251
 *
 */
public class PauseApplication {

	public PauseApplication(DungeonFullController main) throws IOException {
    	final Stage pause = new Stage();
    	pause.setTitle("Game Paused");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("PauseMenu.fxml"));
    	
    	PauseController controller = new PauseController(main, pause);

        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        pause.setScene(scene);
        pause.show();
    }
}
