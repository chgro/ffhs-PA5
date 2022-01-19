package ch.ffhs.pa5.backend.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Diese Klasse dient für die Fehlerausgabe auf den Controller-Klassen zur Rückgabe eines definierten Fehlertextes
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    /**
     * Konstruktor der Fehler-Klasse
     * @param message Fehlertext
     */
    public BadRequestException(String message) {
        super(message);
    }
}
