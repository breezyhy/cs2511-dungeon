package unsw.dungeon;

/**
 * Implements the state pattern for a locked and unlocked door in an interface
 */
public interface DoorState {
    public boolean showState();

    public void trigger(Door s);
}