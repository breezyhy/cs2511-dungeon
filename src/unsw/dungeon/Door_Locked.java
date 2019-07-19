package unsw.dungeon;

public class Door_Locked implements DoorState {
    @Override
    public boolean showState() {
        return false;
    }

    @Override
    public void trigger(Door s) {
        s.setState(new Door_Unlocked());
    }
}