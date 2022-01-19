package ch.ffhs.pa5.backend.controller;

import ch.ffhs.pa5.backend.model.Module;
import ch.ffhs.pa5.backend.model.SemesterType;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.service.IModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Der ModuleController stellt die API zur Verfügung, welche vom Frontend HTTP-Requests
 * entgegennimmt und diese an den jeweiligen Service weiterleitet. Ein Modul kann belegt werden,
 * um sich in einem Thema weiterzubilden und Referenzpunkte sowie ECTS-Punkte zu erhalten.
 */
@RestController
@RequestMapping("api/v1/modules")
public class ModuleController {

    /**
     * Feld für DI des Modul-Dienstes
     */
    private final IModuleService iModuleService;

    /**
     * Konstruktor des Module-Controllers
     *
     * @param iModuleService Modul-Dienst
     */
    ModuleController(IModuleService iModuleService) {
        this.iModuleService = iModuleService;
    }


    /**
     * Dieser Endpunkt liefert eine Liste von Modulen auf Basis der definierten Filter.
     *
     * @param specialisation Die gesetzte Spezialisierungsrichtung des Studienplans (Beispiel: DATASCIENCE)
     * @param year Das Jahr (Beispiel: 2021)
     * @param semesterType Der SemesterTyp (Beispiel: AUTUMN)
     * @return Eine Liste aller Module auf Basis der angegebenen Parameter
     */
    @GetMapping
    public ResponseEntity<List<Module>> getModulesByFilter(@RequestParam(name="specialisation", required=false, defaultValue = "NONE") Specialisation specialisation,
                                                          @RequestParam(name="year", required = false, defaultValue = "0") Integer year,
                                                          @RequestParam(name="semesterType", required = false, defaultValue = "ALL") SemesterType semesterType){
        var result = this.iModuleService.getModulesByFilter(specialisation, year, semesterType);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}