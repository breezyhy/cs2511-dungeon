package unsw.dungeon;

/**
 * Class that implements a subject of an observer of type MultipleObserver
 * @author z5161251
 *
 */
public interface MultipleSubject {
    public void registerObserver(MultipleObserver o);

    public void removeObserver(MultipleObserver o);

    public void notifyObservers();
}