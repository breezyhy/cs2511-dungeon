package unsw.dungeon;

import java.util.ArrayList;

public class GameTick implements Observer {
    private Dungeon dungeon;
    private ArrayList<GameTickSubscriber> listener;
    private int moveCount;

    public GameTick(Dungeon dungeon) {
        this.moveCount = 0;
        attachTickListener(dungeon);
    }

    private void attachTickListener(Dungeon dungeon) {
        this.listener = dungeon.getTickListener();
    }

    @Override
    public void update(Subject obj) {
        this.moveCount++;
        for (GameTickSubscriber sub : listener) {
            sub.trigger();
        }
    }

}