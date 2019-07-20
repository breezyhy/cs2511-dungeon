package unsw.dungeon;

public interface MultipleSubject {
    public void registerObserver(MultipleObserver o);
    public void removeObserver(MultipleObserver o);
    public void notifyObservers();
}