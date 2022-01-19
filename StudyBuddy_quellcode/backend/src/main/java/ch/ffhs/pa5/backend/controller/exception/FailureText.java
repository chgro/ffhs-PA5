package ch.ffhs.pa5.backend.controller.exception;

/**
 * Diese Klasse dient für die Fehlerausgabe auf den Controller-Klassen zur Rückgabe eines definierten Fehlertextes
 */
public class FailureText {
    public static final String NEWSTUDYPLAN_BADREQUEST = "Bitte Datum, Name und Programm prüfen. Studienplan konnte nicht erstellt werden";
    public static final String GETSPECIALISATIONS_NOCONTENT = "Keine Spezialisierungen vorhanden";
    public static final String DELETESTUDYPLAN_BADREQUEST = "Studienplan konnte nicht gelöscht werden";
    public static final String GETSTUDYPLAN_BADREQUEST = "Studienplan konnte nicht gefunden werden";
    public static final String ADDMODULETOSTUDYPLAN_BADREQUEST = "Modul konnte nicht hinzugefügt werden";
    public static final String REMOVEMODULETOSTUDYPLAN_BADREQUEST = "Modul konnte nicht entfernt werden";
}
