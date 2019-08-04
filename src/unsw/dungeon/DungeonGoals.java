package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class DungeonGoals {
	
	/**
	 * Class the gives the progress of a player toward the goal as a IntegerProperty
	 * eg. 2/3 treasure collected
	 * @return Number of items completed
	 */
	public SimpleIntegerProperty getProgress() {
		throw new UnsupportedOperationException();
	}
	
	public SimpleIntegerProperty getTotal() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Add an item to track towards the goals progress
	 * @param goal
	 */
	public void add(DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove an item that was being tracked for the goals progress
	 * @param goal
	 */
	public void remove(DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the state of the goal to be achieved
	 * @return true if goal has been achieved
	 * @return false if goal has not been achieved
	 */
	public BooleanProperty achieved() {
		throw new UnsupportedOperationException();
	}

}