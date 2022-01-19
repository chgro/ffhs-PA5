package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.FakeIdProvider;
import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.repository.IModulePlanRepository;
import ch.ffhs.pa5.backend.repository.IModuleRepository;
import ch.ffhs.pa5.backend.repository.IStudyPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class StudyPlanServiceTest {

    @Autowired
    private StudyPlanService service;

    @Autowired
    @Qualifier("fakeIdProvider")
    IRandomIdProvider randomIdProvider = new FakeIdProvider();

    @MockBean
    private IStudyPlanRepository studyPlanRepository;

    @MockBean
    private IModulePlanRepository modulePlanRepository;

    @MockBean
    private IModuleRepository moduleRepository;

    @BeforeEach
    void setUp() {
        this.service = new StudyPlanService(
                studyPlanRepository,
                modulePlanRepository,
                moduleRepository,
                randomIdProvider
        );
    }

    /**
     * Test: Prüfen ob ein Studienplan mit demselben Namen bereits besteht
     *
     * @result: Gibt "FALSE" zurück, da der Name noch nicht vergeben ist
     */
    @Test
    public void ts001_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var name = studyPlan.getName();
        when(studyPlanRepository.findStudyPlanByName(name))
                .thenReturn(Optional.empty());

        // act
        var result = service.studyPlanNameIsUnique(name);

        // assert
        assertEquals(result, true);
    }

    /**
     * Test: Prüfen ob ein Studienplan mit demselben Namen bereits besteht
     *
     * @result: Test mit bereits vorhandenem Studienplan mit demselben Namen. Gibt "FALSE" zurück, da der Name bereits vergeben ist
     */
    @Test
    public void ts001_NegativeCase1() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var name = studyPlan.getName();
        when(studyPlanRepository.findStudyPlanByName(name))
                .thenReturn(Optional.of(studyPlan));

        // act
        var resultTrue = service.studyPlanNameIsUnique(name);

        // then
        assertEquals(resultTrue, false);
    }

    /**
     * Test: Prüfen ob ein Studienplan mit demselben Namen bereits besteht
     *
     * @result: Test mit Fehler bei Datenbank-Aufruf. Gibt "FALSE" zurück, da der Name bereits vergeben ist
     */
    @Test
    public void ts001_NegativeCase2() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var name = studyPlan.getName();
        when(studyPlanRepository.findStudyPlanByName(name))
                .thenThrow(new NullPointerException());

        // act
        var resultTrue = service.studyPlanNameIsUnique(name);

        // then
        assertEquals(resultTrue, false);
    }

    /**
     * Test: Prüfen ob ein Studienplan gültig/vollständig ist
     *
     * @result: Gibt "TRUE" zurück, da der der Stuienplan alle relevanten Informationen beinhaltet
     */
    @Test
    public void ts002_PositiveCase() {
        // arrange
        var newStudyPlanRequest = SomeData.newStudyPlanRequest;

        // act
        var result = service.validateStudyPlan(
                newStudyPlanRequest.getName(),
                newStudyPlanRequest.getDate(),
                newStudyPlanRequest.getStudyProgram());

        // assert
        assertTrue(result);
    }

    /**
     * Test: Prüfen ob ein Studienplan gültig/vollständig ist
     *
     * @result: Gibt "FALSE" zurück, da der der Name des Studienplans zu lange (>= 255 Zeichen lang) ist
     */
    @Test
    public void ts002_NegativeCase() {
        // arrange
        var newStudyPlanRequest = SomeData.falseStudyPlanRequest;

        // act
        var result = service.validateStudyPlan(
                newStudyPlanRequest.getName(),
                newStudyPlanRequest.getDate(),
                newStudyPlanRequest.getStudyProgram());

        // assert
        assertFalse(result);
    }

    /**
     * Test: Speichern eines neuen Studienplans (StudyPlanRequest)
     *
     * @result: Gibt den Studienplan zurück und Studienplan hat eine UUID, da der Request vollständig ist
     */
    @Test
    public void ts003_PositiveCase() {
        // arrange
        var studyPlanRequest = SomeData.newStudyPlanRequest;
        var studyPlan = SomeData.studyPlanWithoutModules;

        when(studyPlanRepository.save(studyPlan))
                .thenReturn(studyPlan);

        // act
        var result = service.addNewStudyPlan(studyPlanRequest);

        // assert
        assertTrue(result.isPresent());
    }

    /**
     * Test: Speichern eines neuen Studienplans (StudyPlanRequest)
     *
     * @result: Gibt ein leeres Optional zurück, da die Datenbank nicht aufgerufen werden kann (Mock wird bewusst nicht initiert)
     */
    @Test
    public void ts003_NegativeCase1() {
        // arrange
        var studyPlanRequest = SomeData.newStudyPlanRequest;

        // act
        var result = service.addNewStudyPlan(studyPlanRequest);

        // assert
        assertEquals(result, Optional.empty());
    }

    /**
     * Test: Speichern eines neuen Studienplans (StudyPlanRequest)
     *
     * @result: Gibt ein leeres Optional zurück, da der Request unvollständig ist
     */
    @Test
    public void ts003_NegativeCase2() {
        // arrange
        var studyPlanRequest = SomeData.falseStudyPlanRequest;

        // act
        var result = service.addNewStudyPlan(studyPlanRequest);

        // assert
        assertEquals(result, Optional.empty());
    }

    /**
     * Test: Finde StudienPlan mittels UUID
     *
     * @result: Gibt ein Optional von StudyPlan zurück da der Plan existiert
     */
    @Test
    public void ts005_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var expectedStudyPlan = Optional.of(studyPlan);
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(expectedStudyPlan);

        // act
        var result = service.getStudyPlanById(studyPlan.getId());

        // assert
        assertEquals(result, expectedStudyPlan);
    }

    /**
     * Test: Finde StudienPlan mittels UUID
     *
     * @result: Gibt ein leeres Optional zurück, da der Plan nicht existiert
     */
    @Test
    public void ts005_NegativeCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.empty());

        // act
        var result = service.getStudyPlanById(studyPlan.getId());

        // assert
        assertEquals(result, Optional.empty());
    }

    /**
     * Test: Füge Modul dem Studienplan hinzu
     *
     * @result: Gibt den Modulplan zurück, da alles geklappt hat
     */
    @Test
    public void ts009_PositiveCase(){
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var moduleToStudyPlanRequest = SomeData.moduleToStudyPlanRequest;
        var module = SomeData.module;
        var studyPlan = SomeData.studyPlan;
        var expectedModulePlan = SomeData.responseOfAddModuleToStudyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan));

        when(moduleRepository.findById(moduleToStudyPlanRequest.getModuleId())).thenReturn(Optional.ofNullable(module));

        // act
        var result = service.addModuleToStudyPlan(studyPlanId, moduleToStudyPlanRequest);

        // assert
        assertEquals(result, Optional.of(expectedModulePlan));
    }

    /**
     * Test: Füge Modul dem Studienplan hinzu
     *
     * @result: Gibt leeres Optional zurück, da der Studienplan und das Modul nicht gefunden werden konnte
     */
    @Test
    public void ts009_NegativeCase(){
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var moduleToStudyPlanRequest = SomeData.moduleToStudyPlanRequest;
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.empty());

        when(moduleRepository.findById(moduleToStudyPlanRequest.getModuleId())).thenReturn(Optional.empty());

        // act
        var result = service.addModuleToStudyPlan(studyPlanId, moduleToStudyPlanRequest);

        // assert
        assertEquals(result, Optional.empty());
    }

    /**
     * Test: Prüfe ob Studienplan das Modul enthält
     *
     * @result: Gibt true zurück, da der Studienplan das Modul enthält
     */
    @Test
    public void ts010_PositiveCase(){
        // arrange
        var studyPlan = SomeData.studyPlan;
        var module = studyPlan.getModulePlans().get(0);
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan));

        // act
        var result = service.checkIfStudyPlanContainsModule(studyPlan.getId(), module.getModule().getId());

        // assert
        assertEquals(result, true);
    }
    /**
     * Test: Prüfe ob Studienplan das Modul enthält
     *
     * @result: Gibt false zurück, da der Studienplan das Modul nicht enthält
     */
    @Test
    public void ts010_NegativeCase(){
        // arrange
        var studyPlan = SomeData.studyPlanWithoutModules;
        var module = SomeData.module;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan));

        // act
        var result = service.checkIfStudyPlanContainsModule(studyPlan.getId(), module.getId());

        // assert
        assertEquals(result, false);
    }


    /**
     * Test: Entferne Modul vom Studienplan
     *
     * @result: Gibt TRUE zurück, da die Entfernung geklappt hat
     */
    @Test
    public void ts011_PositiveCase(){
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var studyPlan = SomeData.studyPlan;
        var moduleId = SomeData.module.getId();

        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan));
        // act
        var result = service.removeModuleFromStudyPlan(studyPlanId, moduleId);

        // assert
        assertEquals(result, true);
    }

    /**
     * Test: Entferne Modul vom Studienplan
     *
     * @result: Test mit nicht vorhandenen Studienplan. Gibt FALSE zurück, da der Studienplan nicht gefunden werden konnte
     */
    @Test
    public void ts011_NegativeCase1(){
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var studyPlan = SomeData.studyPlan;
        var moduleId = SomeData.module.getId();

        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.empty());
        // act
        var result = service.removeModuleFromStudyPlan(studyPlanId, moduleId);

        // assert
        assertEquals(result, false);
    }

    /**
     * Test: Entferne Modul vom Studienplan
     *
     * @result: Test mit nicht vorhandenen Modul. Gibt FALSE zurück, da der Studienplan nicht gefunden werden konnte
     */
    @Test
    public void ts011_NegativeCase2(){
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var studyPlan = SomeData.studyPlan;
        var notExistingModule = 0;

        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan));
        // act
        var result = service.removeModuleFromStudyPlan(studyPlanId, notExistingModule);

        // assert
        assertEquals(result, false);
    }

    /**
     * Test: Finde StudienPlan mittels Name
     *
     * @result: Gibt ein Optional von StudyPlan zurück da der Plan existiert
     */
    @Test
    public void ts012_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var expectedStudyPlan = Optional.of(studyPlan);
        when(studyPlanRepository.findStudyPlanByName(studyPlan.getName()))
                .thenReturn(expectedStudyPlan);

        // act
        var result = service.getStudyPlanByName(studyPlan.getName());

        // assert
        assertEquals(result, expectedStudyPlan);
    }

    /**
     * Test: Finde StudienPlan mittels Name
     *
     * @result: Gibt ein leeres Optional zurück, da der Plan nicht existiert
     */
    @Test
    public void ts012_NegativeCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findStudyPlanByName(studyPlan.getName()))
                .thenReturn(Optional.empty());

        // act
        var result = service.getStudyPlanByName(studyPlan.getName());

        // assert
        assertEquals(result, Optional.empty());
    }

    /**
     * Test: Lösche StudienPlan mittels UUID
     *
     * @result: Gibt true zurück, da Studienplan gelöscht werden konnte
     */
    @Test
    public void ts013_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan))
                .thenReturn(Optional.empty());

        // act
        var result = service.deleteStudyPlan(studyPlan.getId());

        // assert
        assertTrue(result);
    }

    /**
     * Test: Lösche StudienPlan mittels UUID
     *
     * @result: Gibt false zurück, da Studienplan nicht gefunden werden konnte
     */
    @Test
    public void ts013_NegativeCase1() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.empty());

        // act
        var result = service.deleteStudyPlan(studyPlan.getId());

        // assert
        assertFalse(result);
    }

    /**
     * Test: Lösche StudienPlan mittels UUID
     *
     * @result: Gibt false zurück, da Studienplan nach löschen noch vorhanden ist
     */
    @Test
    public void ts013_NegativeCase2() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.of(studyPlan))
                .thenReturn(Optional.of(studyPlan));

        // act
        var result = service.deleteStudyPlan(studyPlan.getId());

        // assert
        assertFalse(result);
    }

    /**
     * Test: Finde ECTS Punkte mittels StudyPlan UUID
     *
     * @result: Gibt ein int der ECTS Punkte von StudyPlan zurück da der Plan existiert
     */
    @Test
    public void ts014_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var expectedStudyPlan = Optional.of(studyPlan);
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(expectedStudyPlan);

        // act
        var result = service.getECTS(studyPlan.getId());

        // assert
        assertEquals(result, studyPlan.getModulePlans().get(0).getModule().getEcts());
    }

    /**
     * Test: Finde ECTS Punkte mittels StudyPlan UUID
     *
     * @result: Gibt int 0 zurück, da der Plan nicht existiert
     */
    @Test
    public void ts014_NegativeCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.empty());

        // act
        var result = service.getECTS(studyPlan.getId());

        // assert
        assertEquals(result, 0);
    }

    /**
     * Test: Finde Relevanz Punkte mittels StudyPlan UUID
     *
     * @result: Gibt ein int der Relevanz Punkte von StudyPlan zurück da der Plan existiert
     */
    @Test
    public void ts015_PositiveCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var specialisation = Specialisation.DATASCIENCE;
        var expectedStudyPlan = Optional.of(studyPlan);
        var relevancePoints = 5;

        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(expectedStudyPlan);

        // act
        var result = service.getTotalRelevanceBySpecialisation(studyPlan.getId(), specialisation);

        // assert
        assertEquals(result, relevancePoints);
    }

    /**
     * Test: Finde Relevanz Punkte mittels StudyPlan UUID
     *
     * @result: Gibt ein int 0 zurück, da der Plan nicht existiert
     */
    @Test
    public void ts015_NegativeCase() {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var specialisation = Specialisation.DATASCIENCE;
        when(studyPlanRepository.findById(studyPlan.getId()))
                .thenReturn(Optional.empty());

        // act
        var result = service.getTotalRelevanceBySpecialisation(studyPlan.getId(), specialisation);

        // assert
        assertEquals(result, 0);
    }
}
