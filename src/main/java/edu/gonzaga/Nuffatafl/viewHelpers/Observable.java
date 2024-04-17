package edu.gonzaga.Nuffatafl.viewHelpers;

import java.util.ArrayList;

public class Observable<T> {
    public Observable(T value) {
        this.value = value;
        this.observers = new ArrayList<EventCallback<T>>();
    }
    
    private T value;
    private ArrayList<EventCallback<T>> observers;
    
    public T getValue() {
        return value;
    }
    
    public void set(T newValue) {
        value = newValue;
        notifyObservers();
    }
    
    public void onChange(EventCallback<T> observer) {
        observers.add(observer);
    }
    
    private void notifyObservers() {
        observers.forEach(observer -> observer.action(value));
    }
}
