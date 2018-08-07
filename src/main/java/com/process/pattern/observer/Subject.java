package com.process.pattern.observer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ShenShuaihu on 2018/8/7.
 */


public class Subject {

    private List<Observer> observers  = new ArrayList<Observer>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    /**
     * 附上
     * @param observer
     */
    public void attach(Observer observer){
        observers.add(observer);
    }

    public void remove(Observer observer){
        Iterator<Observer> iterable = observers.iterator();
        while (iterable.hasNext()){
            Observer o = iterable.next();
            if (o.getClass().equals(observer.getClass())){
                    iterable.remove();
             }
        }

        observers.remove(observer);
    }


    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

