package unsw.dungeon;
/**
 * Class that implements the Observer interface for an observer with multiple subjects
 * @author z5161251
 *
 */
public interface MultipleObserver {
    public void update(MultipleSubject obj);

    public void addSubject(MultipleSubject obj);

    public void removeSubject(MultipleSubject obj);
}