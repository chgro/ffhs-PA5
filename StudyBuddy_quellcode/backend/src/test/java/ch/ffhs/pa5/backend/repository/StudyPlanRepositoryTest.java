package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.SomeData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* Test Against real Database:
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
*/
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class StudyPlanRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IStudyPlanRepository repository;

    @BeforeEach
    public void clearBefore() {
        entityManager.clear();
    }

    @AfterEach
    public void clearAfter() {
        entityManager.clear();
    }

    /**
     * Test: Prüfen ob ein Studienplan mittels Namen abgerufen werden kann
     *
     * @result: Gibt den Studienplan zurück, da der Studienplan mit dem Namen existiert
     */
    @Test
    public void tr001_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlanWithoutModules;
        entityManager.persist(studyPlan);

        // act
        var result = repository.findStudyPlanByName(studyPlan.getName());

        // assert
        assertEquals(result, Optional.of(studyPlan));
    }

    /**
     * Test: Prüfen ob ein Studienplan persistiert werden kann
     *
     * @result: Speichert den Studienplan und gibt den Studienplan zurück
     */
    @Test
    public void tr002_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlanWithoutModules;

        // act
        var result = repository.save(studyPlan);

        // assert
        assertEquals(result, studyPlan);
    }

    /**
     * Test: Prüfen ob ein Studienplan mittels ID abgerufen werden kann
     *
     * @result: Gibt den Studienplan zurück, da der Studienplan mit der ID existiert
     */
    @Test
    public void tr003_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        entityManager.persist(studyPlan);

        // act
        var result = repository.findById(studyPlan.getId());

        // assert
        assertEquals(result, Optional.of(studyPlan));
    }

    /**
     * Test: Prüfen ob ein Studienplan gespeichert/aktuallisiert werden kann.
     *
     * @result: Gibt den Studienplan zurück mit den Änderungen zurück
     */
    @Test
    public void tr007_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        for (var modulePlan: studyPlan.getModulePlans()) {
            entityManager.persist(modulePlan);
        }

        // act
        var result = repository.save(studyPlan);

        // assert
        assertEquals(result.getModulePlans(), studyPlan.getModulePlans());
    }

    /**
     * Test: Prüfen ob ein Studienplan gelöscht werden konnte
     *
     * @result: Studienplan ist nicht mehr vorhanden da dieser gelöscht werden konnte
     */
    @Test
    public void tr008_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        entityManager.persist(studyPlan);

        // act
        repository.delete(studyPlan);
        var result = repository.findById(studyPlan.getId());

        // assert
        assertEquals(result, Optional.empty());
    }
}
