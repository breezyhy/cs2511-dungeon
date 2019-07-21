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

    public void setGoal(DungeonGoals goals) {
        this.goals = goals;
    }

    public DungeonGoals getGoal() {
        return this.goals;
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

    public ArrayList<GameTickSubscriber> getTickListener() {
        ArrayList<GameTickSubscriber> listener = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof GameTickSubscriber)
                listener.add((GameTickSubscriber) e);
        }
        return listener;
    }

    // Game tick loader
    // Needs to be loaded initially
    public void initiateTicker() {
        if (gametick != null)
            throw new UnsupportedOperationException();
        this.gametick = new GameTick(this);
        this.gametick.attachTickListener(this);
        player.registerObserver(this.gametick);
    }

    // Entity observer interactions
    // Needs to be loaded initially
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

        if (exit == null)
            return;
        System.out.println("Attaching exit-player observer");
        player.registerObserver(exit);
    }

    public void updateAllBoulders() {
        List<Boulder> boulders = new ArrayList<>();
        for (Entity entity : entities) {
            if ((entity instanceof Boulder)) {
                ((Boulder) entity).notifyObservers();
            }
        }
    }

    // Loader's functions
    public Exit getExit() {
        for (Entity e : entities) {
            if (e instanceof Exit)
                return ((Exit) e);
        }
        return null;
    }

    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Enemy)
                enemies.add((Enemy) e);
        }
        return enemies;
    }

    public ArrayList<Treasure> getTreasures() {
        ArrayList<Treasure> treasures = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof Treasure)
                treasures.add((Treasure) e);
        }
        return treasures;
    }

    public ArrayList<FloorSwitch> getFloorSwitch() {
        ArrayList<FloorSwitch> fs = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof FloorSwitch)
                fs.add((FloorSwitch) e);
        }
        return fs;
    }
}
