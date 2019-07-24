package unsw.dungeon;

public class Door extends EntitySemiblocking {

    private DoorState accessible;
    private int id;

    public Door(int x, int y, int id) {
        super(x, y);
        this.accessible = new Door_Locked();
        this.id = id;
    }

    public void switchState() {
        accessible.trigger(this);
    }

    public int getId(){
        return this.id;
    }

    public void setState(DoorState accessible) {
        this.accessible = accessible;
    }

    public boolean accessible() {
        return accessible.showState();
    }

    @Override
    public boolean resolveCollision(EntityMoveable obj) {
        if (!(obj instanceof Player))
            return false;
        if (accessible())
            return true;
        Player player = (Player) obj;
        if (player.resolveCollision(this)) {
            // switchState();
            return true;
        }
        return false;
    }
}
