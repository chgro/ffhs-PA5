package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.controller.exception.FailureText;
import ch.ffhs.pa5.backend.service.IStudyPlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ValidatorController.class)
public class ValidatorControllerTest {
    @MockBean
    private IStudyPlanService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Test: Prüfen ob ein Studienplan mit demselben Namen bereits besteht
     *
     * @result: Gibt "TRUE" zurück, da der Name noch nicht vergeben ist
     */
    @Test
    public void tc001_PositiveCase() throws Exception {
        // arrange
        var studyPlanName = "test";
        given(service.studyPlanNameIsUnique(studyPlanName)).willReturn(true);

        // act
        var result = mvc.perform(
                get("/api/v1/validator/validStudyPlan")
                        .param("studyPlanName", studyPlanName)
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    /**
     * Test: Prüfen ob ein Studienplan mit demselben Namen bereits besteht
     *
     * @result: Gibt "FALSE" zurück, da der Name bereits vergeben ist
     */
    @Test
    public void tc001_NegativeCase1() throws Exception {
        // arrange
        var studyPlanName = "test";
        given(service.studyPlanNameIsUnique(studyPlanName)).willReturn(false);

        // act
        var result = mvc.perform(
                get("/api/v1/validator/validStudyPlan")
                        .param("studyPlanName", studyPlanName)
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    /**
     * Test: Prüfen ob ein Studienplan mit demselben Namen bereits besteht
     *
     * @result: Gibt "FALSE" zurück, da der Name leer ist
     */
    @Test
    public void tc001_NegativeCase2() throws Exception {
        // act
        var result = mvc.perform(
                get("/api/v1/validator/validStudyPlan")
                        .param("studyPlanName", "")
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    /**
     * Test: Prüfen ob ein Modul dem Studienplan hinzugefügt werden kann
     *
     * @result Gibt "TRUE" zurück, da das Modul dem Studienplan hinzugefügt werden kann
     */
    @Test
    public void tc008_PositiveCase() throws Exception {
        // arrange
        var studyPlanId = SomeData.studyPlan.getId();
        var moduleId = SomeData.module.getId();
        given(service.checkIfStudyPlanContainsModule(studyPlanId, moduleId)).willReturn(false);

        // act
        var result = mvc.perform(
                get("/api/v1/validator/canAdd/studyPlan/{studyPlanId}/module/{moduleId}",
                        studyPlanId.toString(),
                        Integer.toString(moduleId))
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    /**
     * Test: Prüfen ob ein Modul dem Studienplan hinzugefügt werden kann
     *
     * @result Gibt "FALSE" zurück, da das Modul dem Studienplan nicht hinzugefügt werden kann
     */
    @Test
    public void tc007_NegativeCase1() throws Exception {
        // arrange
        var studyPlanId = SomeData.studyPlan.getId();
        var moduleId = SomeData.module.getId();
        given(service.checkIfStudyPlanContainsModule(studyPlanId, moduleId)).willReturn(true);

        // act
        var result = mvc.perform(
                get("/api/v1/validator/canAdd/studyPlan/{studyPlanId}/module/{moduleId}",
                        studyPlanId.toString(),
                        Integer.toString(moduleId))
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}