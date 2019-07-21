package unsw.dungeon;

public class Key extends EntityConsumable {

    public Key(int x, int y) {
        super(x, y);
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

    public void useKey() {
        if (getBackpack() == null)
            return;
        disappear(getBackpack().getStoringColumn() + 1, 0);
        used();
    }
}
