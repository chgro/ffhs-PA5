package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.controller.exception.FailureText;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.service.StudyPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StudyPlanController.class)
public class StudyPlanControllerTest {
    @MockBean
    private StudyPlanService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Test: HTTP POST für einen neuen Studienplan (NewStudyPlanRequest)
     *
     * @result: Gibt den neuen Studienplan zurück, da der Service den Request verarbeiten konnte
     */
    @Test
    public void tc002_PositiveCase() throws Exception {
        // arrange
        var studyPlanRequest = SomeData.newStudyPlanRequest;
        var requestJson = objectMapper.writeValueAsString(studyPlanRequest);
        var validStudyPlan = Optional.of(SomeData.studyPlan);
        var expectedJson = objectMapper.writeValueAsString(validStudyPlan);

        given(service.addNewStudyPlan(studyPlanRequest)).willReturn(validStudyPlan);

        // act
        var resultAction = mvc.perform(
                post("/api/v1/studyPlans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));

        // assert
        var result = resultAction.andReturn();
        var resultJson = result.getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJson);
    }

    /**
     * Test: HTTP POST für einen neuen Studienplan (NewStudyPlanRequest)
     *
     * @result: Gibt HTTP 400 zurück, da der Service den Request nicht verarbeiten konnte
     */
    @Test
    public void tc002_NegativeCase() throws Exception {
        // arrange
        var studyPlanRequest = SomeData.newStudyPlanRequest;
        var requestJson = objectMapper.writeValueAsString(studyPlanRequest);

        given(service.addNewStudyPlan(studyPlanRequest)).willReturn(Optional.empty());

        // act
        var resultAction = mvc.perform(
                post("/api/v1/studyPlans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.BAD_REQUEST.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.NEWSTUDYPLAN_BADREQUEST);
    }

    /**
     * Test: HTTP GET für den Studienplan mittels UUID abfragen
     *
     * @result: Gibt den Studienplan zurück, da der Service den Studienplan finden konnte
     */
    @Test
    public void tc004_PositiveCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var expectedJson = objectMapper.writeValueAsString(studyPlan);

        given(service.getStudyPlanById(studyPlan.getId())).willReturn(Optional.of(studyPlan));

        // act
        var resultAction = mvc.perform(
                get("/api/v1/studyPlans")
                        .param("studyPlanId", studyPlan.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var resultJson = result.getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJson);
    }

    /**
     * Test: HTTP GET für den Studienplan mittels UUID abfragen
     *
     * @result: Gibt HTTP 400 zurück, da der Service den Studienplan nicht finden konnte
     */
    @Test
    public void tc004_NegativeCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        given(service.getStudyPlanById(studyPlan.getId())).willReturn(Optional.empty());

        // act
        var resultAction = mvc.perform(
                get("/api/v1/studyPlans")
                        .param("studyPlanId", studyPlan.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.BAD_REQUEST.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.GETSTUDYPLAN_BADREQUEST);
    }

    /**
     * Test: HTTP POST Module für den Studienplan hinzufügen
     *
     * @result: Gibt den aktuallisierten Studienplan zurück, da der Service die Module dem Studienplan hinzufügen konnte
     */
    @Test
    public void tc007_PositiveCase() throws Exception {
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var moduleToStudyPlanRequest = SomeData.moduleToStudyPlanRequest;
        var requestJson = objectMapper.writeValueAsString(moduleToStudyPlanRequest);
        var serviceAnswer = SomeData.modulePlan;
        var expectedJson = objectMapper.writeValueAsString(serviceAnswer);

        given(service.addModuleToStudyPlan(SomeData.studyPlan.getId(), moduleToStudyPlanRequest)).willReturn(Optional.of(serviceAnswer));

        // act
        var resultAction = mvc.perform(
                post("/api/v1/studyPlans/{studyPlanId}/modules", studyPlanId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));

        // assert
        var result = resultAction.andReturn();
        var resultJson = result.getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJson);
    }

    /**
     * Test: HTTP POST Module für den Studienplan hinzufügen
     *
     * @result: Gibt HTTP 400 zurück, da der Service, da der Service die Module dem Studienplan hinzufügen konnte
     */
    @Test
    public void tc007_NegativeCase() throws Exception {
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var moduleToStudyPlanRequest = SomeData.moduleToStudyPlanRequest;
        var requestJson = objectMapper.writeValueAsString(moduleToStudyPlanRequest);

        given(service.addModuleToStudyPlan(SomeData.studyPlan.getId(), moduleToStudyPlanRequest)).willReturn(Optional.empty());

        // act
        var resultAction = mvc.perform(
                post("/api/v1/studyPlans/{studyPlanId}/modules", studyPlanId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.BAD_REQUEST.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.ADDMODULETOSTUDYPLAN_BADREQUEST);
    }

    /**
     * Test: HTTP DELETE Module aus dem StudienPlan entfernen
     *
     * @result: Gibt die ID des StudienPlan zurück, da der Service das Module dem Studienplan entfernen konnte
     */
    @Test
    public void tc009_PositiveCase() throws Exception {
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var moduleToDeleteId = 900;
        var expectedJson = objectMapper.writeValueAsString(studyPlanId);

        given(service.removeModuleFromStudyPlan(SomeData.studyPlan.getId(), moduleToDeleteId)).willReturn(true);

        // act
        var resultAction = mvc.perform(
                delete("/api/v1/studyPlans/{studyPlanId}/modules/{moduleId}", studyPlanId, moduleToDeleteId)
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var resultJson = result.getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJson);
    }

    /**
     * Test: HTTP DELETE Module aus dem StudienPlan entfernen
     *
     * @result: Gibt HTTP 400 zurück, da der Service das Modul nicht entfernen konnte
     */
    @Test
    public void tc009_NegativeCase() throws Exception {
        // arrange
        var studyPlanId = UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002");
        var moduleToDeleteId = 900;

        given(service.removeModuleFromStudyPlan(SomeData.studyPlan.getId(), moduleToDeleteId)).willReturn(false);

        // act
        var resultAction = mvc.perform(
                delete("/api/v1/studyPlans/{studyPlanId}/modules/{moduleId}", studyPlanId, moduleToDeleteId)
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.BAD_REQUEST.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.REMOVEMODULETOSTUDYPLAN_BADREQUEST);
    }


    /**
     * Test: HTTP GET für den Studienplan mittels Name abfragen
     *
     * @result: Gibt den Studienplan zurück, da der Service den Studienplan finden konnte
     */
    @Test
    public void tc010_PositiveCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var expectedJson = objectMapper.writeValueAsString(studyPlan);

        given(service.getStudyPlanByName(studyPlan.getName())).willReturn(Optional.of(studyPlan));

        // act
        var resultAction = mvc.perform(
                get("/api/v1/studyPlans")
                        .param("studyPlanName", studyPlan.getName())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var resultJson = result.getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJson);
    }

    /**
     * Test: HTTP GET für den Studienplan mittels Name abfragen
     *
     * @result: Gibt HTTP 400 zurück, da der Service den Studienplan nicht finden konnte
     */
    @Test
    public void tc010_NegativeCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        given(service.getStudyPlanByName(studyPlan.getName())).willReturn(Optional.empty());

        // act
        var resultAction = mvc.perform(
                get("/api/v1/studyPlans")
                        .param("studyPlanName", studyPlan.getName())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.BAD_REQUEST.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.GETSTUDYPLAN_BADREQUEST);
    }

    /**
     * Test: HTTP DELETE einen Studienplan mittels UUID
     *
     * @result: Gibt HTTP 200, gibt Studienplan erfolgreich gelöscht zurück
     */
    @Test
    public void tc011_PositiveCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var expectedJson = objectMapper.writeValueAsString(studyPlan.getId().toString());
        given(service.deleteStudyPlan(studyPlan.getId())).willReturn(true);

        // act
        var result = mvc.perform(
                delete("/api/v1/studyPlans/{studyPlanId}", studyPlan.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    /**
     * Test: HTTP DELETE für den Studienplan mittels UUID
     *
     * @result: Gibt HTTP 400, da der Service den Studienplan nicht löschen konnte
     */
    @Test
    public void tc011_NegativeCase1() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        given(service.deleteStudyPlan(studyPlan.getId())).willReturn(false);

        // act
        var resultAction = mvc.perform(
                delete("/api/v1/studyPlans/{studyPlanId}", studyPlan.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.BAD_REQUEST.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.DELETESTUDYPLAN_BADREQUEST);
    }

    /**
     * Test: HTTP GET ECTS Punkte mittels StudyPlan UUID abfragen
     *
     * @result: Gibt int ECTS Punkte erfolgreich zurück
     */
    @Test
    public void tc012_PositiveCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        given(service.getECTS(studyPlan.getId())).willReturn(5);

        // act
        var result = mvc.perform(
                get("/api/v1/studyPlans/{studyPlanId}/modules/ects", studyPlan.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(content().string("5"));
    }

    /**
     * Test: HTTP GET Relevanz Punkte mittels StudyPlan UUID abfragen
     *
     * @result: Gibt int Relevanz Punkte erfolgreich zurück
     */
    @Test
    public void tc013_PositiveCase() throws Exception {
        // arrange
        var studyPlan = SomeData.studyPlan;
        var specialisation = Specialisation.DATASCIENCE;
        given(service.getTotalRelevanceBySpecialisation(studyPlan.getId(), specialisation)).willReturn(5);

        // act
        var result = mvc.perform(
                get("/api/v1/studyPlans/{studPlanId}/specialisations/{specialisation}", studyPlan.getId().toString(), specialisation)
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(content().string("5"));
    }
}
