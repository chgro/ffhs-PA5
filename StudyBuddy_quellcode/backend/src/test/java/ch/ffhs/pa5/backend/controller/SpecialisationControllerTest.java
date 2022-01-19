package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.controller.exception.FailureText;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.service.SpecialisationService;
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

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SpecialisationController.class)
public class SpecialisationControllerTest {
    @MockBean
    private SpecialisationService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Test: HTTP GET für die Abfrage aller Spezialisierungen
     * @result: Gibt alle Spezialisierungen zurück, da der Servicedie Spezialisierungen serialisieren finden konnte
     */
    @Test
    public void tc005_PositiveCase() throws Exception {
        // arrange
        var specialisation = Specialisation.DATASCIENCE;
        var specialisations = Arrays.asList(specialisation);

        var expectedJson = objectMapper.writeValueAsString(specialisations);

        given(service.getAllSpecialisations()).willReturn(specialisations);

        // act
        var resultAction = mvc.perform(
                get("/api/v1/specialisations")
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        resultAction
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    /**
     * Test: HTTP GET für die Abfrage aller Spezialisierungen
     * @result: Gibt HTTP 204 zurück, da der Service die Spezialisierungen nicht serialisieren konnte
     */
    @Test
    public void tc005_NegativeCase() throws Exception {
        // arrange
        var specialisation = Specialisation.DATASCIENCE;
        var specialisations = Arrays.asList(specialisation);

        var expectedJson = objectMapper.writeValueAsString(specialisations);

        given(service.getAllSpecialisations()).willReturn(Collections.emptyList());

        // act
        var resultAction = mvc.perform(
                get("/api/v1/specialisations")
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var responseCode = result.getResponse().getStatus();

        assertEquals(responseCode, HttpStatus.NO_CONTENT.value());
        assertEquals(result.getResolvedException().getMessage(), FailureText.GETSPECIALISATIONS_NOCONTENT);
    }
}
