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
 * Application used for a pop-up that displays when the player dies 
 * @author z5161251
 *
 */
public class PopupOnDeathApplication {

	public PopupOnDeathApplication(DungeonFullController main, String string) throws IOException {
    	final Stage popup = new Stage();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("PopupOnDeath.fxml"));
    	
    	PopupOnDeathController controller = new PopupOnDeathController(main, popup, string);

        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        popup.setScene(scene);
        popup.show();
    }
}
