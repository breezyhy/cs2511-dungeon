package unsw.dungeon;

public class Sword extends EntityConsumable {

    private int useCount;

    public Sword(int x, int y) {
        super(x, y);
        this.useCount = 5;
    }

    @Override
    public boolean resolveCollision(EntityMoveable obj) {
        if (!(obj instanceof Player))
            return true;

        Backpack backpack = ((Player) obj).getBackpack();
        if (backpack.getConsumable(this)) {
            System.out.println("Item disappears");
        }

        return true;
    }

    public boolean useSword() {
        if (this.useCount > 1) {
            this.useCount--;
            return true;
        } else if (this.useCount == 1) {
        	// setStorage(getBackpack().getStoringColumn() + 1, 0);
        	used();
            this.useCount--;
            return true;
        }

        return false;
    }

}
