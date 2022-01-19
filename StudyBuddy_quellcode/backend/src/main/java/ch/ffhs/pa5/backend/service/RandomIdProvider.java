package ch.ffhs.pa5.backend.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Diese Hilfs-Klasse dient der Erzeugung einer eindeutigen Identifikations-Nummer
 * Die Klasse wurde erstellt um innerhalb der Test-Klassen die Identifikations-Nummer vorgeben (Mocken) zu können.
 */
@Primary
@Component("productiveIdProvider")
public class RandomIdProvider implements IRandomIdProvider {

    /**
     * Erstellt eine neue Identifikations-Nummer
     *
     * @return eindeutige Identifikations-Nummer als UUID
     */
    @Override
    public UUID id() {
        return UUID.randomUUID();
    }
}
