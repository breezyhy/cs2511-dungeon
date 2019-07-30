package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

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

    public boolean achieved() {
        if (this.subgoals.size() == 0)
            return false;

        boolean achieved = true;
        if (name.equals("AND")) {
            for (DungeonGoals a : subgoals) {
                if (!a.achieved())
                    achieved = false;
            }
        } else {
            achieved = false;
            for (DungeonGoals a : subgoals) {
                if (a.achieved())
                    achieved = true;
            }
        }

        return achieved;
    }
}