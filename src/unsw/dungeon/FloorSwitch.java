package unsw.dungeon;

public class FloorSwitch extends EntityNonblocking implements Observer {
    private FloorSwitchState state;

    public FloorSwitch(int x, int y) {
        super(x, y);
        this.state = new FloorSwitch_Off();
    }

    @Override
    public void update(Subject obj){
        Boulder boulder = (Boulder) obj;
        if (boulder.getX() == getX() && boulder.getY() == getY()) {
            switchState();
            System.out.println(state());
        }
        if (boulder.getPrevX() == getX() && boulder.getPrevY() == getY()) {
            switchState();
            System.out.println(state());
        }
    }

    private void switchState () {
        state.trigger(this);
    }

    public void setState (FloorSwitchState state) {
        this.state = state;
    }

    public boolean state() {
        return state.showState();
    }
}
