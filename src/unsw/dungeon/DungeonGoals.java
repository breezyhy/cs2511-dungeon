package unsw.dungeon;

public abstract class DungeonGoals {
    String name;
	public void add (DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}
	public void remove (DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}
	public boolean achieved () {
        throw new UnsupportedOperationException();
    }
    
}