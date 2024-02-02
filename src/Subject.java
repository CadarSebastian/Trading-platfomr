import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(List<String> transactions) {
        for (Observer observer : observers) {
            observer.update(transactions);
        }
    }
}
