package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.service.IStudyPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * ValidatorController prüft, ob der gewünschte Name des Studienplans schon vorhanden/vergeben ist und
 * ein bestimmtes Modul bereits einem Studienplan zugeordnet ist.
 */
@RestController
@RequestMapping("/api/v1/validator")
public class ValidatorController {

    /**
     * Feld für DI   des StudyPlan-Dienstes
     */
    private final IStudyPlanService iStudyPlanService;

    /**
     * Konstruktor des Validator-Controllers
     *
     * @param iStudyPlanService StudyPlan-Dienst
     */
    ValidatorController(IStudyPlanService iStudyPlanService) {
        this.iStudyPlanService = iStudyPlanService;
    }

    /**
     * Liefert die Antwort, ob der Namen eines Studienplans gültig (noch nicht verwendet) ist.
     *
     * @param studyPlanName zu kontrollierender Name
     *
     * @return true, wenn der Name einzigartig ist
     */
    @GetMapping("validStudyPlan")
    public ResponseEntity<Boolean> validateStudyPlanByName(@RequestParam(name= "studyPlanName") String studyPlanName) {
        if(!studyPlanName.isEmpty()){
            var result = iStudyPlanService.studyPlanNameIsUnique(studyPlanName);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity(false, HttpStatus.OK);
    }

    /**
     * Lieft die Antwort, ob ein Modul (Parameter: moduleId) bereits im Studienplan vorhanden ist (Parameter: studyPlanId).
     *
     * @param studyPlanId ID des zu prüfenden Studienplans
     * @param moduleId ID des zu prüfenden Moduls
     *
     * @return true, wenn Modul einzigartig
     */
    @GetMapping("canAdd/studyPlan/{studyPlanId}/module/{moduleId}")
    public ResponseEntity<Boolean> canAddModuleToStudyPlan(@PathVariable("studyPlanId") UUID studyPlanId, @PathVariable int moduleId) {
        var result = !iStudyPlanService.checkIfStudyPlanContainsModule(studyPlanId, moduleId);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
