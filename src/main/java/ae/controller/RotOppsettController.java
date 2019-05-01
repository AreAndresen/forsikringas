package ae.controller;

import ae.HovedApplikasjon;
import ae.util.AlertHandler;
import ae.model.*;
import ae.model.exceptions.UgyldigKundeFormatException;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for RotOppsett. Rotoppsettet inneholder menylinjen
 * og plass hvor andre views skal plasserers.
 */
public class RotOppsettController {

    // Referanse til hovedapplikasjonen
    private HovedApplikasjon hovedApplikasjon;

    @FXML
    private MenuItem lagreFilMenuItem;

    public RotOppsettController() {
    }

    /**
     * Kalles fra hovedapplikasjonen for å gi en referanse til seg
     * selv.
     */
    public void setHovedApplikasjon(HovedApplikasjon hovedApplikasjon) {
        this.hovedApplikasjon = hovedApplikasjon;
    }

    // <------- KUNDE ------->
    //Går til kundeoversikt ved trykk i meny
    @FXML
    private void gåTilKundeoversikt() {
        Viewbehandling.visKundeOversikt(hovedApplikasjon);
        lagreFilMenuItem.setDisable(false);
    }


    // <------SKADEMELDING------>
    //Går til skademeldingversikt ved trykk i meny
    @FXML
    private void gåTilSkademeldingoversikt() {
        Viewbehandling.visSkademeldingOversikt(hovedApplikasjon);
        lagreFilMenuItem.setDisable(false);
    }

    /**
     * <--- FORSIKRING --->
     */
    @FXML
    public void gåTilForsikringoversikt() {
        Viewbehandling.visForsikringOversikt(hovedApplikasjon);
        lagreFilMenuItem.setDisable(false);
    }

    // <---- FILER ----->
    @FXML
    public void lagreFilTrykket() {
        Filbehandling.lagreFil(hovedApplikasjon);
    }

    @FXML
    public void hentFilTrykket() {
        hovedApplikasjon.getKundeData().setAll(Filbehandling.henteFil(hovedApplikasjon));
    }

    @FXML
    public void avsluttTrykket() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(hovedApplikasjon.getHovedStage());
        alert.setTitle("Avslutt");
        alert.setHeaderText("Bekreft avlsutning");
        alert.setContentText("Er du sikker på at du ønsker å avslutte?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Bekreft");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Avbryt");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            hovedApplikasjon.getHovedStage().close();
        }
    }

    @FXML
    public void visOmOss() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(hovedApplikasjon.getHovedStage());
        alert.setTitle("Om oss");
        alert.setHeaderText("Semesteroppgave DATA1600 Vår 2019");
        alert.setContentText("Dette er en semesteroppgave av studenter ved OsloMet.");

        alert.showAndWait();
    }
}