package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.Vicino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare ai branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField distanzaMinima;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer distanzaMinima;
    	try {
    		distanzaMinima = Integer.parseInt(this.distanzaMinima.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Errore! Devi inserire un valore numerico intero nella casella di testo 'Distanza minima'!");
    		return;
    	}
    	
    	this.model.creaGrafo(distanzaMinima);
    	this.txtResult.appendText("Grafo creato!\n");
    	this.txtResult.appendText("# VERTICI: " + this.model.nVertici() + "\n");
    	this.txtResult.appendText("# ARCHI: " + this.model.nArchi() + "\n\n");    	

    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(this.cmbBoxAeroportoPartenza.getValue() == null) {
    		this.txtResult.appendText("Errore! Devi selezionare un aeroporto dall'apposito menu a tendina!");
    		return;
    	}
    	
    	Airport aeroporto = this.cmbBoxAeroportoPartenza.getValue();
    	
    	this.txtResult.appendText("Elenco degli aeroporti adiacenti all'aeroporto" + aeroporto + " (in ordine decrescente di distanza):\n");
    	for(Vicino vicino : this.model.getAdiacentiAeroporto(aeroporto)) {
    		this.txtResult.appendText(vicino + "\n");
    	}

    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Double migliaDisponibili;
    	try {
    		migliaDisponibili = Double.parseDouble(this.numeroVoliTxtInput.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Errore! Devi inserire un valore numerico decimale nella casella di testo 'Miglia disponibili'!");
    		return;
    	}
    	
    	List<Airport> best = this.model.cercaItinerario(migliaDisponibili);
		this.txtResult.appendText("L'itinerario è formato dai seguenti aeroporti:\n");
		for(Airport a : best) {
			this.txtResult.appendText(a + "\n");
		}
		
		this.txtResult.appendText("\nLa distanza totale percorsa è di " + this.model.getTotDistanza() + " miglia");

    	

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getAirports());
	}
}
