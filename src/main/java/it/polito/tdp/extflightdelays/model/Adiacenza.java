package it.polito.tdp.extflightdelays.model;

public class Adiacenza {

	private Airport primo;
	private Airport secondo;
	private Double peso;

	public Adiacenza(Airport primo, Airport secondo, Double peso) {
		super();
		this.primo = primo;
		this.secondo = secondo;
		this.peso = peso;
	}

	public Airport getPrimo() {
		return primo;
	}

	public void setPrimo(Airport primo) {
		this.primo = primo;
	}

	public Airport getSecondo() {
		return secondo;
	}

	public void setSecondo(Airport secondo) {
		this.secondo = secondo;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

}
