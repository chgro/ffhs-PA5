package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.model.StudyProgram;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class StudyProgramServiceTest {
    @Autowired
    private IStudyProgramService service;

    /**
     * Test: Prüfen ob alle Studienprogramme zurückgegeben werden
     *
     * @result: Gibt eine Liste mit einem Wert zurück, da dies dem einzigen momentan vorhandenen Studienprogramme entspricht
     */
    @Test
    public void ts004_PositiveCase() {
        // arrange
        var studyPrograms = new ArrayList<StudyProgram>();
        studyPrograms.add(StudyProgram.PASSARELLE);

        // act
        var result = service.getAll();

        //assert
        assertEquals(result, studyPrograms);
    }
}
