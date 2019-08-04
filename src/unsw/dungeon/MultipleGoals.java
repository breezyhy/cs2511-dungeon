package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MultipleGoals extends DungeonGoals {
    private String name;
    private List<DungeonGoals> subgoals;
    private BooleanProperty achieved;
    
    public MultipleGoals(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.subgoals = new ArrayList<>();
        this.achieved = new SimpleBooleanProperty(false);
    }

    /**
     * Add subgoal for this multiplegoals and attaching a listener for achieved state of the subgoal
     */
    public void add(DungeonGoals goal) {
        subgoals.add(goal);
        goal.achieved().addListener((event) -> {
        	// Call achieved on any change on subgoal
        	achieved();
        });
    }

    /**
     * Remove subgoal for this multiplegoals
     */
    public void remove(DungeonGoals goal) {
        subgoals.add(goal);
    }
    
    /**
     * Get subgoals of this multiplegoals
     * @return subgoals (List<DungeonGoals>)
     */
    public List<DungeonGoals> getGoals() {
    	return this.subgoals;
    }

    /**
     * Checks if the status of this multipleGoals is true/false and returns the booleanproperty of the achieved state
     * @return BooleanProperty
     */
    public BooleanProperty achieved() {
    	if (this.subgoals.size() == 0)
            return achieved;
    	
    	boolean tempStatus = true;

        if (name.equals("AND")) {
            for (DungeonGoals a : subgoals) {
                if (!a.achieved().get())
                    tempStatus = false;
            }
        } else {
            tempStatus = false;
            for (DungeonGoals a : subgoals) {
                if (a.achieved().get())
                    tempStatus = true;
            }
        }
        
        achieved.set(tempStatus);

        return achieved;
    }
}