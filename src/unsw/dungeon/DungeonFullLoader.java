package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonFullLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image exitImage;
    private Image switchImage;
    private Image boulderImage;
    private Image keyImage;
    private Image doorImage;
    private Image swordImage;
    private Image bombImage;
    private Image treasureImage;
    private Image potionImage;
    private Image enemyImage;
    private Image pinkDoor;
    private Image greyDoor;
    private Image pinkKey;
    private Image greyKey;

    public DungeonFullLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        wallImage = new Image("/brick_brown_0.png");
        exitImage = new Image("/exit.png");
        switchImage = new Image("/pressure_plate.png");
        boulderImage = new Image("/boulder.png");
        keyImage = new Image("/key.png");
        doorImage = new Image("/closed_door.png");
        swordImage = new Image("/greatsword_1_new.png");
        bombImage = new Image("/bomb_unlit.png");
        treasureImage = new Image("/gold_pile.png");
        potionImage = new Image("/brilliant_blue_new.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        pinkDoor = new Image("/pink_door.png");
        greyDoor = new Image("/grey_door.png");
        pinkKey = new Image("/pink_key.png");
        greyKey = new Image("/grey_key.png");
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(FloorSwitch floorSwitch){
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);
    }

    @Override
    public void onLoad(Boulder boulder){
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Key key){
    	ImageView view = new ImageView(keyImage);
    	switch(key.getID()) {
    	case 0:
            view = new ImageView(keyImage);
            break;
    	case 1:
            view = new ImageView(greyKey);
            break;
    	case 2:
            view = new ImageView(pinkKey);
            break;

    	}
        addEntity(key, view);
    }

    @Override
    public void onLoad(Door door){
    	ImageView view = new ImageView(doorImage);
    	switch(door.getId()) {
    	case 0:
            view = new ImageView(doorImage);
            break;
    	case 1:
            view = new ImageView(greyDoor);
            break;
    	case 2:
            view = new ImageView(pinkDoor);
            break;

    	}
        addEntity(door, view);
        trackState(door, view);
    }

    @Override
    public void onLoad(Sword sword){
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
    }
    
    @Override
    public void onLoad(Bomb_Unlit bomb){
        ImageView view = new ImageView(bombImage);
        addEntity(bomb, view);
    }
    
    @Override
    public void onLoad(Treasure treasure){
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }
    
    @Override
    public void onLoad(Potion potion){
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
    }
    
    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }

    private void addEntity(Entity entity, ImageView view) {
    	trackPositionVisibility(entity, view);
        entities.add(view);
    }

    // To override old entity image to new entity image, in case of any switching
    private void overrideEntityImage (Entity entity, ImageView view){

    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPositionVisibility(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
        entity.visible().addListener(new ChangeListener<Boolean>() {
        	@Override
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean oldValue, Boolean newValue) {
        		node.setVisible(newValue.booleanValue());
        	}
        });
    }
    
    // Track door state, whether it's been unlocked
    private void trackState(Door door, ImageView node) {
    	door.accessibleProperty().addListener(new ChangeListener<Boolean>() {
        	@Override
        	public void changed(ObservableValue<? extends Boolean> observable,
        			Boolean oldValue, Boolean newValue) {
        		Image openDoor = new Image("/open_door.png");
        		node.setImage(openDoor);
        	}
        });
    }

  
    public Dungeon getDungeon() {
    	return load();
    }

    public List<ImageView> getEntities() {
    	return this.entities;
    }

}
