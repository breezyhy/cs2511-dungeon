package unsw.dungeon;
/**
 * An observer class that is used to implement the Observer pattern across various entities and classes
 * @author z5161251
 *
 */
public interface Observer {
	/**
	 * Update a subject of this Observer
	 * @param obj Subject
	 */
    public void update(Subject obj);
}