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
    private GameTick gametick;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goals = null;
        this.gametick = null;
    }

    /**
     * Get width of the dungeon, in cell unit
     * @return width of dungeon
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get height of the dungeon, in cell unit
     * @return height of dungeon
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get player of the dungeon
     * @return Player character
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set player of the dungeon
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Add entity to the dungeon
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    /**
     * Remove entity from the dungeon
     * @param entity
     */
    public void removeEntity(Entity entity) {
    	if (entities.contains(entity))
    		entities.remove(entity);
    }

    /**
     * Set the goal of the dungeon, which can be a single goal or multiplegoals
     * Goals are wrapped under DungeonGoals composite abstract class
     * @param goals
     */
    public void setGoal(DungeonGoals goals) {
        this.goals = goals;
    }
    
    /**
     * Get the goal of the dungeon
     * @return
     */
    public DungeonGoals getGoal() {
        return this.goals;
    }

    /**
     * Get entity on the given coordinate
     * @param x
     * @param y
     * @return list of all entity on the coordinate, in form of List<Entity>
     */
    public List<Entity> getCollidingEntity(int x, int y) {
        List<Entity> collide = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getX() == x && entity.getY() == y) {
                collide.add(entity);
            }
        }
        return collide;
    }

    /**
     * Get entities on the surrounding of the given coordinate
     * Surrounding means directly adjacent (direct left/right/up/down)
     * @param x
     * @param y
     * @return list of all surrounding entities, in form of List<Entity>
     */
    public List<Entity> getSurroundingEntity(int x, int y) {
        List<Entity> collide = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity == null) continue;
            if (entity.getX() == (x + 1) && entity.getY() == y) {
                collide.add(entity);
            }
            if (entity.getX() == (x - 1) && entity.getY() == y) {
                collide.add(entity);
            }
            if (entity.getX() == x && entity.getY() == (y + 1)) {
                collide.add(entity);
            }
            if (entity.getX() == x && entity.getY() == (y - 1)) {
                collide.add(entity);
            }
        }
        return collide;
    }

    /**
     * Set tick listener for all entities by calling the gametick
     */
    public void setTickListener() {
    	this.gametick.attachTickListener(this);
    }
    
    /**
     * Get any gametick listener (entities) from list of entities inside the dungeon
     * @return list of gametick listener
     */
    public ArrayList<GameTickSubscriber> getTickListener() {
        ArrayList<GameTickSubscriber> listener = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof GameTickSubscriber)
                listener.add((GameTickSubscriber) e);
        }
        return listener;
    }

    /**
     * Game tick loader which gets initialized once by DungeonLoader
     */
    public void initiateTicker() {
        if (gametick != null)
            throw new UnsupportedOperationException();
        this.gametick = new GameTick(this);
        this.gametick.attachTickListener(this);
        player.registerObserver(this.gametick);
    }

    /**
     * Attaching floor switch (observer) to boulders
     */
    public void attachBoulderObservers() {
        List<Boulder> boulders = new ArrayList<>();
        List<FloorSwitch> fswitches = new ArrayList<>();
        for (Entity entity : entities) {
            if ((entity instanceof Boulder)) {
                boulders.add((Boulder) entity);
            } else if ((entity instanceof FloorSwitch)) {
                fswitches.add((FloorSwitch) entity);
            }
        }

        for (Boulder boulder : boulders) {
            for (FloorSwitch fs : fswitches) {
                boulder.registerObserver(fs);
            }
        }
    }

    /**
     * Attaching exit (observer) to player
     */
    public void attachExitObservers() {
        Player player = null;
        Exit exit = null;
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                player = (Player) entity;
            } else if (entity instanceof Exit) {
                exit = (Exit) entity;
            }
        }

        if (exit == null || player == null)
            return;
        System.out.println("Attaching exit-player observer");
        player.registerObserver(exit);
    }

    /**
     * Notify all boulders in the beginning of the dungeon
     */
    public void updateAllBoulders() {
        for (Entity entity : entities) {
            if ((entity instanceof Boulder)) {
                ((Boulder) entity).notifyObservers();
            }
        }
    }

    /**
     * Get exit of the dungeon, which gets called by loader
     * @return exit object of the dungeon or null, depending on the dungeon
     */
    public Exit getExit() {
        for (Entity e : entities) {
            if (e instanceof Exit)
                return ((Exit) e);
        }
        return null;
    }

    /**
     * Get arraylist of enemies inside the dungeon. 
     * Gets called by the loader to attach EnemiesGoal
     * @return ArrayList of Enemy
     */
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Enemy)
                enemies.add((Enemy) e);
        }
        return enemies;
    }

    /**
     * Get arraylist of treasures inside the dungeon
     * Gets called by the loader to attach TreasureGoal
     * @return ArrayList of Treasure
     */
    public ArrayList<Treasure> getTreasures() {
        ArrayList<Treasure> treasures = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Treasure)
                treasures.add((Treasure) e);
        }
        return treasures;
    }

    /**
     * Get arraylist of floorswitch inside the dungeon
     * Gets called by the loader to attach FloorSwitchGoal
     * @return ArrayList of FloorSwitch
     */
    public ArrayList<FloorSwitch> getFloorSwitch() {
        ArrayList<FloorSwitch> fs = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof FloorSwitch)
                fs.add((FloorSwitch) e);
        }
        return fs;
    }
}
