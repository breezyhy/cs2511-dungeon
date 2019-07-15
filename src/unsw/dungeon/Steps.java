package unsw.dungeon;

public class Steps {
    // Steps stores the amount of keystroke done
    private int count;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Steps() {
        this.count = 0;
    }

    public void move() {
        this.count++;
    }

    // Implement observer pattern here
    // Items such as invincibility or lit bomb will observe this class as reference
    // Enemies also observe this class to determine whether they should move towards the player
    

}