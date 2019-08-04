package unsw.dungeon;

/**
 * Implements the door unlocked state, which is used by the door entity
 * and the state pattern implemented with door state
 */
public class Door_Unlocked implements DoorState {
    @Override
    public boolean showState() {
        return true;
    }
    /**
     * Cannot re-lock a door once opened
     */
    @Override
    public void trigger(Door s) {
        // Once the door is unlocked, it won't be locked
        return;
    }
}