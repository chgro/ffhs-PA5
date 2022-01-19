package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.controller.exception.FailureText;
import ch.ffhs.pa5.backend.controller.exception.NoContentException;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.service.ISpecialisationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Der SpecialisationController stellt die API zur Verfügung, welche vom Frontend HTTP-Requests
 * entgegennimmt und diese an den jeweiligen Service weiterleitet. Eine Spezialisierung gibt an, für welche
 * Fachrichtung Referenzpunkte erzielt werden müssen um, entsprechende Spezialisierung zu erfüllen.
 */
@RestController
@RequestMapping("api/v1/specialisations")
class SpecialisationController {

    /**
     * Feld für DI des Specialisation-Dienstes
     */
    private final ISpecialisationService iSpecialisationService;

    /**
     * Konstruktor des Specialisation-Controllers
     *
     * @param iSpecialisationService Specialisation-Dienst
     */
    SpecialisationController(ISpecialisationService iSpecialisationService) {
        this.iSpecialisationService = iSpecialisationService;
    }

    /**
     * Liefert alle Spezialisierungen.
     *
     * @return eine Liste aller Spezialisierungen
     */
    @GetMapping()
    @ResponseBody
    public ResponseEntity<List<Specialisation>> getSpecialisations() {
        var specialisations = this.iSpecialisationService.getAllSpecialisations();
        if(specialisations != null && !specialisations.isEmpty()){
            return new ResponseEntity(specialisations, HttpStatus.OK);
        }
        throw new NoContentException(FailureText.GETSPECIALISATIONS_NOCONTENT);
    }
}