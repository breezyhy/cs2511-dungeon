package unsw.dungeon;

public class FloorSwitch extends EntityNonblocking implements Observer, MultipleSubject {

    private FloorSwitchState state;
    private MultipleObserver floorSwitchObserver = null;

    public FloorSwitch(int x, int y) {
        super(x, y);
        this.state = new FloorSwitch_Off();
    }

    @Override
    public void update(Subject obj){
        Boulder boulder = (Boulder) obj;
        if (boulder.getX() == getX() && boulder.getY() == getY()) {
            switchState();
            // System.out.println(state());
        }
        if (boulder.getPrevX() == getX() && boulder.getPrevY() == getY()) {
            switchState();
            //m System.out.println(state());
        }

    }

    private void switchState () {
        state.trigger(this);
        notifyObservers();
    }

    public void setState (FloorSwitchState state) {
        this.state = state;
    }

    public boolean showState() {
        return state.showState();
    }

    @Override
    public void registerObserver(MultipleObserver o) {
        // System.out.println("Observer added: " + o.getClass());
        floorSwitchObserver = o;
        o.addSubject(this);
    }

    @Override
    public void removeObserver(MultipleObserver o) {
        if (floorSwitchObserver == o) {
            floorSwitchObserver.removeSubject(this);
            floorSwitchObserver = null;
        }
    }

    @Override
    public void notifyObservers() {
        if (floorSwitchObserver != null) floorSwitchObserver.update(this);
    }
}
