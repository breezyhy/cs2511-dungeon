package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
/**
 * Implements the floor switch goal as detailed in the spec to complete or partially complete a dungeon
 * @author z5161251
 *
 */
public class FloorSwitchGoal extends DungeonGoals implements MultipleObserver {
    private String name;
    private SimpleBooleanProperty achieved;
    private ArrayList<MultipleSubject> subjects;
    private SimpleIntegerProperty progress;
    private SimpleIntegerProperty total;

    public FloorSwitchGoal(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.progress = new SimpleIntegerProperty(0);
        this.achieved = new SimpleBooleanProperty(false);
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
    
    public void add(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }

    public void remove(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }
    
    public String getName() {
    	return this.name;
    }

    public SimpleBooleanProperty achieved() {
        return achieved;
    }

    /**
     * Checks to see whether all floor switches are in their 'on' state
     */
    @Override
    public void update(MultipleSubject obj) {
        boolean done = true;
        int count = 0;
        for (MultipleSubject fs : subjects) {
            if (!((FloorSwitch) fs).showState())
                done = false;
            else
            	count++;
        }
        setProgress(count);
        achieved.set(done);
        if (achieved.get())
            System.out.println("FloorSwitchGoal has been achieved");
    }

    @Override
    public void addSubject(MultipleSubject obj) {
        subjects.add(obj);
        System.out.println("Object added: " + obj.getClass());
        this.total.set(subjects.size());
    }

    @Override
    public void removeSubject(MultipleSubject obj) {
        if (subjects.contains(obj))
            subjects.remove(obj);
        this.total.set(subjects.size());
    }
}