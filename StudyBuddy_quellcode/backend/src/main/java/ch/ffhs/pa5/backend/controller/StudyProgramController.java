package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.model.StudyProgram;
import ch.ffhs.pa5.backend.service.IStudyProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Der StudyProgramController stellt die API zur Verfügung, welche vom Frontend HTTP-Requests
 * entgegennimmt und diese an den jeweiligen Service weiterleitet. Ein StudyProgam gibt die Art des Studienganges an.
 */
@RestController
@RequestMapping("api/v1/studyPrograms")
public class StudyProgramController {

    /**
     * Feld für DI des StudyProgram-Dienstes
     */
    private final IStudyProgramService iStudyProgramService;

    /**
     * Konstruktor des StudyProgram-Controllers
     *
     * @param iStudyProgramService StudyProgram-Dienst
     */
    StudyProgramController(IStudyProgramService iStudyProgramService) {
        this.iStudyProgramService = iStudyProgramService;
    }

    /**
     * Liefert alle Studienprogramme. Im Moment steht nur die Passarelle HF zur Verfügung.
     *
     * @return eine Liste aller StudyPrograms
     */
    @GetMapping()
    public ResponseEntity<List<StudyProgram>> getStudyPrograms() {
        var result = this.iStudyProgramService.getAll();
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
