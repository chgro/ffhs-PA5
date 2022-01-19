package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.model.SemesterType;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.service.ModuleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ModuleController.class)
public class ModuleControllerTest {
    @MockBean
    private ModuleService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Test: HTTP GET für Spezialisierungen eines Modules mittels Filter
     *
     * @result: Gibt eine Liste von Modulen zurück, da der Service die Module finden konnte
     */
    @Test
    public void tc006_PositiveCase() throws Exception {
        // arrange
        var modules = SomeData.modules;
        var specialisation = Specialisation.NONE;
        var expectedJson = objectMapper.writeValueAsString(modules);
        var year = 2022;
        var semesterType = SemesterType.ALL;
        given(service.getModulesByFilter(specialisation, year, semesterType)).willReturn(modules);

        // act
        var result = mvc.perform(
                get("/api/v1/modules?specialisation={specialisation}&year={year}&semesterType={semesterType}",specialisation, year, semesterType)
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        result
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }
}
