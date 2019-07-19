package unsw.dungeon;

public class Door_Unlocked implements DoorState {
    @Override
    public boolean showState() {
        return true;
    }

    @Override
    public void trigger(Door s) {
        // Once the door is unlocked, it won't be locked
        return;
    }
}