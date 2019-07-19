package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;


public class Enemy extends EntityMoveable {

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
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
            if (!f.resolveCollision(this)) return false;
        }
        return true;
    }

    // You can't be squashed by an entity
    public boolean resolveCollision(EntitySemiblocking e) {
        return false;
    }
    
    public boolean resolveCollision(EntityBlocking e) {
        return false;
    }

    public boolean resolveCollision(EntityMoveable e) {
        if (e instanceof Enemy) return true;
        if (!(e instanceof Player)) return true;
        
        // If player is going to die, player will die and return false
        Player p = (Player) e;
        if (!p.isImmune(this)) {
            return false;
        }

        // Else, this entity will die
        die();
        return true;
    }
}
