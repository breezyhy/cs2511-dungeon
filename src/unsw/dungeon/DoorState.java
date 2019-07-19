package unsw.dungeon;

public interface DoorState {
    public boolean showState();
    public void trigger(Door s);
}