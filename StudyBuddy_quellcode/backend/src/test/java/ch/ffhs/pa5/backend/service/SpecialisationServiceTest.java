package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.model.Specialisation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class SpecialisationServiceTest {

    @Autowired
    private SpecialisationService service;

    /**
     * Test: Service Anfrage alle Spezialisierungen
     *
     * @result: Gibt alle Spezialisierungen als Liste zurück, da der Service die Spezialisierungen serialisieren konnte
     */
    @Test
    public void ts006_PositiveCase() {
        // arrange
        var specialisations = new ArrayList<Specialisation>();
        specialisations.add(Specialisation.DATASCIENCE);
        specialisations.add(Specialisation.SECURITY);
        specialisations.add(Specialisation.ENTERPRISECOMPUTING);

        // act
        var result = service.getAllSpecialisations();

        // assert
        assertThat(result).containsAll(specialisations);
    }
}
