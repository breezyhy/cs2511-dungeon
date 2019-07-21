package unsw.dungeon;

public class Bomb_Unlit extends EntityConsumable {
    public Bomb_Unlit(int x, int y) {
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

}