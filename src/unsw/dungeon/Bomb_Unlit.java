package unsw.dungeon;

/**
 * Implements the unlit bomb that a player is able to pick up from dungeon floor
 */
public class Bomb_Unlit extends EntityConsumable {
    public Bomb_Unlit(int x, int y) {
        super(x, y);
    }
    /**
     * If a player moves onto an unlit bomb, it is added to the players backpack
     */
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