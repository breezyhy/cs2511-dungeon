package unsw.dungeon;

public class ExitGoal extends DungeonGoals implements Observer {
    private String name;
    private boolean achieved;

    public ExitGoal (String name){
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

    @Override
    public void update(Subject obj) {
        achieved = ((Exit) obj).getStatus();
        if (achieved) System.out.println("ExitGoal has been achieved");
    }
}