package unsw.dungeon;

public interface FloorSwitchState {
    public boolean showState();

    public void trigger(FloorSwitch s);
}