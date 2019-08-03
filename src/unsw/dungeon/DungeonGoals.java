package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class DungeonGoals {
	
	public SimpleIntegerProperty getProgress() {
		throw new UnsupportedOperationException();
	}
	
	public SimpleIntegerProperty getTotal() {
		throw new UnsupportedOperationException();
	}
	
	public void add(DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}

	public void remove(DungeonGoals goal) {
		throw new UnsupportedOperationException();
	}

	public BooleanProperty achieved() {
		throw new UnsupportedOperationException();
	}

}