package unsw.dungeon;

public interface MultipleObserver {
    public void update(MultipleSubject obj);

    public void addSubject(MultipleSubject obj);

    public void removeSubject(MultipleSubject obj);
}