package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends EntityMoveable implements MultipleSubject, GameTickSubscriber {

    private MultipleObserver enemyObserver = null;
    private int moveCount;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.moveCount = 0;
    }

    public void moveUp() {
        if (!alive())
            return;
        if (getY() <= 0)
            return;
        if (collide(getX(), getY() - 1))
            y().set(getY() - 1);
    }

    public void moveDown() {
        if (!alive())
            return;
        if (getY() >= dungeon().getHeight() - 1)
            return;
        if (collide(getX(), getY() + 1))
            y().set(getY() + 1);
    }

    public void moveLeft() {
        if (!alive())
            return;
        if (getX() <= 0)
            return;
        if (collide(getX() - 1, getY()))
            x().set(getX() - 1);
    }

    public void moveRight() {
        if (!alive())
            return;
        if (getX() >= dungeon().getWidth() - 1)
            return;
        if (collide(getX() + 1, getY()))
            x().set(getX() + 1);
    }

    public boolean collide(int x, int y) {
        List<Entity> colliding = dungeon().getCollidingEntity(x, y);
        if (colliding == null)
            return true;
        boolean collide = true;
        for (Entity f : colliding) {
            if (!f.resolveCollision(this))
                return false;
        }
        return true;
    }

    // You can't be squashed by an entity
    public boolean resolveCollision(EntitySemiblocking e) {
        return false;
    }

    public boolean resolveCollision(EntityBlocking e) {
        if (e instanceof Bomb_Lit) {
            die();
            notifyObservers();
        }
        return false;
    }

    public boolean resolveCollision(EntityMoveable e) {
        if (e instanceof Enemy)
            return true;
        if (!(e instanceof Player))
            return true;

        // If player is going to die, player will die and return false
        Player p = (Player) e;
        if (!p.isImmune(this) && p.getHP().get() <= 1) {
        	System.out.println("1");
        	p.die();
            return false;
        }else if(!p.isImmune(this) && p.getHP().get() > 1) {
        	System.out.println("2");
        	p.loseHp();
        }else {
	        // Else, this entity will die
	        die();
	        notifyObservers();
        }
        return true;

    }

    @Override
    public void registerObserver(MultipleObserver o) {
        enemyObserver = o;
        o.addSubject(this);
    }

    @Override
    public void removeObserver(MultipleObserver o) {
        if (enemyObserver == o) {
            enemyObserver.removeSubject(this);
            enemyObserver = null;
        }
    }

    @Override
    public void notifyObservers() {
        if (enemyObserver != null)
            enemyObserver.update(this);
    }

    public void moveToPlayer() {
        Player player = dungeon().getPlayer();
        int deltaX = player.getX() - getX();
        int deltaY = player.getY() - getY();

        if (deltaX > 0)
            moveRight();
        if (deltaX < 0)
            moveLeft();
        if (deltaY > 0)
            moveDown();
        if (deltaY < 0)
            moveUp();
    }

    @Override
    public void trigger() {
        this.moveCount++;
        // Enemy moves to player every 2 keystrokes by player and able to move
        // diagonally
        if (this.moveCount % 2 == 0)
            moveToPlayer();
    }

}