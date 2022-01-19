package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.model.ModuleType;
import ch.ffhs.pa5.backend.model.SemesterType;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.repository.IModuleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ModuleServiceTest {

    @Autowired
    private ModuleService service;

    @MockBean
    private IModuleRepository repository;

    /**
     * Test: Prüfen ob für Filter Module vorhanden sind
     *
     * @result: Testen mit Spezialisierung. Gibt eine Liste mit Modulen zurück, da zum angegebenen Filter Module vorhanden sind
     */
    @Test
    public void ts007_PositiveCase1() {
        // arrange
        var module = SomeData.module;
        var specialisation = Specialisation.DATASCIENCE;
        Mockito.when(repository.findByRelevanceSpecialisation(specialisation))
                .thenReturn(List.of(module));

        // act
        var result = service.getModulesByFilter(specialisation, 2021, SemesterType.ALL);

        // assert
        assert(result).contains(module);
    }

    /**
     * Test: Prüfen ob für Filter Module vorhanden sind
     *
     * @result: Testen ohne Spezialisierung. Gibt eine Liste mit Modulen zurück, da zum angegebenen Filter Module vorhanden sind
     */
    @Test
    public void ts007_PositiveCase2() {
        // arrange
        var module = SomeData.basicModule;
        var specialisation = Specialisation.NONE;
        Mockito.when(repository.findAll())
                .thenReturn(List.of(module));

        // act
        var result = service.getModulesByFilter(specialisation, 2021, SemesterType.ALL);

        // assert
        assert(result).contains(module);
    }

    /**
     * Test: Finde ein Liste von Modul auf Basis seines Typs (ModuleType)
     *
     * @result: Gibt ein Modul zurück, da das Repository das Modul gefunden hat
     */
    @Test
    public void ts008_PositiveCase() {
        // arrange
        var module = SomeData.module;
        var moduleType = ModuleType.ELECTIVE;
        Mockito.when(repository.findByModuleType(moduleType))
                .thenReturn(List.of(module));

        // act
        var result = service.getByModuleType(moduleType);

        // assert
        assert(result).contains(module);
    }


    /**
     * Test: Finde ein Liste von Modul auf Basis seines Typs (ModuleType)
     *
     * @result: Gibt ein Leere Liste zurück, da das Repository keine Modul gefunden hat
     */
    @Test
    public void ts008_NegativeCase() {
        // arrange
        var module = SomeData.module;
        var moduleType = ModuleType.ELECTIVE;
        Mockito.when(repository.findByModuleType(moduleType))
                .thenReturn(List.of());

        // act
        var result = service.getByModuleType(moduleType);

        // assert
        assertEquals(result, List.of());
    }
}
