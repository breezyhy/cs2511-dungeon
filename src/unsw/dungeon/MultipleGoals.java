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

    public void add(DungeonGoals goal) {
        subgoals.add(goal);
        goal.achieved().addListener((event) -> {
        	// Call achieved on any change on subgoal
        	achieved();
        });
    }

    public void remove(DungeonGoals goal) {
        subgoals.add(goal);
    }
    
    public List<DungeonGoals> getGoals() {
    	return this.subgoals;
    }

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