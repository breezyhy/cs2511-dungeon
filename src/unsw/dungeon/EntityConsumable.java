package unsw.dungeon;

public abstract class EntityConsumable extends Entity {

    private Backpack backpack = null;

    public EntityConsumable(int x, int y) {
        super(x, y);
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    // Consumed items will put itself inside the backpack, and then disappear from
    // the screen
    public void storeBackpack(Backpack backpack) {
        backpack.addConsumables(this);
        setBackpack(backpack);
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void used() {
        if (backpack == null)
            return;
        backpack.removeConsumables(this);
        this.setVisibility(false);
    }

    // Fix on clarity
    // <<<<<< disappear
    // >>>>>> setStorage
    // Setstorage is now used only for setting the backpack, used() will handle the disappearance of the entity from the screen
    public void setStorage(int x, int y) {
        // Implement a method to make this disappear
        x().set(x);
        y().set(y);
    }

    public boolean resolveCollision(EntityConsumable obj) {
        return false;
    }

    public boolean resolveCollision(EntityBlocking obj) {
        return false;
    }

    public boolean resolveCollision(EntitySemiblocking obj) {
        return false;
    }

    public boolean resolveCollision(EntityNonblocking obj) {
        return true;
    }

    public boolean resolveCollision(EntityMoveable obj) {
        return true;
    }

}