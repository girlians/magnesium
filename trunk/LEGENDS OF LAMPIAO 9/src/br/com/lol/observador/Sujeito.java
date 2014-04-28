package br.com.lol.observador;

import java.util.List;

public class Sujeito {
	
	private List<Observer> observers;

	public void notifyObserver(){
		
	}
	
	public void addObserver(Observer obs){
		observers.add(obs);
	}
	
	public void removeObserver(Observer obs){
		for(int i = 0; i < observers.size(); i++){
			if(observers.get(i) == obs){
				observers.remove(i);
			}
		}
	}
	
	public void setChanges(){
		
	}
	
}
