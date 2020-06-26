package it.polito.tdp.extflightdelays.model;

public class Vicino implements Comparable<Vicino>{

	private Airport aeroporto;
	private Double peso;

	public Vicino(Airport aeroporto, Double peso) {
		super();
		this.aeroporto = aeroporto;
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Aeroporto: " + this.aeroporto + " - distanza: " + this.peso;
	}

	@Override
	public int compareTo(Vicino other) {
		return other.peso.compareTo(this.peso);
	}

}
