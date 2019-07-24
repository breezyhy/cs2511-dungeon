package unsw.dungeon;

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
