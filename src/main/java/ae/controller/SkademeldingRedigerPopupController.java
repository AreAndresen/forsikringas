package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Kunde;
import ae.model.Skademelding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;

public class SkademeldingRedigerPopupController {

    @FXML
    private TextField forsikringsNrField, skadeNrField, skadebeskrivelseField,
            belopTakseringField, erstatningsbelopUtbetaltField, datoSkademeldingOpprettetField;
    @FXML
    private ChoiceBox statusField, skadeTypeField;
    @FXML
    private TextArea vitneInfoField;

    private ObservableList<String> statusListe = FXCollections.observableArrayList("Betalt", "Ubetalt");
    private ObservableList<String> skadeTypeListe = FXCollections.observableArrayList("Båtforsikring",
            "Hus- og innboForsikring", "Fritidsboligforsikring", "Reiseforsikring");

    private Stage popupStage;
    private Skademelding skademeldingÅRedigere;
    private boolean bekreft = false;
    private boolean inputOK = false;

    // Referanse til Rot-kontrolleren.
    private HovedApplikasjon hovedApplikasjon;


    @FXML
    private void initialize() {
        statusField.setValue("Betalt");
        statusField.setItems(statusListe);

        skadeTypeField.setValue("Båtforsikring");
        skadeTypeField.setItems(skadeTypeListe);

    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    /**
     * Plasserer skademeldingen som skal redigeres i popup-vinduet.
     */
    public void setSkademeldingÅRedigere(Skademelding skademelding) {
        this.skademeldingÅRedigere = skademelding;

        oppdaterFelter();
    }

    /**
     * Metode for å legge inn kundens data i TextFields.
     */
    public void oppdaterFelter() {
        forsikringsNrField.setText(Integer.toString(skademeldingÅRedigere.getForsikringsNr()));
        skadeNrField.setText(Integer.toString(skademeldingÅRedigere.getSkadeNr()));
        //skadeTypeField.setText(skademeldingÅRedigere.getSkadeType());
        skadebeskrivelseField.setText(skademeldingÅRedigere.getSkadeBeskrivelse());
        belopTakseringField.setText(Double.toString(skademeldingÅRedigere.getBelopTaksering()));
        erstatningsbelopUtbetaltField.setText(Double.toString(skademeldingÅRedigere.getErstatningsbelopUtbetalt()));
        datoSkademeldingOpprettetField.setText(skademeldingÅRedigere.getDatoSkade().toString());
        vitneInfoField.setText(skademeldingÅRedigere.getKontaktinfoVitner());
        //statusField.setValue(skademeldingÅRedigere.get);

        //forsikringsNrField.setDisable(true);
        skadeNrField.setDisable(true);
        datoSkademeldingOpprettetField.setDisable(true);
    }

    /**
     * Returnerer true dersom bruker trykker på Bekreft, hvis ikke
     * så false
     */
    public boolean erBekreftTrykket() {
        return bekreft;
    }

    /**
     * Kalles når bruker trykker Bekreft.
     */
    @FXML
    public void bekreftTrykkes() {
        // TODO: input-validering med exceptions venter
        oppdaterSkademelding();
        bekreft = true;

        if(inputOK){ //implementert en boolean for å lukke om input er riktig/feil
            popupStage.close();
        }
    }

    public void oppdaterSkademelding() {
        String msg = "";

        skademeldingÅRedigere.setSkadeNr(Integer.parseInt(skadeNrField.getText()));

        //Bytter set her ut med metoder (se under)

        msg += redigerForsikrnignsNr();
        msg += redigerSkadetype();
        msg += redigerSkadebeskrivelse();
        msg += redigerTakseringsbeløp();
        msg += redigerErstatningsbelopUtbetalt();
        msg += redigerKontaktinfoVitner();
        msg += redigerStatus();

        //kundeÅRedigere.setDatoKundeOpprettet(LocalDate.datoKundeOpprettetField.getText());
        // TODO: må parse LocalDate så riktig format lagres

        //kontrollerer etter aktiverte feilmeldinger
        if(msg.length() != 0){
            UgyldigInputHandler.generateAlert(msg); //alert
        }
        else{
            inputOK = true; //riktig input
        }
    }

    //TODO: METODER FOR ENDRING AV SKADEMELDING
    //oppdaterer skadetype
    private String redigerForsikrnignsNr() {
        String msg = "";

        skademeldingÅRedigere.setForsikringsNr(Integer.parseInt(forsikringsNrField.getText()));

        return msg;
    }

    //oppdaterer skadetype
    private String redigerSkadetype() {
        String msg = "";

            skademeldingÅRedigere.setSkadeType(skadeTypeField.getValue().toString());

        return msg;
    }

    //oppdaterer skadebeskrivelse
    private String redigerSkadebeskrivelse() {
        String msg = "";
        skademeldingÅRedigere.setSkadeBeskrivelse(skadebeskrivelseField.getText());

        return msg;
    }

    //oppdaterer Takseringsbeløp
    private String redigerTakseringsbeløp() {
        String msg = "";

            skademeldingÅRedigere.setBelopTaksering(Double.parseDouble(belopTakseringField.getText()));

        return msg;
    }

    //oppdaterer erstatningsbelop Utbetalt
    private String redigerErstatningsbelopUtbetalt() {
        String msg = "";

            skademeldingÅRedigere.setErstatningsbelopUtbetalt(Double.parseDouble(erstatningsbelopUtbetaltField.getText()));

        return msg;
    }

    //oppdaterer skadebeskrivelse
    private String redigerKontaktinfoVitner() {
        String msg = "";
        skademeldingÅRedigere.setKontaktinfoVitner(vitneInfoField.getText());

        return msg;
    }

    //oppdaterer status
    private String redigerStatus() {
        String msg = "";
        skademeldingÅRedigere.setStatus(statusField.getValue().toString());

        return msg;
    }




    @FXML
    public void avbrytTrykkes() {
        popupStage.close();
    }
}
