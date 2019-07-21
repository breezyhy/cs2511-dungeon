package unsw.dungeon;

public class Exit extends EntityNonblocking implements Observer, Subject {

    private boolean playerExit = false;
    private Observer exitObserver = null;

    public Exit(int x, int y) {
        super(x, y);
    }

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

    // TODO
    // Method to check whether all other goals has been satisfied
}
