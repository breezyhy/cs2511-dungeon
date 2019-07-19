package unsw.dungeon;

public abstract class EntityBlocking extends Entity {

    public EntityBlocking(int x, int y) {
        super(x, y);
        // TODO Auto-generated constructor stub
    }

    public boolean resolveCollision(EntityConsumable obj){
        return false;
    }

    public boolean resolveCollision(EntityBlocking obj) {
        return false;
    }

    public boolean resolveCollision(EntitySemiblocking obj){
        return false;
    }

    public boolean resolveCollision(EntityNonblocking obj){
        return false;
    }

    public boolean resolveCollision(EntityMoveable obj){
        return false;
    }

}