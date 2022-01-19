package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.model.ModuleType;
import ch.ffhs.pa5.backend.model.Specialisation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
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
public class ModuleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IModuleRepository moduleRepository;

    @Autowired
    private IRelevanceRepository relevanceRepository;

    @BeforeEach
    public void clearBefore() {
        entityManager.clear();
    }

    @AfterEach
    public void clearAfter() {
        entityManager.clear();
    }

    /**
     * Test: Prüfen ob alle Module abgerufen werden können
     *
     * @result: Gibt eine Liste mit Modulen zurück, da Module existieren
     */
    @Test
    public void tr004_PositiveCase() {
        // arrange
        var module = SomeData.module;
        entityManager.persist(module);

        // act
        var found = moduleRepository.findAll();
        var result = StreamSupport.stream(found.spliterator(), false)
                .collect(Collectors.toList());
        // assert
        assertThat(result.contains(module));
    }

    /**
     * Test: Prüfen ob alle Module abgerufen werden können
     *
     * @result: Gibt eine Liste mit Modulen zurück, da keine Module existieren
     */
    @Test
    public void tr004_NegativeCase() {
        // act
        var result = moduleRepository.findAll();

        // assert
        assertEquals(result, Collections.emptyList());
    }

    /**
     * Test: Prüfen ob alle Module einer Spezialisierung abgerufen werden können
     *
     * @result: Gibt eine Liste mit Modulen zurück, da Module mit der gewählten Spezialisierung existieren
     */
    @Test
    @Commit
    public void tr005_PositiveCase() {
        // arrange
        var modules = SomeData.modules;
        for (var module : modules) {
            entityManager.persist(module);
        }

        var expectedModule = modules.get(0);

        // act
        var test = moduleRepository.findByRelevanceSpecialisation(Specialisation.DATASCIENCE);
        var found = moduleRepository.findAll();
        var result = StreamSupport.stream(found.spliterator(), false)
                .collect(Collectors.toList());
        // assert
        assertThat(result.stream().findFirst().get()).isEqualTo(expectedModule);
    }

    /**
     * Test: Prüfen ob alle Module einer Spezialisierung abgerufen werden können
     *
     * @result: Gibt eine Liste mit Modulen zurück, da keine Module mit der gewählten Spezialisierung existieren
     */
    @Test
    public void tr005_NegativeCase() {
        // act
        var result = moduleRepository.findByRelevanceSpecialisation(Specialisation.DATASCIENCE);

        // assert
        assertEquals(result, Collections.emptyList());
    }

    /**
     * Test: Prüfen ob alle Module eines Modultyps abgerufen werden können
     *
     * @result: Gibt eine Liste mit Modulen zurück, da die Module mit dem gewählten Modultyps existieren
     */
    @Test
    public void tr006_PositiveCase() {
        var module = SomeData.module;
        entityManager.persist(module);
        var expectedModule = module;

        // act
        var found = moduleRepository.findByModuleType(ModuleType.ELECTIVE);
        var result = StreamSupport.stream(found.spliterator(), false)
                .collect(Collectors.toList());
        // assert
        assertThat(result).containsExactly(expectedModule);
    }

    /**
     * Test: Prüfen ob alle Module eines Modultyps abgerufen werden können
     *
     * @result: Gibt eine leere Liste mit Modulen zurück, da die Module mit dem gewählten Modultyps nicht existieren
     */
    @Test
    public void tr006_NegativeCase() {
        // act
        var found = moduleRepository.findByModuleType(ModuleType.IMMERSION);
        var result = StreamSupport.stream(found.spliterator(), false)
                .collect(Collectors.toList());
        // assert
        assertThat(result).isEqualTo(List.of());
    }
}
