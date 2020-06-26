package it.polito.tdp.extflightdelays.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		m.creaGrafo(900);
		List<Airport> best = m.cercaItinerario(10000);
		
		System.out.println(best);
		System.out.println(best.size());
	}

}
