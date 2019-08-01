package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DungeonFullController {

	@FXML
	private Pane pane;

	@FXML
	private GridPane squares;

	@FXML
	private MenuBar menubar;

	@FXML
	private MenuItem mainmenu;

	@FXML
	private MenuItem restart;

	@FXML
	private MenuItem exit;

	@FXML
	private Menu levelmenu;

    private List<String> dungeonPaths;
	private Dungeon loadedDungeon = null;
	private String loadedDungeonPath = null;
	private Stage stage = null;
	    
    public DungeonFullController(List<String> dungeonPaths) {
    	// dungeonPaths will take all dungeons and make the required levels on the dungeon selector
    	this.dungeonPaths = dungeonPaths;
    }
    
    
    @FXML
	public void initialize() {
    	System.out.println(levelmenu);
    	for (int i = 0; i < dungeonPaths.size(); i++) {
    		String name = dungeonPaths.get(i);
    		String rname = name.replaceAll(".json$", "");
    		System.out.println("here " + rname);
    		MenuItem r = new MenuItem(rname);
    		r.setOnAction((event) -> {
    			// Load the dungeon with the specified name
    			try {
					loadDungeon(name);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
    		});
    		levelmenu.getItems().add(r);
    	}	
	}
    
    @FXML
    void handleKeyPress(KeyEvent event) {
    	// System.out.println("keyevent " + event);
    	if (loadedDungeon == null) return;
    	
    	Player player = loadedDungeon.getPlayer();
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
    	clearDungeon();
    }

    @FXML
    void exitApp(ActionEvent event) {
    	
    }
    
    @FXML
    void restart(ActionEvent event) throws FileNotFoundException {
    	String pastDungeon = this.loadedDungeonPath;
    	loadDungeon(pastDungeon);
    }
    
    private void initMainMenu() {
    	
    }
    
    private void loadDungeon(String pathname) throws FileNotFoundException {
    	clearDungeon();
    	System.out.println(pathname);
    	DungeonFullLoader dungeonLoader = new DungeonFullLoader(pathname);
    	this.loadedDungeon = dungeonLoader.load();
    	this.loadedDungeonPath = pathname;
    	Dungeon dungeon = this.loadedDungeon;
    	List<ImageView> initialEntities = dungeonLoader.getEntities();
    	
    	
    	Image ground = new Image("/dirt_0_new.png");
        Image blank = new Image("/blank.png");
        Image backpack = new Image("/backpack.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        
        squares.add(new ImageView(backpack), dungeon.getWidth(), 0);

        for (int y = 1; y < dungeon.getHeight(); y++) {
            int x = dungeon.getWidth();
            squares.add(new ImageView(blank), x, y);
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        this.stage.setWidth((dungeon.getWidth() + 1) * 32 + 16);
        this.stage.setHeight((dungeon.getHeight() + 2) * 32);
        
    }
    
    private void clearDungeon() {
    	this.loadedDungeon = null;
    	this.loadedDungeonPath = null;
    	squares.getChildren().clear();
    	
    	this.stage.setWidth(400 + 16);
    	this.stage.setHeight(300 + 32 + 8);
    }
    
    public GridPane getSquares() {
    	return this.squares;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }
}

