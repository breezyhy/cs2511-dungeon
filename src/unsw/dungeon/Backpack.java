package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
/*
 * A class that stores the items (consumable) that a player is able to pick up
 */
public class Backpack {
    private List<EntityConsumable> itemList;
    private Dungeon dungeon;
    private int itemSize = 1;

    public Backpack(Dungeon dungeon) {
        this.itemList = new ArrayList<>();
        this.dungeon = dungeon;
    }
    
    public List<EntityConsumable> getConsumableList(){
    	return this.itemList;
    }
    
    public void addConsumables(EntityConsumable i) {
        itemList.add(i);
    }
    
    public void removeConsumables(EntityConsumable i) {
        if (itemList.contains(i)) {
            ((Entity) i).x().set(getStoringColumn() + 1);
            itemList.remove(i);
        }
    }

    public int getStoringColumn() {
        return this.dungeon.getWidth();
    }
    
    /**
     * This function gets called by player in EntityConsumable collision
     * Is repeated for each kind of consumable entity
     * @return false if it doesn't get put in backpack
     * @return true if able to be put in backpack, and is added to backpack 
     */
    public boolean getConsumable(Key k) {
        for (EntityConsumable e : itemList) {
            if (e instanceof Key) {
                return false;
            }
        }
        k.storeBackpack(this);
        k.setStorage(getStoringColumn(), itemSize);
        itemSize++;
        return true;
    }

    /**
     * This function gets called by player in EntityConsumable collision
     * Is repeated for each kind of consumable entity
     * @return false if it doesn't get put in backpack
     * @return true if able to be put in backpack, and is added to backpack 
     */
    public boolean getConsumable(Potion p) {
        for (EntityConsumable e : itemList) {
            if (e instanceof Potion) {
                return false;
            }
        }
        p.storeBackpack(this);
        p.setStorage(getStoringColumn(), itemSize);
        itemSize++;
        return true;
    }

    /**
     * This function gets called by player in EntityConsumable collision
     * Is repeated for each kind of consumable entity
     * @return false if it doesn't get put in backpack
     * @return true if able to be put in backpack, and is added to backpack 
     */
    public boolean getConsumable(Sword s) {
        for (EntityConsumable e : itemList) {
            if (e instanceof Sword) {
                return false;
            }
        }
        s.storeBackpack(this);
        s.setStorage(getStoringColumn(), itemSize);
        itemSize++;
        return true;
    }

    /**
     * This function gets called by player in EntityConsumable collision
     * Is repeated for each kind of consumable entity
     * @return false if it doesn't get put in backpack
     * @return true if able to be put in backpack, and is added to backpack 
     */
    public boolean getConsumable(Treasure t) {
        t.storeBackpack(this);
        t.setStorage(getStoringColumn(), itemSize);
        itemSize++;
        return true;
    }

    /**
     * This function gets called by player in EntityConsumable collision
     * Is repeated for each kind of consumable entity
     * @return false if it doesn't get put in backpack
     * @return true if able to be put in backpack, and is added to backpack 
     */
    public boolean getConsumable(Bomb_Unlit b) {
        for (EntityConsumable e : itemList) {
            if (e instanceof Bomb_Unlit) {
                return false;
            }
        }
        b.storeBackpack(this);
        b.setStorage(getStoringColumn(), itemSize);
        itemSize++;
        return true;
    }

    /**
     * If player has a key, uses the key on the door
     * @return true if key can be used on door and door unlocks
     * @return false if player has no key
     */
    public boolean useConsumable(Door d) {
        for (EntityConsumable e : itemList) {
            if (e instanceof Key) {
                // d.switchState();
                Key k = (Key) e;
                if (k.useKey(d)) return true;
                return false;
            }
        }

        return false;
    }

    /**
     * Using a consumable on an enemy follows an order of precedence
     * 1. Resolve enemy with current consumable and tickrate
     * 2. Resolve bomb damage with current consumable and tickrate
     * 3. Resolve the consumable storing method
     * @return true if player is immune/has required consumable
     */
    public boolean useConsumable(Enemy e) {
        for (EntityConsumable p : itemList) {
            if (p instanceof Potion) {
                if (((Potion) p).stillActive())
                    return true;
            }
        }

        for (EntityConsumable p : itemList) {
            if (p instanceof Sword) {
                if (((Sword) p).useSword())
                    return true;
            }
        }
        return false;
    }

    /**
     * Places a lit bomb in the dungeon.
     * @return 
     */
    public boolean useBomb() {
    	for (EntityConsumable b : itemList) {
    		if (b instanceof Bomb_Unlit) {
    			((Bomb_Unlit) b).useBomb();
    			return true;
    		}
    	}
    	
        return false;
    }

}