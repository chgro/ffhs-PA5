package ch.ffhs.pa5.backend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Diese Klasse dient für die Fehlerausgabe auf den Controller-Klassen zur Rückgabe eines definierten Fehlertextes
 */
@ResponseStatus(value= HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {
    /**
     * Konstruktor der Fehler-Klasse
     * @param message Fehlertext
     */
    public NoContentException(String message) {
        super(message);
    }
}
