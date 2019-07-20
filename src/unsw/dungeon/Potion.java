package unsw.dungeon;

public class Potion extends EntityConsumable implements GameTickSubscriber {

    private int useCount;
    // Backpack is stored here because the potion needs to be detached from backpack once it expires
    public Potion(int x, int y) {
        super(x, y);
        this.useCount = 10;
    }

    // resolveCollision is done in each class since getConsumable has its own classtype
    @Override
    public boolean resolveCollision(EntityMoveable obj){
        if (!(obj instanceof Player)) return true;

        Backpack backpack = ((Player) obj).getBackpack();
        if (backpack.getConsumable(this)) {
            System.out.println("Item disappears");
        }

        return true;
    }

    public void decrementTime(){
        // Do not decrement if there is no backpack
        if (getBackpack() == null) return;
        this.useCount--;
        if (useCount < 0) {
            used(getBackpack());
        }
    }

    public boolean stillActive(){
        return (useCount > 0);
    }

    @Override
    public void trigger() {
        decrementTime();
    }

}
