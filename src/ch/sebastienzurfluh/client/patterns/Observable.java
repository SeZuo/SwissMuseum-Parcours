package ch.sebastienzurfluh.client.patterns;

import java.util.LinkedList;

public class Observable {
	public LinkedList<Observer> observerList = new LinkedList<Observer>();
	
	public void subscribeObserver(Observer observer) {
		observerList.add(observer);
	}
    
    public void notifyObservers() {
    	for (Observer observer : observerList) {
			observer.notifyObserver(this);
		}
    }
}
