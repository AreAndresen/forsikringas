package ae.util;

import ae.model.Kunde;
import ae.model.Skademelding;
import javafx.collections.ObservableList;

public class IdUtil {

    /**
     * Løper gjennom ObservableListen og finner det høyeste forsikringsnummeret
     * og returnerer neste tall i rekken.
     */
    public static int genererLøpenummer(ObservableList<Kunde> kundeData) {
        int forrigeId = 0;
        for (Kunde kunde : kundeData) {
            if (kunde.getForsikringsNr() > forrigeId) {
                forrigeId = kunde.getForsikringsNr();
            }
        }
        return ++forrigeId;
    }

    public static int genererLøpenummerSkade(ObservableList<Skademelding> skademeldingData) {
        int forrigeId = 0;
        for (Skademelding skademelding : skademeldingData) {
            if (skademelding.getSkadeNr() > forrigeId) {
                forrigeId = skademelding.getSkadeNr();
            }
        }
        return ++forrigeId;
    }
}
