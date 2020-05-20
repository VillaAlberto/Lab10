package it.polito.tdp.bar.model;

public class TestSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulator simulator= new Simulator();
		
		for  (int i=0; i<10; i++)
		{
	simulator.run();
System.out.println(simulator.getClienti());
System.out.println(simulator.getSoddisfatti());
System.out.println(simulator.getInsoddisfatti());
		}
		
	}

}
