package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A class used to track the progress of the player toward a treasure goal
 * prescribed by a dungeon
 */
public class TreasureGoal extends DungeonGoals implements MultipleObserver {
    private String name;
    private SimpleBooleanProperty achieved;
    private ArrayList<MultipleSubject> subjects;
    private SimpleIntegerProperty progress;
    private SimpleIntegerProperty total;

    public TreasureGoal(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.achieved = new SimpleBooleanProperty(false);
        this.progress = new SimpleIntegerProperty(0);
        this.subjects = new ArrayList<MultipleSubject>();
        this.total = new SimpleIntegerProperty(subjects.size());
    }
    
    public SimpleIntegerProperty getProgress() {
    	return this.progress;
    }
    
    public SimpleIntegerProperty getTotal() {
    	return this.total;
    }
    
    public void setProgress(int p) {    	
    	this.progress.set(p);
    }
    
    public String getName() {
    	return this.name;
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
    public SimpleBooleanProperty achieved() {
        return achieved;
    }

    /**
     * Checks to see if all treasure has been collected and updates 
     * achieved() accordingly
     */
    @Override
    public void update(MultipleSubject obj) {
        boolean done = true;
        int count = 0;
        for (MultipleSubject treasure : subjects) {
            if (!((Treasure) treasure).collected())
                done = false;
            else {
            	count++;
            }
        }
        setProgress(count);
        achieved.set(done);
        if (achieved.get())
            System.out.println("TreasureGoal has been achieved");
    }

    /**
     * Adds a treasure to the list of collectibles
     */
    @Override
    public void addSubject(MultipleSubject obj) {
        subjects.add(obj);
        System.out.println("Object added: " + obj.getClass());
        this.total.set(subjects.size());
    }
    /**
     * Removes an objects (treasure) from the list
     */
    @Override
    public void removeSubject(MultipleSubject obj) {
        if (subjects.contains(obj))
            subjects.remove(obj);
        this.total.set(subjects.size());

    }
}