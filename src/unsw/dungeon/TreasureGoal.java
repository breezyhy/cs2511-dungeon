package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A class used to track the progress of the player toward a treasure goal
 * prescribed by a dungeon
 */
public class TreasureGoal extends DungeonGoals implements MultipleObserver {
    private String name;
    private boolean achieved;
    private ArrayList<MultipleSubject> subjects;

    public TreasureGoal(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.achieved = false;
        this.subjects = new ArrayList<MultipleSubject>();
    }
    
    public void add(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }

    public void remove(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }
    /**
     * @return true when the goal has been achieved by the player
     */
    public boolean achieved() {
        return achieved;
    }

    /**
     * Checks to see if all treasure has been collected and updates 
     * achieved() accordingly
     */
    @Override
    public void update(MultipleSubject obj) {
        boolean done = true;
        for (MultipleSubject treasure : subjects) {
            if (!((Treasure) treasure).collected())
                done = false;
        }
        achieved = done;
        if (achieved)
            System.out.println("TreasureGoal has been achieved");
    }

    /**
     * Adds a treasure to the list of collectibles
     */
    @Override
    public void addSubject(MultipleSubject obj) {
        subjects.add(obj);
        System.out.println("Object added: " + obj.getClass());
    }
    /**
     * Removes an objects (treasure) from the list
     */
    @Override
    public void removeSubject(MultipleSubject obj) {
        if (subjects.contains(obj))
            subjects.remove(obj);
    }
}