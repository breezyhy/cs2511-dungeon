package unsw.dungeon;

public class FloorSwitch_On implements FloorSwitchState {
    @Override
    public boolean showState() {
        return true;
    }

    @Override
    public void trigger(FloorSwitch s) {
        s.setState(new FloorSwitch_Off());
    }
}