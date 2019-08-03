package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

public class EnemiesGoal extends DungeonGoals implements MultipleObserver {
    private String name;
    private boolean achieved;
    private ArrayList<MultipleSubject> subjects;
    private SimpleIntegerProperty progress;
    private SimpleIntegerProperty total;
    
    public EnemiesGoal(String name) {
        // name should be either "AND" or "OR" for MultipleGoals
        this.name = name;
        this.achieved = false;
        this.progress = new SimpleIntegerProperty(0);
        this.subjects = new ArrayList<MultipleSubject>();
        this.total = new SimpleIntegerProperty(subjects.size());
    }
    public SimpleIntegerProperty getProgress() {
    	return this.progress;
    }
    
    public SimpleIntegerProperty getTotal() {
    	return this.total;
    }
    
    public void setProgress(int p) {    	
    	this.progress.set(p);
    }
    
    public void add(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }

    public void remove(DungeonGoals goal) {
        throw new UnsupportedOperationException();
    }

    public boolean achieved() {
        return achieved;
    }

    @Override
    public void update(MultipleSubject obj) {
        boolean done = true;
        int count = 0;
        for (MultipleSubject enemy : subjects) {
            if (((Enemy) enemy).alive())
                done = false;
            else {
            	count++;
            }
        }
        setProgress(count);
        achieved = done;
        if (achieved)
            System.out.println("EnemiesGoal has been achieved");
    }

    @Override
    public void addSubject(MultipleSubject obj) {
        subjects.add(obj);
        System.out.println("Object added: " + obj.getClass());
        this.total.set(subjects.size());
    }

    @Override
    public void removeSubject(MultipleSubject obj) {
        if (subjects.contains(obj))
            subjects.remove(obj);
        this.total.set(subjects.size());

    }

}