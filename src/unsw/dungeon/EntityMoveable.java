package unsw.dungeon;

public abstract class EntityMoveable extends Entity {

    private Dungeon dungeon = null;

    public EntityMoveable(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;    
    }

    public Dungeon dungeon(){
        return this.dungeon;
    }

    public void die(){
        // Placeholder of dead moveable
        x().set(dungeon.getWidth());
        y().set(dungeon.getHeight());
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
        return true;
    }

    public boolean resolveCollision(EntityMoveable obj){
        return false;
    }
}