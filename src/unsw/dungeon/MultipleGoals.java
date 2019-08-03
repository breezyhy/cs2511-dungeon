package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MultipleGoals extends DungeonGoals {
    private String name;
    private List<DungeonGoals> subgoals;

    public MultipleGoals(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.subgoals = new ArrayList<>();
    }

    public void add(DungeonGoals goal) {
        subgoals.add(goal);
    }

    public void remove(DungeonGoals goal) {
        subgoals.add(goal);
    }
    
    public List<DungeonGoals> getGoals() {
    	return this.subgoals;
    }

    public BooleanProperty achieved() {
        BooleanProperty achieved = new SimpleBooleanProperty(false);
    	if (this.subgoals.size() == 0)
            return achieved;
        achieved.set(true);
        if (name.equals("AND")) {
            for (DungeonGoals a : subgoals) {
                if (!a.achieved().get())
                    achieved.set(false);
            }
        } else {
            achieved.set(false);
            for (DungeonGoals a : subgoals) {
                if (a.achieved().get())
                    achieved.set(true);
            }
        }

        return achieved;
    }
}