package unsw.dungeon;

public class Key extends EntityConsumable {

    private int id;

    public Key(int x, int y, int id) {
        super(x, y);
        this.id = id;
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

    public boolean useKey(Door d) {
        if (getBackpack() == null)
            return false;
        if (d.getId() == this.id) {
            disappear(getBackpack().getStoringColumn() + 1, 0);
            used();
            d.switchState();
            return true;
        }
        return false;
    }
}
