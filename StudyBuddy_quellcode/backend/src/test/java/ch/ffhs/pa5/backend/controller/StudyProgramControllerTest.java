package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.SomeData;
import ch.ffhs.pa5.backend.service.IStudyProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StudyProgramController.class)
public class StudyProgramControllerTest {
    @MockBean
    private IStudyProgramService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Test: Prüfen ob alle Studienprogramme zurückgegeben werden
     *
     * @result: Gibt eine Liste mit einem Wert zurück, da dies dem einzigen momentan vorhandenen Studienprogram entspricht
     */
    @Test
    public void tc003_PositiveCase() throws Exception {
        // arrange
        var studyPrograms = SomeData.studyPrograms;
        var expectedJson = objectMapper.writeValueAsString(studyPrograms);
        given(service.getAll()).willReturn(studyPrograms);

        // act
        var resultAction = mvc.perform(
                get("/api/v1/studyPrograms")
                        .contentType(MediaType.APPLICATION_JSON));

        // assert
        var result = resultAction.andReturn();
        var resultJson = result.getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJson);
    }
}
