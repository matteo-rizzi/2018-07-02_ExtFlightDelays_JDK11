package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private Map<Integer, Airport> idMap;
	private ExtFlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private List<Airport> best;
	private Double totDistanza;

	public Model() {
		this.dao = new ExtFlightDelaysDAO();
		this.idMap = new HashMap<>();
		this.dao.loadAllAirports(idMap);
	}

	public List<Airport> getAirports() {
		List<Airport> aeroporti = new ArrayList<>(idMap.values());
		return aeroporti;
	}

	public void creaGrafo(int distanzaMinima) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, idMap.values());

		// aggiungo gli archi
		for (Adiacenza a : this.dao.getAdiacenze(idMap)) {
			DefaultWeightedEdge e = this.grafo.getEdge(a.getPrimo(), a.getSecondo());
			if (!this.grafo.containsEdge(e)) {
				Graphs.addEdge(this.grafo, a.getPrimo(), a.getSecondo(), a.getPeso());
			} else {
				this.grafo.setEdgeWeight(e, (this.grafo.getEdgeWeight(e) + a.getPeso()) / 2);
			}
		}

		List<DefaultWeightedEdge> daRimuovere = new ArrayList<>();
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(e) <= distanzaMinima)
				daRimuovere.add(e);
		}
		this.grafo.removeAllEdges(daRimuovere);

	}

	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Vicino> getAdiacentiAeroporto(Airport aeroporto) {
		List<Vicino> vicini = new ArrayList<>();
		for (Airport vicino : Graphs.neighborListOf(this.grafo, aeroporto)) {
			DefaultWeightedEdge e = this.grafo.getEdge(aeroporto, vicino);
			Vicino v = new Vicino(vicino, this.grafo.getEdgeWeight(e));
			vicini.add(v);
		}

		Collections.sort(vicini);
		return vicini;
	}

	public List<Airport> cercaItinerario(double migliaDisponibili) {
		this.best = new ArrayList<>();
		List<Airport> parziale = new ArrayList<>();
		this.totDistanza = 0.0;

		for (Airport partenza : this.grafo.vertexSet()) {
			parziale.add(partenza);
			this.cerca(parziale, 0, migliaDisponibili, migliaDisponibili);
			parziale.remove(partenza);
		}

		return best;
	}

	private void cerca(List<Airport> parziale, int livello, double migliaDisponibili, double migliaRimanenti) {
		System.out.println(parziale);
		// caso terminale
		if (parziale.size() > best.size()) {
			this.best = new ArrayList<>(parziale);
			this.totDistanza = migliaDisponibili - migliaRimanenti;
		}

		// caso intermedio
		Airport ultimo = parziale.get(livello);
		for (Airport vicino : Graphs.neighborListOf(this.grafo, ultimo)) {
			DefaultWeightedEdge e = this.grafo.getEdge(ultimo, vicino);
			migliaRimanenti = migliaRimanenti - this.grafo.getEdgeWeight(e);
			if (!parziale.contains(vicino) && migliaRimanenti > 0) {
				parziale.add(vicino);
				this.cerca(parziale, livello + 1, migliaDisponibili, migliaRimanenti);
				parziale.remove(vicino);
			}
		}
	}

	public Double getTotDistanza() {
		return totDistanza;
	}

}
