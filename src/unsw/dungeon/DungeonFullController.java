package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Pane textpane;
    
	@FXML
	private Pane mainpane;

	// Internals
	private Stage stage = null;
	
	// Others
    private List<String> dungeonPaths;
	private Dungeon loadedDungeon = null;
	private String loadedDungeonPath = null;
	private ChoiceBox<String> choicebox = null;
	private int numGoals;
	private boolean controllerOn;
	
	
	
    public DungeonFullController(List<String> dungeonPaths) {
    	// dungeonPaths will take all dungeons and make the required levels on the dungeon selector
    	this.dungeonPaths = dungeonPaths;
    	this.numGoals = 0;
    }
    
    
    @FXML
	public void initialize() {
    	// System.out.println(levelmenu);
    	
    	textpane.getChildren().add(new Text("Select dungeon"));
    	
    	choicebox = new ChoiceBox<String>();
    	mainpane.getChildren().add(choicebox);
    	
    	for (int i = 0; i < dungeonPaths.size(); i++) {
    		
    		// Set up the levelmenu
    		String name = dungeonPaths.get(i);
    		String rname = name.replaceAll(".json$", "");
    		// System.out.println("here " + rname);
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
    
    @FXML
    void handleKeyPress(KeyEvent event) {
    	// System.out.println("keyevent " + event);
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
        case N:
            // Get to the next level
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
    
    @FXML
    void restart(ActionEvent event) throws FileNotFoundException {
    	restartDungeon();
    }
    
    private void restartDungeon() throws FileNotFoundException {
    	String pastDungeon = this.loadedDungeonPath;
    	if (pastDungeon == null) return;
    	loadDungeon(pastDungeon);
    }

    private void loadDungeon(String pathname) throws FileNotFoundException {
    	clearDungeon();
    	// System.out.println(pathname);
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
        player.aliveProperty().addListener(new ChangeListener<Boolean>() {
        	@Override
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean oldValue, Boolean newValue) {
        		popupOnDeath("You died");
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
    
    private void displayGoal(DungeonGoals g) {
    	if(g instanceof TreasureGoal) {
            Image treasure = new Image("/gold_pile.png");
            Label l = new Label();
            l.setMaxWidth(Double.MAX_VALUE);
//            l.setMaxHeight(30);
            l.textProperty().bind(Bindings.concat(g.getProgress().asString(), "/", g.getTotal().asString()));
            this.squares.add(new ImageView(treasure), this.numGoals, this.loadedDungeon.getHeight());
            this.squares.add(l, this.numGoals + 1, this.loadedDungeon.getHeight());
            this.squares.setFillWidth(l, true);

    	}else if (g instanceof EnemiesGoal) {
    		Image enemy = new Image("/deep_elf_master_archer.png");
            Label l = new Label();
            l.setMaxWidth(Double.MAX_VALUE);
//            l.setMaxHeight(30);
            l.textProperty().bind(Bindings.concat(g.getProgress().asString(), "/", g.getTotal().asString()));
            this.squares.add(new ImageView(enemy), this.numGoals, this.loadedDungeon.getHeight());
            this.squares.add(l, this.numGoals+1, this.loadedDungeon.getHeight());
            this.squares.setFillWidth(l, true);
    	}else if (g instanceof FloorSwitchGoal) {
    		Image boulder = new Image("/boulder.png");
            Label l = new Label();
            l.setMaxWidth(Double.MAX_VALUE);
//            l.setMaxHeight(30);
            l.textProperty().bind(Bindings.concat(g.getProgress().asString(), "/", g.getTotal().asString()));
            this.squares.add(new ImageView(boulder), this.numGoals, this.loadedDungeon.getHeight());
            this.squares.add(l, this.numGoals+1, this.loadedDungeon.getHeight());
            this.squares.setFillWidth(l, true);
    	}
    }
    

    private void popupOnDeath(String string) {
    	try {
			new PopupOnDeathApplication(this, string);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void popupOnFinish(String string) {
    	try {
			new PopupOnFinishApplication(this, string);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void clearDungeon() {
    	this.loadedDungeon = null;
    	this.loadedDungeonPath = null;
    	squares.getChildren().clear();
    	this.numGoals = 0;
    	this.stage.setWidth(400 + 16);
    	this.stage.setHeight(300 + 32 + 7);
    }
    
    public GridPane getSquares() {
    	return this.squares;
    }
    
    public void setStage(Stage stage) {
    	this.stage = stage;
    }

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
    
    public Dungeon getDungeon() {
    	return this.loadedDungeon;
    }

    public void newRandomDungeon() throws FileNotFoundException {
    	Random rand = new Random();
    	int random = rand.nextInt(dungeonPaths.size());
    	System.out.println(random);
    	loadDungeon(dungeonPaths.get(random));
    }
    
    private void disableController() {
    	this.controllerOn = false;
    }
}

