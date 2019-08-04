package unsw.dungeon;
/**
 * Implements the treasure class as per the spec. Is used by TreasureGoal as well
 * to keep track of end goals that a player has to achieve to complete the dungeon
 */
public class Treasure extends EntityConsumable implements MultipleSubject {

    private MultipleObserver treasureObserver = null;
    private boolean collected;

    public Treasure(int x, int y) {
        super(x, y);
        this.collected = false;
    }

    public boolean collected() {
        return this.collected;
    }
    /**
     * A method to resovle collision with other entities. Anything other than a player
     * will move over it, a player will collect it first
     * @return true for early returns
     */
    @Override
    public boolean resolveCollision(EntityMoveable obj) {
        if (!(obj instanceof Player))
            return true;

        Backpack backpack = ((Player) obj).getBackpack();
        if (backpack.getConsumable(this)) {
            this.collected = true;
            System.out.println("Item disappears");
            notifyObservers();
        }

        return true;
    }

    @Override
    public void registerObserver(MultipleObserver o) {
        treasureObserver = o;
        o.addSubject(this);
    }

    @Override
    public void removeObserver(MultipleObserver o) {
        if (treasureObserver == o) {
            treasureObserver.removeSubject(this);
            treasureObserver = null;
        }
    }

    @Override
    public void notifyObservers() {
        treasureObserver.update(this);
    }
}
