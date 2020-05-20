package it.polito.tdp.bar.model;

import java.time.LocalTime;

public class Event implements Comparable<Event> {

	public enum EventType{
		NEW_CLIENT, CLIENT_SERVED
	}
	
	private LocalTime time;
	private EventType type;
	private int dimensioneTavolorichiesta;
	private int dimensioneTavolomassima;
	
	
	
	public Event(LocalTime time, EventType type, int dimensioneTavolorichiesta) {
		this.time = time;
		this.type = type;
		this.dimensioneTavolorichiesta = dimensioneTavolorichiesta;
		this.dimensioneTavolomassima=(int) (dimensioneTavolorichiesta*(2));
	}

	
	
	public LocalTime getTime() {
		return time;
	}




	public EventType getType() {
		return type;
	}




	public int getDimensioneTavolorichiesta() {
		return dimensioneTavolorichiesta;
	}




	public int getDimensioneTavolomassima() {
		return dimensioneTavolomassima;
	}




	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	

}
