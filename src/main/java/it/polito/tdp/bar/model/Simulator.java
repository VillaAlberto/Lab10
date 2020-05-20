package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	public Simulator() {};
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> coda;
		
	// PARAMETRI DI SIMULAZIONE
	
	private Map<Integer, Integer> mappaTavoli= new TreeMap<Integer, Integer>();
	
	private int totEventi=2000;
	
	private int intervalloMin=1;
	private int intervalloMax=10;
	
	private int personeMin=1;
	private int personeMax=10;
	
	private int durataMin=60;
	private int durataMax=120;
	
	private double tolleranzaMin=0.0;
	private double tolleranzaMax=0.9;
		
	// MODELLO DEL MONDO
	
	//Aggiorno la mappa
		
	// VALORI DA CALCOLARE
	
	private int clienti;
	private int soddisfatti;
	private int insoddisfatti;
		
	// METODI PER IMPOSTARE I PARAMETRI
	//Non abbiamo parametri da impostare dall'interfaccia
	//METODI PER RESTITUIRE I RISULTATI
	
	public int getClienti() {
		return clienti;
	}

	public int getSoddisfatti() {
		return soddisfatti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	//SIMULAZIONE VERA E PROPRIA
	public void run() {
		
			mappaTavoli.put(4, 5);
			mappaTavoli.put(6, 4);
			mappaTavoli.put(8, 4);
			mappaTavoli.put(10, 2);
		
		this.clienti=0;
		this.insoddisfatti=0;
		this.soddisfatti=0;
		
		coda= new PriorityQueue<Event>();
		
		LocalTime ora= LocalTime.now();
		Event e = new Event(ora, EventType.NEW_CLIENT, getPersone());
		this.coda.add(e);
		
		do {processEvent(coda.poll());}
		while(clienti!=totEventi);
			
		
	}
	
	private void processEvent(Event poll) {
		switch (poll.getType()) {
		
		case NEW_CLIENT:
			clienti++;
			//Preparo il cliente che arriverà successivamente
			LocalTime arrivo= poll.getTime().plus(getIntervallo());
			Event nuovoo= new Event(arrivo, EventType.NEW_CLIENT, getPersone());
			coda.add(nuovoo);
			//Decido se posso assegnare il tavolo, RITORNO -1 se NON posso assegnre il tavolo
			
			int tavoloOccupato=tavoloLibero(poll.getDimensioneTavolorichiesta(), poll.getDimensioneTavolomassima());
			if (tavoloOccupato>0) {
				soddisfatti++;
				
				//Creo l'evento di quando abbandona il tavolo
				LocalTime fine= poll.getTime().plus(getDurata());
				Event nuovo= new Event(fine, EventType.CLIENT_SERVED,tavoloOccupato);
				coda.add(nuovo);
			}
			else {//Se non posso assegnarlo vedo se il cliente è disposto a stare al bancone
				if (banconeOkk())
					soddisfatti++;
				else insoddisfatti++;
			}
			break;

		case CLIENT_SERVED:
			//Devo solamente liberare il tavolo
			int dimensioneTavolo= poll.getDimensioneTavolorichiesta();
			int tavoliAttualmenteLiberi= mappaTavoli.get(dimensioneTavolo)+1;
			mappaTavoli.remove(dimensioneTavolo);
			mappaTavoli.put(dimensioneTavolo, tavoliAttualmenteLiberi);
			break;
		}
		
	}

	private boolean banconeOkk() {
		if (Math.random()<=getTolleranza())
			return true;
		return false;
	}

	private int tavoloLibero(int dimensioneRichiesta, int dimensioneMassima) {
		for (int i=1; i<=dimensioneMassima; i++)
		{
			if (mappaTavoli.get(i)!=null&&mappaTavoli.get(i)>0)
			{
				int tavoliLiberi=mappaTavoli.get(i)-1;
				mappaTavoli.remove(i);
				mappaTavoli.put(i, tavoliLiberi);
				return i;
			}
		}
		
		return -1;
	}

	public Duration getIntervallo() {
		double num =(intervalloMax-intervalloMin+1)*Math.random();
		Duration durata= Duration.of((int)num+intervalloMin, ChronoUnit.MINUTES);
		return durata;
		
	}
	
	public int getPersone() {
		double num=(personeMax-personeMin+1)*Math.random();
		return (int) num+personeMin;
	}
	
	public Duration getDurata() {
		double num =(durataMax-durataMin+1)*Math.random();
		Duration durata= Duration.of((int)num+durataMin, ChronoUnit.MINUTES);
		return durata;
		
	}
	
	public double getTolleranza() {
		double i=1.00;
		while ((i>tolleranzaMax)||(i<tolleranzaMin))
		{
			i=Math.random();
		}
		return i;
	}
}
