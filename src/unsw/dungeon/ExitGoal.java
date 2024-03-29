package unsw.dungeon;
/**
 * Implements the ExitGoal that must be achieved in order to finish a dungeon if presribed
 */
import javafx.beans.property.SimpleBooleanProperty;

public class ExitGoal extends DungeonGoals implements Observer {
    private String name;
    private SimpleBooleanProperty achieved;

    public ExitGoal(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.achieved = new SimpleBooleanProperty(false);
    }
    
    public String getName() {
    	return this.name;
    }

    public void add(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }

    public void remove(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }

    public SimpleBooleanProperty achieved() {
        return achieved;
    }

    @Override
    public void update(Subject obj) {
        achieved.set(((Exit) obj).getStatus());
        if (achieved.get())
            System.out.println("ExitGoal has been achieved");
    }
}