package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.controller.model.AddModuleToStudyPlanRequest;
import ch.ffhs.pa5.backend.controller.model.NewStudyPlanRequest;
import ch.ffhs.pa5.backend.controller.exception.BadRequestException;
import ch.ffhs.pa5.backend.controller.exception.FailureText;
import ch.ffhs.pa5.backend.model.ModulePlan;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.model.StudyPlan;
import ch.ffhs.pa5.backend.service.IStudyPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Der StudyPlanController stellt die API zur Verfügung, welche vom Frontend HTTP-Requests
 * entgegennimmt und diese an den jeweiligen Service weiterleitet. Ein StudyPlan enthält alle Informationen die,
 * einem Studienplan beinhalten, wie z.B. Module, Spezialisierung. usw.
 */
@RestController
@RequestMapping("api/v1/studyPlans")
public class StudyPlanController {

    /**
     * Feld für DI des StudyPlan-Dienstes
     */
    private final IStudyPlanService iStudyPlanService;

    /**
     * Konstruktor des StudyPlan-Controllers
     *
     * @param iStudyPlanService StudyPlan-Dienst
     */
    StudyPlanController(IStudyPlanService iStudyPlanService) {
        this.iStudyPlanService = iStudyPlanService;
    }

    /**
     * Erstellt einen neuen Studienplan anhand eines StudyPlanRequest.
     *
     * @param studyPlanRequest enthält Angaben zu Name, Datum und Studienart des zu erstellenden Studienplans
     * @return den gerade erstellten Studienplan
     */
    @PostMapping()
    public @ResponseBody
    ResponseEntity<StudyPlan> addNewStudyPlan(
            @RequestBody NewStudyPlanRequest studyPlanRequest) {
        var result = this.iStudyPlanService.addNewStudyPlan(studyPlanRequest);
        if (!result.isPresent()) {
            throw new BadRequestException(FailureText.NEWSTUDYPLAN_BADREQUEST);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    /**
     * Liefert den Studienplan aufgrund des mitgegebenen Parameters.
     * Es kann entweder eine UUID oder der Name mitgegeben werden.
     *
     * @param studyPlanId die UUID des Studienplans
     * @param studyPlanName        der Name des Studienplans
     * @return den gefundenen Studienplan anhand UUID oder Name
     */
    @GetMapping()
    public @ResponseBody
    ResponseEntity<StudyPlan> getStudyPlan(
            @RequestParam(name = "studyPlanId", required = false) UUID studyPlanId,
            @RequestParam(name = "studyPlanName", required = false) String studyPlanName) {
        if (studyPlanId != null) {
            var studyPlan = this.iStudyPlanService.getStudyPlanById(studyPlanId);
            if (studyPlan.isPresent()) {
                return new ResponseEntity(studyPlan.get(), HttpStatus.OK);
            }
        }

        if (studyPlanName != null) {
            var studyPlan = this.iStudyPlanService.getStudyPlanByName(studyPlanName);
            if (studyPlan.isPresent()) {
                return new ResponseEntity(studyPlan.get(), HttpStatus.OK);
            }
        }
        throw new BadRequestException(FailureText.GETSTUDYPLAN_BADREQUEST);
    }

    /**
     * Fügt dem Studienplan (Parameter: studyPlanId) ein Modul (Parameter: AddModuleToStudyPlanRequest) hinzu.
     *
     * @param studyPlanId des Studienplans dem ein Modul hinzugefügt werden soll
     * @param module      welches dem Studienplan hinzugefügt werden soll
     * @return das Modul welches dem Studienplan hinzugefügt wurde
     */
    @PostMapping("{studyPlanId}/modules")
    public @ResponseBody
    ResponseEntity<ModulePlan> addModuleToStudyPlan(
            @PathVariable("studyPlanId") UUID studyPlanId,
            @RequestBody AddModuleToStudyPlanRequest module) {
        var result = this.iStudyPlanService.addModuleToStudyPlan(studyPlanId, module);
        if (!result.isPresent()) {
            throw new BadRequestException(FailureText.ADDMODULETOSTUDYPLAN_BADREQUEST);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    /**
     * Entfernt das ausgewählte Modul (Parameter: moduleId) vom Studienplan (Parameter: studyPlanId).
     *
     * @param studyPlanId des Studienplans des zu löschenden Moduls
     * @param moduleId    des zu löschenden Moduls im Studienplan
     * @return ein HttpStatus OK
     */
    @DeleteMapping("{studyPlanId}/modules/{moduleId}")
    public @ResponseBody
    ResponseEntity<UUID> removeModuleFromStudyPlan(@PathVariable("studyPlanId") UUID studyPlanId, @PathVariable int moduleId) {
        var result = this.iStudyPlanService.removeModuleFromStudyPlan(studyPlanId, moduleId);
        if (!result) {
            throw new BadRequestException(FailureText.REMOVEMODULETOSTUDYPLAN_BADREQUEST);
        }
        return new ResponseEntity<>(studyPlanId, HttpStatus.OK);
    }

    /**
     * Löscht einen Studienplan (Parameter: studyPlanId)
     *
     * @param studyPlanId des zu löschenden Studienplans
     * @return ein HttpStatus OK
     */
    @DeleteMapping("{studyPlanId}")
    public ResponseEntity<UUID> deleteStudyPlan(@PathVariable(name = "studyPlanId") UUID studyPlanId) {
        var isDeleted = this.iStudyPlanService.deleteStudyPlan(studyPlanId);
        if (!isDeleted) {
            throw new BadRequestException(FailureText.DELETESTUDYPLAN_BADREQUEST);
        }
        return new ResponseEntity<>(studyPlanId, HttpStatus.OK);
    }

    /**
     * Liefert die totale Anzahl an ECTS Punkten zu einem StudienPlan (Parameter: studyPlanId).
     *
     * @param studyPlanId des zu ermittelnden Studienplans
     * @return das Total der ECTS
     */
    @GetMapping("{studyPlanId}/modules/ects")
    public ResponseEntity<Integer> getTotalECTS(@PathVariable("studyPlanId") UUID studyPlanId) {
        var result = this.iStudyPlanService.getECTS(studyPlanId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Liefert die totale Anzahl an Referenzpunkte pro Spezialisierung (Parameter: specialisation) zu einem StudienPlan (Parameter: studyPlanId).
     *
     * @param studyPlanId    des zu ermittelnden Studienplans
     * @param specialisation die Spezialisierung
     * @return das Total der Referenzpunkte pro Spezialisierung
     */
    @GetMapping("{studyPlanId}/specialisations/{specialisation}")
    public ResponseEntity<Integer> getTotalRelevanceBySpecialisation(
            @PathVariable("studyPlanId") UUID studyPlanId,
            @PathVariable("specialisation") Specialisation specialisation) {
        var result = this.iStudyPlanService.getTotalRelevanceBySpecialisation(studyPlanId, specialisation);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}


