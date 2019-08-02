package unsw.dungeon;
/**
 * Interface used for the observer patter implemented across various classes
 */
public interface Subject {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();
}