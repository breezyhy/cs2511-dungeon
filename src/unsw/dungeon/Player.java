package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends EntityMoveable implements Subject {

    private Backpack backpack;
    private ArrayList<Observer> playerObserver;

    /**
     * Create a player positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.backpack = new Backpack(dungeon);
        this.playerObserver = new ArrayList<>();
    }
    
    /**
     * Move player up one square, checking to make sure space is available and 
     * notifying all subscribers
     */
    public void moveUp() {
        if (!alive())
            return;
        if (getY() <= 0)
            return;
        if (collide(getX(), getY() - 1)) {
            y().set(getY() - 1);
        }
        notifyObservers();
    }
    /**
     * Move player diwn one square, checking to make sure space is available and 
     * notifying all subscribers
     */
    public void moveDown() {
        if (!alive())
            return;
        if (getY() >= dungeon().getHeight() - 1)
            return;
        if (collide(getX(), getY() + 1)) {
            y().set(getY() + 1);
        }
        notifyObservers();
    }
    /**
     * Move player left one square, checking to make sure space is available and 
     * notifying all subscribers
     */
    public void moveLeft() {
        if (!alive())
            return;
        if (getX() <= 0)
            return;
        if (collide(getX() - 1, getY())) {
            x().set(getX() - 1);
        }
        notifyObservers();
    }
    /**
     * Move player right one square, checking to make sure space is available and 
     * notifying all subscribers
     */
    public void moveRight() {
        if (!alive())
            return;
        if (getX() >= dungeon().getWidth() - 1)
            return;
        if (collide(getX() + 1, getY())) {
            x().set(getX() + 1);
        }
        notifyObservers();
    }

    /**
     * 
     * @param x the x position of the entity player collides with
     * @param y the y position of the entity player collides with
     * @return true if there is no collide action or if the entity is able to be 'collided' with
     * @return false if a player cannot move onto the square
     */
    public boolean collide(int x, int y) {
        List<Entity> colliding = dungeon().getCollidingEntity(x, y);
        if (colliding == null)
            return true;
        boolean collide = true;
        for (Entity f : colliding) {
            // System.out.println("Colliding with " + f.getClass() + " . Type: " +
            // this.getClass());
            if (!f.resolveCollision(this))
                return false;
        }
        return true;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    // If player is immune, the calling entity (enemy) will disappear
    // Player is immune if the player has the required consumable (returns true)
    // Else, player will die
    /**
     * Checks to see if player has any way of killing an enemy
     * @param e Enemy that player is colliding with 
     * @return true if player kills enemy
     * @return false if player is killed by enemy
     */
    public boolean isImmune(Enemy e) {
        if (backpack.useConsumable(e)) {
            return true;
        }
        die();
        return false;
    }
    
    /**
     * Called when player collides with a door, and key needs to be used
     * @param d Door that is being collided with
     * @return mimicks return of useConsumable called with door
     */
    public boolean resolveCollision(Door d) {
        return backpack.useConsumable(d);
    }

    // If the calling function is a lit bomb, it won't do anything apart from either
    // killing the player or leaving the player alive
    /**
     * Used to check a players collision with any blocking entity
     * A bomb will kill player, anything else is no interaction
     */
    public boolean resolveCollision(EntityBlocking e) {
        if (e instanceof Bomb_Lit){
            die();
            return true;
        }
        return false;
    }

    /**
     * Any moveable objects collision is handled by the entity
     */
    public boolean resolveCollision(EntityMoveable e) {
        if (e instanceof Enemy) {
            e.resolveCollision(this);
        }
        return true;
    }

    @Override
    public void registerObserver(Observer o) {
        playerObserver.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (playerObserver.contains(o))
            playerObserver.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : playerObserver) {
            o.update(this);
        }
    }

}
