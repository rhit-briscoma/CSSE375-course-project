package domain;

import java.util.List;

abstract class Subject {
    protected List<Observer> observers;
    public abstract void addObserver(Observer element);
    public abstract void removeObserver(Observer element);
}
