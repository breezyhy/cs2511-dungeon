package unsw.dungeon;

public class EnemiesGoal extends DungeonGoals {
    String name;
    boolean achieved;

    public EnemiesGoal (String name){
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