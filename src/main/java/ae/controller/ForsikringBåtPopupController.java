package ae.controller;

import ae.HovedApplikasjon;
import ae.controller.util.UgyldigInputHandler;
import ae.model.Båtforsikring;
import ae.model.Kunde;
import ae.model.exceptions.forsikring.UgyldigDatoException;
import ae.model.exceptions.forsikring.UgyldigForsikringsnrException;
import ae.model.exceptions.skademelding.UgyldigKundenrException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Popup-vindu for å opprette og redigere båtforsikring.
 */

public class ForsikringBåtPopupController {

    // textfields
    @FXML
    private TextField kundeNrField, forsikringsNrField, datoOpprettetField, forsikringsbelopField, betingelserField,
            typeField, regnrField, båttypeField, lengdeFotField, årsmodellField, motortypeField;

    private Stage popupStage;
    private Båtforsikring båtforsikringÅRedigere;
    private boolean bekreft = false;
    private boolean inputOK = false;
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private void initialize() {}

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setBåtforsikringÅRedigere(Båtforsikring båtforsikring) {
        this.båtforsikringÅRedigere = båtforsikring;
        oppdaterFelter();
    }

    private void oppdaterFelter() {
        kundeNrField.setText(Integer.toString(båtforsikringÅRedigere.getKundeNr()));
        forsikringsNrField.setText(Integer.toString(båtforsikringÅRedigere.getForsikringsNr()));
        datoOpprettetField.setText(båtforsikringÅRedigere.getDatoOpprettet().toString());
        forsikringsbelopField.setText(Integer.toString(båtforsikringÅRedigere.getForsikringsBelop()));
        betingelserField.setText(båtforsikringÅRedigere.getBetingelser());
        typeField.setText(båtforsikringÅRedigere.getType());
        regnrField.setText(båtforsikringÅRedigere.getRegistreringsNr());
        båttypeField.setText(båtforsikringÅRedigere.getTypeModell());
        lengdeFotField.setText(Integer.toString(båtforsikringÅRedigere.getLengdeFot()));
        årsmodellField.setText(Integer.toString(båtforsikringÅRedigere.getÅrsmodell()));
        motortypeField.setText(båtforsikringÅRedigere.getMotorEgenskaper());

        typeField.setPromptText("Båtforsikring");
        forsikringsNrField.setDisable(true);
        datoOpprettetField.setDisable(true);
        typeField.setDisable(true);
    }

    public boolean erBekreftTrykket() {
        return bekreft;
    }

    @FXML
    public void bekreftTrykkes() {
        sjekkBåtforsikring();

        if (inputOK) {
            bekreft = true;
            popupStage.close();
        }
    }

    private void sjekkBåtforsikring() {
        String msg = "";

        msg += sjekkKundeNr();
        msg += sjekkForsikringsNr();
        msg += sjekkDatoOpprettet();

        if (msg.length() != 0) {
            UgyldigInputHandler.generateAlert(msg);
        } else {
            inputOK = true;
        }
    }

    private String sjekkKundeNr() {
        String msg = "";

        if (kundeNrField.getText() == null || kundeNrField.getText().isEmpty()) {
            msg += "Kundenummer kan ikke være tomt.\n";
        } else {
            try {
                boolean kundeFinnes = false;
                for (Kunde kunde : hovedApplikasjon.getKundeData()) {
                    if (kunde.getKundeNr() == Integer.parseInt(kundeNrField.getText())) {
                        kundeFinnes = true;
                    }
                }
                if (!kundeFinnes) {
                    msg += "Det er ingen kunde registrert med det\nkundenummeret i systemet.\n";
                } else {
                    båtforsikringÅRedigere.setKundeNr(Integer.parseInt(kundeNrField.getText()));
                }
            } catch (NumberFormatException e) {
                msg += "Kundenummer må være tall.\n";
            } catch (UgyldigKundenrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    private String sjekkForsikringsNr() {
        String msg = "";

        if (forsikringsNrField.getText() == null || kundeNrField.getText().isEmpty()) {
            msg += "Forsikringsnummer kan ikke være tomt.";
        } else {
            try {
                båtforsikringÅRedigere.setForsikringsNr(Integer.parseInt(forsikringsNrField.getText()));
            } catch (NumberFormatException e) {
                msg += "Forsikringsnummer nå være tall.\n";
            } catch (UgyldigForsikringsnrException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    private String sjekkDatoOpprettet() {
        String msg = "";

        if (datoOpprettetField.getText() == null || datoOpprettetField.getText().isEmpty()) {
            msg += "Dato kan ikke være tom.";
        } else {
            try {
                båtforsikringÅRedigere.setDatoOpprettet(LocalDate.parse(datoOpprettetField.getText()));
            } catch (UgyldigDatoException e) {
                msg += e.getMessage() + "\n";
            }
        }
        return msg;
    }

    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    @FXML
    public void avbrytTrykkes() {
        popupStage.close();
    }
}
