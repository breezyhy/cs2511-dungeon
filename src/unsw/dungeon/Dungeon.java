/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private DungeonGoals goals;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goals = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void setGoal(DungeonGoals goals){
        this.goals = goals;
    }

    public List<Entity> getCollidingEntity(int x, int y) {
        List<Entity> collide = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == y) {
                collide.add(entity);
            }
        }
        return collide;
    }

    public void attachBoulderObservers(){
        List<Boulder> boulders = new ArrayList<>();
        List<FloorSwitch> fswitches = new ArrayList<>();
        for (Entity entity : entities) {
            if ((entity instanceof Boulder)) {
                boulders.add((Boulder) entity);
            } else if ((entity instanceof FloorSwitch)) {
                fswitches.add((FloorSwitch) entity);
            }
        }

        for (Boulder boulder : boulders){
            for (FloorSwitch fs : fswitches) {
                boulder.registerObserver(fs);
            }
        }
    }

    public void updateAll(){
        List<Boulder> boulders = new ArrayList<>();
        for (Entity entity : entities) {
            if ((entity instanceof Boulder)) {
                ((Boulder) entity).notifyObservers();
            }
        }
    }

    
}
