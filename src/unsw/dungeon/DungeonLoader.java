package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        dungeon.attachBoulderObservers();
        dungeon.updateAllBoulders();
        dungeon.attachExitObservers();
        dungeon.initiateTicker();
        
        JSONObject goals = json.getJSONObject("goal-condition");
        DungeonGoals dGoals = loadGoals(goals, dungeon);
        dungeon.setGoal(dGoals);
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(x, y);
            onLoad(floorSwitch);
            entity = floorSwitch;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "key":
            System.out.println("Creating object key");
            int idK = json.getInt("id");
            Key key = new Key(x, y, idK);
            onLoad(key);
            entity = key;
            System.out.println("Created object key");
            break;
        case "door":
            System.out.println("Creating object door");
            int idD = json.getInt("id");
            Door door = new Door(x, y, idD);
            onLoad(door);
            entity = door;
            System.out.println("Created object door");
            break;
        case "sword":
            Sword sword = new Sword(x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "bomb":
            Bomb_Unlit bomb = new Bomb_Unlit(x, y);
            onLoad(bomb);
            entity = bomb;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "invincibility":
            Potion potion = new Potion(x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "hound":
            Hound hound = new Hound(dungeon, x, y);
            onLoad(hound);
            entity = hound;
            break;
        case "witch":
            Witch witch = new Witch(dungeon, x, y);
            onLoad(witch);
            entity = witch;
            break;
        default:
            break;
        }
        dungeon.addEntity(entity);
    }

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Wall wall);
    public abstract void onLoad(Exit exit);
    public abstract void onLoad(FloorSwitch floorSwitch);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Door door);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Bomb_Unlit bomb);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(Potion potion);
    public abstract void onLoad(Enemy enemy);
    public abstract void onLoad(Hound hound);
    public abstract void onLoad(Witch witch);

    private DungeonGoals loadGoals(JSONObject jsongoals, Dungeon dungeon){
        String type = jsongoals.getString("goal");

        DungeonGoals dGoals = null;
        switch(type) {
            case "AND":
            case "OR":
                MultipleGoals mGoal = new MultipleGoals(type);
                JSONArray subgoals = jsongoals.getJSONArray("subgoals");
                for (int i = 0; i < subgoals.length(); i++) {
                    DungeonGoals subgoal = loadGoals(subgoals.getJSONObject(i), dungeon);
                    mGoal.add(subgoal);
                }
                dGoals = mGoal;
                break;
            case "exit":
                ExitGoal sGoal = new ExitGoal("exit");
                dGoals = sGoal;
                attachGoal(sGoal, dungeon);
                break;
            case "enemies":
                EnemiesGoal eGoal = new EnemiesGoal("enemies");
                dGoals = eGoal;
                attachGoal(eGoal, dungeon);
                break;
            case "treasure":
                TreasureGoal tGoal = new TreasureGoal("treasure");
                dGoals = tGoal;
                attachGoal(tGoal, dungeon);
                break;
            case "boulders":
                FloorSwitchGoal fGoal = new FloorSwitchGoal("boulders");
                dGoals = fGoal;
                attachGoal(fGoal, dungeon);
                break;
            default:
                break;
        }

        return dGoals;
    }

    private void attachGoal(ExitGoal goal, Dungeon dungeon){
        Exit exit = dungeon.getExit();
        if (exit == null) throw new UnsupportedOperationException();
        // Deal with exit and goal
        exit.registerObserver(goal);
    }

    private void attachGoal(EnemiesGoal goal, Dungeon dungeon){
        ArrayList<Enemy> enemies = dungeon.getEnemies();
        if (enemies.size() == 0) throw new UnsupportedOperationException();
        // Deal with enemies and goal
        for (Enemy e : enemies){
            System.out.println("Enemy registered on observer");
            e.registerObserver(goal);
        }
    }

    private void attachGoal(TreasureGoal goal, Dungeon dungeon){
        ArrayList<Treasure> treasures = dungeon.getTreasures();
        if (treasures.size() == 0) throw new UnsupportedOperationException();
        // Deal with treasures and goal
        for (Treasure t : treasures){
            t.registerObserver(goal);
        }
    }

    private void attachGoal(FloorSwitchGoal goal, Dungeon dungeon){
        ArrayList<FloorSwitch> fs = dungeon.getFloorSwitch();
        if (fs.size() == 0) throw new UnsupportedOperationException();
        // Deal with floorswitches and goal
        for (FloorSwitch flSwitch : fs){
            flSwitch.registerObserver(goal);
        }
    }
}
