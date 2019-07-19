package unsw.dungeon;

public class FloorSwitchGoal extends DungeonGoals {
    String name;
    boolean achieved;

    public FloorSwitchGoal (String name){
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.achieved = false;
    }
    
    public void add (DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}
    
    public void remove (DungeonGoals goal) {
		throw new UnsupportedOperationException();
    }
    
    public boolean achieved () {
        return achieved;
    }
}