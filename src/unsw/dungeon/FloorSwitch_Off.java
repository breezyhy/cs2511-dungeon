package unsw.dungeon;

public class FloorSwitch_Off implements FloorSwitchState {
    @Override
    public boolean showState() {
        return false;
    }

    @Override
    public void trigger(FloorSwitch s) {
        s.setState(new FloorSwitch_On());
    }
}