package ch.ffhs.pa5.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BackendApplication ist der Haupteinstieg für die Anwendung
 *
 */
@SpringBootApplication
public class BackendApplication {

    /**
     * Main-Klasse der Java-Applikation
     *
     * @param args ein array von Kommandozeilen-Parameter für die Applikation
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
