package unsw.dungeon;

import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Controller used to display a pause screen for the client
 * @author z5161251
 *
 */
public class PauseController {

	@FXML
	private Button continueButton;
	
	@FXML
	private Button exitButton;
	
	@FXML
	private Button restartButton;
	
	@FXML
	private GridPane inventory;
	
	
    private DungeonFullController main;
    private Stage currentStage;

	public PauseController(DungeonFullController main, Stage currentStage) {
		this.main = main;
		this.currentStage = currentStage;
	}
	
	@FXML
	void continueFun() {
		currentStage.close();
	}
	
	@FXML
	void exitFun() {
		main.mainmenu(new ActionEvent());
		currentStage.close();
	}
	
	@FXML
	void restart() {
		try {
			main.restart(new ActionEvent());
			currentStage.close();
		}catch(Exception e){
			System.out.println("Could not restart: Error");
		}
	}
	
	@FXML
	public void initialize() {
		Backpack bp = main.getDungeon().getPlayer().getBackpack();
		int x = 0;
		int y = 0;
		//System.out.println("Inventory");
		for(EntityConsumable e : bp.getConsumableList()) {
			//e.setStorage(x, y);
			Image i = getImage(e);
			inventory.add(new ImageView(i), x, y);
			//System.out.println("Image added");
			x++; if(x%2 == 0) y++;
		}
		inventory.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

	}
	
	private Image getImage(EntityConsumable e) {
		Image i = new Image("gold_pile.png");
		if(e instanceof Treasure)
			i = new Image("gold_pile.png");
		else if(e instanceof Sword)
	        i = new Image("/greatsword_1_new.png");
		else if(e instanceof Potion)
			i = new Image("/brilliant_blue_new.png");
		else if(e instanceof Bomb_Unlit)
			i = new Image("/bomb_unlit.png");
		else if(e instanceof Key)
			i = new Image("/key.png");
		return i;
	}

}
