package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class FloorSwitchGoal extends DungeonGoals implements MultipleObserver {
    private String name;
    private boolean achieved;
    private ArrayList<MultipleSubject> subjects;

    public FloorSwitchGoal(String name) {
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

    public boolean achieved() {
        return achieved;
    }

    @Override
    public void update(MultipleSubject obj) {
        // System.out.println("Called");
        boolean done = true;
        for (MultipleSubject fs : subjects) {
            if (!((FloorSwitch) fs).showState())
                done = false;
        }
        achieved = done;
        if (achieved)
            System.out.println("FloorSwitchGoal has been achieved");
    }

    @Override
    public void addSubject(MultipleSubject obj) {
        subjects.add(obj);
        System.out.println("Object added: " + obj.getClass());
    }

    @Override
    public void removeSubject(MultipleSubject obj) {
        if (subjects.contains(obj))
            subjects.remove(obj);
    }
}