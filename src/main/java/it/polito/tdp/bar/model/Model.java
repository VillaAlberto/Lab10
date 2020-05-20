package it.polito.tdp.bar.model;

public class Model {
	private Simulator sim;
public Model()
{
sim= new Simulator();	
}

public int getSoddisfatti() {
	return sim.getSoddisfatti();
}
	
public int getInsoddisfatti() {
	return sim.getInsoddisfatti();
}

public int getTotali() {
	return sim.getClienti();
}

public void simula() {
	sim.run();
	
}

}
