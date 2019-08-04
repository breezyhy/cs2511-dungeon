package unsw.dungeon;
/**
 * Implements the floor switch entity that is used to achieve the Boulder goal as outlined in the spec
 * @author z5161251
 *
 */
public class FloorSwitch extends EntityNonblocking implements Observer, MultipleSubject {

    private FloorSwitchState state;
    private MultipleObserver floorSwitchObserver = null;

    public FloorSwitch(int x, int y) {
        super(x, y);
        this.state = new FloorSwitch_Off();
    }
    
    /**
     * Switch state depending on whether there is currently a boulder on the switch or if there was a
     * boulder that got moved off
     */
    @Override
    public void update(Subject obj) {
        Boulder boulder = (Boulder) obj;
        if (boulder.getX() == getX() && boulder.getY() == getY()) {
            switchState();
            // System.out.println(state());
        }
        if (boulder.getPrevX() == getX() && boulder.getPrevY() == getY()) {
            switchState();
            // m System.out.println(state());
        }

    }
    
    /**
     * Implements state switching as detailed in the State pattern
     */
    private void switchState() {
        state.trigger(this);
        notifyObservers();
    }

    public void setState(FloorSwitchState state) {
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
        if (floorSwitchObserver != null)
            floorSwitchObserver.update(this);
    }
}
