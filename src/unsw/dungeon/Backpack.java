package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Backpack {
    private List<EntityConsumable> itemList;
    private int storingColumn;
    private int itemSize = 1;

    public Backpack(int col){
        this.itemList = new ArrayList<>();
        this.storingColumn = col;
    }

    public void addConsumables(EntityConsumable i){
        itemList.add(i);
    }

    public void removeConsumables(EntityConsumable i){
        if (itemList.contains(i)) {
            ((Entity) i).x().set(storingColumn + 1);
            itemList.remove(i);
        }
    }

    public int getStoringColumn(){
        return this.storingColumn;
    }

    // This function gets called by player in EntityConsumable collision
    // Returns false if it doesn't get backpacked (because of restrictions)
    // Returns true and the consumable will be backpacked by the backpack
    public boolean getConsumable (Key k) {
        for (EntityConsumable e : itemList){
            if (e instanceof Key) {
                return false;
            }
        }
        k.storeBackpack(this);
        k.disappear(storingColumn, itemSize);
        itemSize++;
        return true;
    }

    public boolean getConsumable (Potion p) {
        for (EntityConsumable e : itemList){
            if (e instanceof Potion) {
                return false;
            }
        }
        p.storeBackpack(this);
        p.disappear(storingColumn, itemSize);
        itemSize++;
        return true;
    }

    public boolean getConsumable (Sword s) {
        for (EntityConsumable e : itemList){
            if (e instanceof Sword) {
                return false;
            }
        }
        s.storeBackpack(this);
        s.disappear(storingColumn, itemSize);
        itemSize++;
        return true;
    }

    public boolean getConsumable (Treasure t){
        t.storeBackpack(this);
        t.disappear(storingColumn, itemSize);
        itemSize++;
        return true;
    }

    public boolean getConsumable (Bomb_Unlit b) {
        for (EntityConsumable e : itemList){
            if (e instanceof Bomb_Unlit) {
                return false;
            }
        }
        b.storeBackpack(this);
        b.disappear(storingColumn, itemSize);
        itemSize++;
        return true;
    }

    // If used, the item will be detached from itemlist, and returns true to any interface it's facing
    // Also possibly switching others state
    public boolean useConsumable(Door d) {
        for (EntityConsumable e : itemList){
            if (e instanceof Key) {
                d.switchState();
                ((Key)e).useKey();
                return true;
            }
        }

        return false;
    }

    public boolean useBomb() {
        return false;
    }


    // Order of precedence:
    // 1. Resolve enemy with current consumable and tickrate
    // 2. Resolve bomb damage with current consumable and tickrate
    // 3. Resolve the consumable storing method

    // True means that the player are immune/has the required consumable
    public boolean useConsumable(Enemy e) {
        for (EntityConsumable p : itemList){
            if (p instanceof Potion) {
                if (((Potion) p).stillActive()) return true;
            }
        }

        for (EntityConsumable p : itemList){
            if (p instanceof Sword) {
                if (((Sword) p).useSword()) return true;
            }
        }
        return false;
    }

    public boolean immunity() {
        return false;
    }
}