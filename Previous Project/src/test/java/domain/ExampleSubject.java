package domain;

public class ExampleSubject extends Subject {
    @Override
    public void addObserver(Observer element) {
        observers.add(element);
    }

    @Override
    public void removeObserver(Observer element) {
        observers.remove(element);
    }
}
