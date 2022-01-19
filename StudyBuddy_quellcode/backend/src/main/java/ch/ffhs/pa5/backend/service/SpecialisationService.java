package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.model.Specialisation;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Diese Service-Klasse SpecialisationService dient der Abfrage der Spezialisierungen.
 * Die Spezialisierungen sind im System einprogrammiert (ENUM) und können nicht geändert werden.
 * Es stehen nur Abfragen zur Verfügung, da die Spezialisierungen nicht geändert werden können.
 */
@Component
public class SpecialisationService implements ISpecialisationService {

    /**
     * Liefert eine Liste aller Spezialisierungen. Gibt auf jeden Fall einen Wert zurück, da die Spezialisierungen als ENUM statisch programmiert sind.
     *
     * @return eine Liste alles Spezialisierungen
     */
    @Override
    public List<Specialisation> getAllSpecialisations() {
        return new ArrayList<>(EnumSet.allOf(Specialisation.class));
    }
}
