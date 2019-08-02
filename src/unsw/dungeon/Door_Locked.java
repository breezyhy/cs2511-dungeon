package unsw.dungeon;
/**
 * A door state that implements the door_locked state for which a player
 * cannot pass through
 */
public class Door_Locked implements DoorState {
    @Override
    public boolean showState() {
        return false;
    }
    /**
     * Move to the next state, which is unlocked
     */
    @Override
    public void trigger(Door s) {
        s.setState(new Door_Unlocked());
    }
}