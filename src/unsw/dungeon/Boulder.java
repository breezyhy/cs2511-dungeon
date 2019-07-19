package unsw.dungeon;

import java.util.List;
import java.util.ArrayList;

public class Boulder extends EntitySemiblocking implements Subject {
    private Dungeon dungeon;
    private int prevX, prevY;

    ArrayList<Observer> listObservers = new ArrayList<Observer>();

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }

    public int getPrevX() {
        return this.prevX;
    }

    public int getPrevY() {
        return this.prevY;
    }

    private void setPrev(int x, int y){
        prevX = x;
        prevY = y;
    }

    @Override
    public boolean resolveCollision(EntityMoveable obj){
        // System.out.println("Resolving collision");
        if (!(obj instanceof Player)) return false;

        int deltaX = getX() - obj.getX();
        int deltaY = getY() - obj.getY();
        List<Entity> colliding = dungeon.getCollidingEntity(getX() + deltaX, getY() + deltaY);
        if (colliding == null) return true;
        boolean collide = true;
        // System.out.println("boulder cooord" + getX() + "," + getY() + "collides with:" + colliding.size());
        for (Entity f : colliding) {
            if (!f.resolveCollision(this)) collide = false;
        }

        if (collide) {
            setPrev(getX(), getY());
            x().set(getX() + deltaX);
            y().set(getY() + deltaY);
            notifyObservers();
        }

        return collide;
    }

    @Override
    public void registerObserver(Observer o) {
        if (!listObservers.contains(o)) listObservers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        listObservers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : listObservers) {
            obs.update(this);
        }
    }
   
}
