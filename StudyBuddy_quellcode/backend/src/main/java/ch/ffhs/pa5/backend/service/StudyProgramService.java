package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.model.StudyProgram;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Dieser Service-Klasse dient der Abfrage aller vorhandenen Studienprogramme. Im Moment wird nur das Modell "HF Passarelle" angeboten.
 * Die Spezialisierungen sind im System einprogrammiert (ENUM) und können nicht geändert werden.
 * Es stehen nur Abfragen zur Verfügung, da die Spezialisierungen nicht geändert werden können.
 */
@Component
public class StudyProgramService implements IStudyProgramService{

    /**
     * Liefert alle vorhanden Studienprogramme. Gibt auf jeden Fall einen Wert zurück, da die Studienprogramme als ENUM statisch programmiert sind.
     *
     * @return Liste der Studiumsprogramme
     */
    @Override
    public List<StudyProgram> getAll() {
        return Arrays.stream(StudyProgram.values()).toList();
    }
}
