package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Full controller of the dungeon, handling all input and UI updating
 * @author z5161251
 *
 */
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

    @FXML
    private Pane textpane1;
    
    @FXML
    private Pane textpane2;
    
    @FXML
    private Pane boxpane1;
    
    @FXML
    private Pane boxpane2;
    
	@FXML
	private Pane mainpane;

	@FXML
    private CheckBox disablewitch;

    @FXML
    private CheckBox disablehound;
    
	// Internals
	private Stage stage = null;
	
	// Others
    private List<String> dungeonPaths;
	private Dungeon loadedDungeon = null;
	private String loadedDungeonPath = null;
	private ChoiceBox<String> choicebox = null;
	private ChoiceBox<String> diffbox = null;
	private int numGoals;
	private boolean controllerOn;
	private int startingHp;
	
	/**
	 * Instantiate the controller of the application. Takes list of dungeon (json) filename
	 * @param dungeonPaths (List<String>)
	 */
    public DungeonFullController(List<String> dungeonPaths) {
    	// dungeonPaths will take all dungeons and make the required levels on the dungeon selector
    	this.dungeonPaths = dungeonPaths;
    }
    
    /**
     * Overriden from FXML Application class, is used to show the first screen to the player
     */
    @FXML
	public void initialize() {
    	//Add difficulty choice to game
    	textpane2.getChildren().add(new Text("Select difficulty"));
    	diffbox = new ChoiceBox<String>();
    	diffbox.getItems().addAll("Easy", "Medium", "Hard");
    	diffbox.setValue("Hard");
    	boxpane2.getChildren().add(diffbox);
    	
    	//Select a level from the list
    	textpane1.getChildren().add(new Text("Select dungeon"));
    	choicebox = new ChoiceBox<String>();
    	boxpane1.getChildren().add(choicebox);
    	
    	for (int i = 0; i < dungeonPaths.size(); i++) {
    		
    		// Set up the levelmenu
    		String name = dungeonPaths.get(i);
    		String rname = name.replaceAll(".json$", "");
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
    		
    		// Set up the choicebox
    		choicebox.getItems().add(i, rname);
    	}
	}
    
    /**
     * Overriden from JavaFX, handles all key input from the client
     * @param event
     */
    @FXML
    void handleKeyPress(KeyEvent event) {
    	if (loadedDungeon == null) return;
    	if (controllerOn == false) return;
    	
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
        	try {
				restartDungeon();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
        	break;
        case D:
        	// Drop the key
        	player.dropKey();
        	break;
        case B:
        	// Drop the bomb
        	if(player.dropBomb()) {
        		dropBomb(player);
        	}
        	break;
        case ESCAPE:
        	try {
    			new PauseApplication(this);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        default:
            break;
        }
    }

    /**
     * Returns the player to the main menu, where they can choose difficulty settings
     * @param event
     */
    @FXML
    void mainmenu(ActionEvent event) {
    	clearDungeon();
    }

    @FXML
    void exitApp(ActionEvent event) {
    	Platform.exit();
    	System.exit(0);
    }
    
    @FXML
    void launchDungeon(ActionEvent event) {
    	int index = choicebox.getSelectionModel().getSelectedIndex();
    	
    	// Avoiding error on unselected option
    	if (index == -1 || index >= dungeonPaths.size()) return;
    	try {
			loadDungeon(dungeonPaths.get(index));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Restarts dungeon with all entities in their initial position
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    void restart(ActionEvent event) throws FileNotFoundException {
    	restartDungeon();
    }
    
    /**
     * Restart the current dungeon by calling the loadDungeon 
     * with the currently running dungeon as the path 
     * @throws FileNotFoundException
     */
    private void restartDungeon() throws FileNotFoundException {
    	String pastDungeon = this.loadedDungeonPath;
    	if (pastDungeon == null) return;
    	loadDungeon(pastDungeon);
    }

    /**
     * Load the dungeon to the primary stage, also shows the goal tooltip and
     * attaching a listener to player and dungeon goal to show popup on game over
     * or finished dungeon. The main menu will be hidden behind the loaded dungeon
     * once the function is called. Also requests keyboard press focus.
     * @param pathname Pathname of the dungeon (json) file
     * @throws FileNotFoundException
     */
    private void loadDungeon(String pathname) throws FileNotFoundException {
    	clearDungeon();
    	// System.out.println(pathname);
    	int diffboxhealth = diffbox.getSelectionModel().getSelectedIndex();
    	this.startingHp = 5 - diffboxhealth * 2;
    	
    	DungeonFullLoader dungeonLoader = new DungeonFullLoader(pathname, disablewitch.isSelected(), disablehound.isSelected());
    	this.loadedDungeon = dungeonLoader.load();
    	this.loadedDungeonPath = pathname;
    	Dungeon dungeon = this.loadedDungeon;
    	List<ImageView> initialEntities = dungeonLoader.getEntities();
    	this.numGoals = 0;
    	
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
        

        int x = dungeon.getWidth();
        for (int y = 1; y < dungeon.getHeight(); y++) {
            squares.add(new ImageView(blank), x, y);
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        //Show tooltip of progress in goals
        DungeonGoals goals = dungeon.getGoal();
        if(goals instanceof MultipleGoals) {
        	for(DungeonGoals g : ((MultipleGoals) goals).getGoals()) {
        		displayGoal(g);
        		this.numGoals += 2;
        	}
        }else {
        	displayGoal(goals);
        }
                
        this.stage.setWidth((dungeon.getWidth() + 1) * 32 + 16);
        this.stage.setHeight((dungeon.getHeight() + 3) * 32);
        this.controllerOn = true;
        squares.requestFocus();
        
        Player player = dungeon.getPlayer();
        player.setHP(this.startingHp);
        player.aliveProperty().addListener(new ChangeListener<Boolean>() {
        	@Override
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean oldValue, Boolean newValue) {
        		popupOnDeath("You died");
        	}
        });
        showHealth();
        player.getHP().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
            		Number oldValue, Number newValue) {
            	showHealth();
            }
        });
        
        DungeonGoals dGoal = loadedDungeon.getGoal();
        dGoal.achieved().addListener(new ChangeListener<Boolean>() {
        	@Override
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean oldValue, Boolean newValue) {
        		popupOnFinish("You have completed your dungeon");
        		disableController();
        	}
        });
    }
    
    /**
     * Displays the players current health at the bottom of the screen
     */
    private void showHealth() {
    	Player p = loadedDungeon.getPlayer();
    	Image heart = new Image("/heart.png");
    	int health = p.getHP().get();
    	int total = startingHp;
    	for(int i = 0; i < total; i++) {
    		clearHealth(this.loadedDungeon.getWidth() - i);
    	}
    	for(int i = 0; i < health; i++) {
    		this.squares.add(new ImageView(heart), this.loadedDungeon.getWidth()-i, this.loadedDungeon.getHeight());
    	}
    	this.squares.add(new Label("HP:"), this.loadedDungeon.getWidth()-total ,this.loadedDungeon.getHeight());
    }
    
    /**
     * Helper function for above, used to redraw the health of the player 
     * @param Current row to clear
     */
    private void clearHealth(int row) {
    	ObservableList<Node> childrens = this.squares.getChildren();
    	for(Node node : childrens) {
    	    if(node instanceof ImageView && GridPane.getRowIndex(node) == this.loadedDungeon.getHeight() && GridPane.getColumnIndex(node) == row) {
    	    	ImageView imageView = (ImageView) node; // use what you want to remove
    	        this.squares.getChildren().remove(imageView);
    	        break;
    	    }
    	}
    }

    
    /**
     * Display goals of the dungeon under the dungeon
     * @param g (DungeonGoals)
     */
    private void displayGoal(DungeonGoals g) {
    	if(g instanceof TreasureGoal) {
            Image treasure = new Image("/gold_pile.png");
            Label l = new Label();
            l.setMaxWidth(Double.MAX_VALUE);
            l.textProperty().bind(Bindings.concat(g.getProgress().asString(), "/", g.getTotal().asString()));
            this.squares.add(new ImageView(treasure), this.numGoals, this.loadedDungeon.getHeight());
            this.squares.add(l, this.numGoals + 1, this.loadedDungeon.getHeight());
            this.squares.setFillWidth(l, true);

    	} else if (g instanceof EnemiesGoal) {
    		Image enemy = new Image("/deep_elf_master_archer.png");
            Label l = new Label();
            l.setMaxWidth(Double.MAX_VALUE);
            l.textProperty().bind(Bindings.concat(g.getProgress().asString(), "/", g.getTotal().asString()));
            this.squares.add(new ImageView(enemy), this.numGoals, this.loadedDungeon.getHeight());
            this.squares.add(l, this.numGoals+1, this.loadedDungeon.getHeight());
            this.squares.setFillWidth(l, true);
    	} else if (g instanceof FloorSwitchGoal) {
    		Image boulder = new Image("/boulder.png");
            Label l = new Label();
            l.setMaxWidth(Double.MAX_VALUE);
            l.textProperty().bind(Bindings.concat(g.getProgress().asString(), "/", g.getTotal().asString()));
            this.squares.add(new ImageView(boulder), this.numGoals, this.loadedDungeon.getHeight());
            this.squares.add(l, this.numGoals+1, this.loadedDungeon.getHeight());
            this.squares.setFillWidth(l, true);
    	} else if (g instanceof ExitGoal) {
    		Image exit = new Image("/exit.png");
    		Label l = new Label();
    		l.setMaxWidth(Double.MAX_VALUE);
//          l.setMaxHeight(30);
    		l.textProperty().bind(Bindings.concat("Last"));
	        this.squares.add(new ImageView(exit), this.numGoals, this.loadedDungeon.getHeight());
	        this.squares.add(l, this.numGoals+1, this.loadedDungeon.getHeight());
	        this.squares.setFillWidth(l, true);
    	}
    }
    
    /**
     * Show popup on player death. Takes a string as information box
     * @param string
     */
    private void popupOnDeath(String string) {
    	try {
			new PopupOnDeathApplication(this, string);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Show popup on dungeon completion. Takes a string as information box
     * @param string
     */
    private void popupOnFinish(String string) {
    	try {
			new PopupOnFinishApplication(this, string);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Clear the dungeon from the primary stage, showing the main menu
     */
    private void clearDungeon() {
    	this.loadedDungeon = null;
    	this.loadedDungeonPath = null;
    	squares.getChildren().clear();
    	this.numGoals = 0;
    	this.stage.setWidth(400 + 16);
    	this.stage.setHeight(300 + 32 + 7);
    }

    /**
     * Set stage of the dungeon, which will be needed by the controller to
     * set up the stage. Only called once by the dungeonapplication.
     * @param stage
     */
    public void setStage(Stage stage) {
    	this.stage = stage;
    }

    /**
     * Drop the bomb
     * @param player
     */
    private void dropBomb(Player player) {
    	int x = player.getX();
    	int y = player.getY();
    	
    	// Instantiate bomb_lit object, and add to the dungeon with attaching to the ticklistener
    	Bomb_Lit bomb = new Bomb_Lit(loadedDungeon, x, y);
    	
		loadedDungeon.addEntity(bomb);
		loadedDungeon.setTickListener();
		
		// Load image to the squares and add visibility listener (position listener is not needed since the bomb won't move)
		Image bomb1 = new Image("/bomb_1.png");
		Image bomb2 = new Image("/bomb_2.png");
		Image bomb3 = new Image("/bomb_3.png");
		Image bomb4 = new Image("/bomb_4.png");
		Image bomb5 = new Image("/bomb_5.png");
		Image bomb6 = new Image("/bomb_6.png");
		Image bomb7 = new Image("/bomb_7.png");
		Image bomb8 = new Image("/bomb_8.png");
		Image bomb9 = new Image("/bomb_9.png");
		Image bomb10 = new Image("/bomb_10.png");
		ImageView display = new ImageView(bomb1);
		squares.add(display, x, y);
		
		bomb.timeLeft().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	switch (newValue.intValue()) {
            	case 7:
            		display.setImage(bomb2);
            		break;
            	case 6:
            		display.setImage(bomb3);
            		break;
            	case 5:
            		display.setImage(bomb4);
            		break;
            	case 4:
            		display.setImage(bomb5);
            		break;
            	case 3:
            		display.setImage(bomb6);
            		break;
            	case 2:
            		display.setImage(bomb7);
            		break;
            	case 1:
            		display.setImage(bomb8);
            		break;
            	case 0:
            		display.setImage(bomb9);
            		break;
            	case -1:
            		display.setImage(bomb10);
            		break;
            	case -2:
            		display.setImage(null);
            		break;
            	default:
            		break;
            	}
            }
        });
    }
    
    /**
     * Get the loaded dungeon
     * @return (Dungeon) loaded dungeon
     */
    public Dungeon getDungeon() {
    	return this.loadedDungeon;
    }

    /**
     * Create a new random dungeon
     * @throws FileNotFoundException
     */
    public void newRandomDungeon() throws FileNotFoundException {
    	Random rand = new Random();
    	int random = rand.nextInt(dungeonPaths.size());
    	System.out.println(random);
    	loadDungeon(dungeonPaths.get(random));
    }
    
    /**
     * Disable keyboard controller. Only called when the dungeon is done.
     */
    private void disableController() {
    	this.controllerOn = false;
    }
}

