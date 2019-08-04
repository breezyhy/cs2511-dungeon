package unsw.dungeon;
/**
 * Interface used for the observer pattern implemented across various classes
 */
public interface Subject {
	/**
	 * Register an observer to this subject, so that it receives updates
	 * @param Observer o
	 */
    public void registerObserver(Observer o);

    /**
     * Remove an observer from this subject, so it no longer receives updatse
     * @param Observer o
     */
    public void removeObserver(Observer o);

    /**
     * Notify all observers of subject
     */
    public void notifyObservers();
}