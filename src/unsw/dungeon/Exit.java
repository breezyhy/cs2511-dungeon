package unsw.dungeon;
/**
 * Implements an exit that is both an entity in the spec and used to achieve the ExitGoal criteria
 * that is described in the spec
 *
 */
public class Exit extends EntityNonblocking implements Observer, Subject {

    private boolean playerExit = false;
    private Observer exitObserver = null;

    public Exit(int x, int y) {
        super(x, y);
    }
    /**
     * Used to notify oberservers of when a player is on the Exit
     */
    @Override
    public void update(Subject obj) {
        if (!(obj instanceof Player))
            return;
        Player player = (Player) obj;
        if (player.getX() == getX() && player.getY() == getY()) {
            playerExit = true;
            notifyObservers();
        } else {
            playerExit = false;
            notifyObservers();
        }
    }

    public boolean getStatus() {
        return playerExit;
    }

    @Override
    public void registerObserver(Observer o) {
        System.out.println("Registered observer " + o.getClass());
        exitObserver = o;
    }

    @Override
    public void removeObserver(Observer o) {
        if (exitObserver == o) {
            exitObserver = null;
        }
    }

    @Override
    public void notifyObservers() {
        if (exitObserver != null)
            exitObserver.update(this);
    }
}
