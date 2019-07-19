package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends EntityMoveable {

    private Backpack backpack;
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.backpack = new Backpack(dungeon.getWidth());
    }

    public void moveUp() {
        if (getY() <= 0) return;
        if (collide(getX(), getY() - 1)) 
            y().set(getY() - 1);
    }

    public void moveDown() {
        if (getY() >= dungeon().getHeight() - 1) return;
        if (collide(getX(), getY() + 1))
            y().set(getY() + 1);
    }

    public void moveLeft() {
        if (getX() <= 0) return;
        if (collide(getX() - 1, getY()))
            x().set(getX() - 1);
    }

    public void moveRight() {
        if (getX() >= dungeon().getWidth() - 1) return;
        if (collide(getX() + 1, getY()))
            x().set(getX() + 1);
    }

    public boolean collide(int x, int y){
        List<Entity> colliding = dungeon().getCollidingEntity(x, y);
        if (colliding == null) return true;
        boolean collide = true;
        for (Entity f : colliding) {
            // System.out.println("Colliding with " + f.getClass() + " . Type: " + this.getClass());
            if (!f.resolveCollision(this)) return false;

        }
        return true;
    }
    
    public Backpack getBackpack(){
        return backpack;
    }

    // If player is immune, the calling entity (enemy) will disappear
    // Player is immune if the player has the required consumable (returns true)
    // Else, player will die
    public boolean isImmune(Enemy e){
        if (backpack.useConsumable(e)) {
            return true;
        }
        die();
        return false;
    }

    public boolean resolveCollision(Door d){
        return backpack.useConsumable(d);
    }
    
    // If the calling function is a lit bomb, it won't do anything apart from either killing the player or leaving the player alive
    public void isImmune(Bomb_Lit b){

    }

}
