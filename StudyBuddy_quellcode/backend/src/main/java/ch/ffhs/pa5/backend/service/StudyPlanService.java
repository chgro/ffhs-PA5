package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.controller.model.AddModuleToStudyPlanRequest;
import ch.ffhs.pa5.backend.controller.model.NewStudyPlanRequest;
import ch.ffhs.pa5.backend.model.ModulePlan;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.model.StudyPlan;
import ch.ffhs.pa5.backend.model.StudyProgram;
import ch.ffhs.pa5.backend.repository.IModulePlanRepository;
import ch.ffhs.pa5.backend.repository.IModuleRepository;
import ch.ffhs.pa5.backend.repository.IStudyPlanRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Diese Service-Klasse StudyPlanService dient der Erstellung, Abfrage, Anpassung und Löschung des Studienplans.
 * Die Eindeutige Identifikation des Studienplans findet über die Identifikationsnummer (UUID) statt.
 * Der Studienplan wird auf dem UI erstellt und verwaltet.
 */
@Component
public class StudyPlanService implements IStudyPlanService {

    /**
     * Feld für DI des StudyPlan-Repositories
     */
    private final IStudyPlanRepository iStudyPlanRepository;

    /**
     * Feld für DI des ModulPlan-Repositories
     */
    private final IModulePlanRepository iModulePlanRepository;

    /**
     * Feld für DI des Modul-Repositories
     */
    private final IModuleRepository iModuleRepository;

    /**
     * Feld für DI des UUID-Generators
     */
    private final IRandomIdProvider iRandomIdProvider;

    /**
     * Konstruktor der StudyPlan-Service Klasse
     *
     * @param studyPlanRepository StudyPlan-Datenbank Repository - Interface oder konkrete Implementierung
     * @param modulePlanRepository ModulePlan-Datenbank Repository - Interface oder konkrete Implementierung
     * @param moduleRepository Module-Datenbank Repository - Interface oder konkrete Implementierung
     * @param randomIdProvider RandomId-Provider Interface oder konkrete Implementierung
     */
    public StudyPlanService(
            IStudyPlanRepository studyPlanRepository,
            IModulePlanRepository modulePlanRepository,
            IModuleRepository moduleRepository,
            IRandomIdProvider randomIdProvider) {
        this.iStudyPlanRepository = studyPlanRepository;
        this.iModulePlanRepository = modulePlanRepository;
        this.iModuleRepository = moduleRepository;
        this.iRandomIdProvider = randomIdProvider;
    }

	/**
     * Erstellt ein neuen Studienplan auf Basis der Werte, welche übers UI eingegeben wurden.
     * Es wird automatisch eine ID erstellt, welche für die weiterverwendung benötigt wird.
     * Die Werte, welche auf dem UI eingegeben wurden, werden hier geprüft. Falls die eingegebenen Daten nicht gültig sind, wird eine leere Option zurückgegeben.
     *
     * @param studyPlanRequest die Anfrage zur Erstellung eines neuen Studienplans
     * @return Studienplan als Option. Kann leer (empty sein)
     */
    @Override
    public Optional<StudyPlan> addNewStudyPlan(NewStudyPlanRequest studyPlanRequest) {
        if (validateStudyPlan(studyPlanRequest.getName(), studyPlanRequest.getDate(), studyPlanRequest.getStudyProgram())) {
            var id = iRandomIdProvider.id();
            var studyPlan = new StudyPlan.Builder()
                    .id(id)
                    .name(studyPlanRequest.getName())
                    .date(studyPlanRequest.getDate())
                    .studyProgram(studyPlanRequest.getStudyProgram())
                    .build();
            try {
                var persistedStudyPlan = this.iStudyPlanRepository.save(studyPlan);
                return Optional.of(persistedStudyPlan);
            } catch (Exception e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Liefert den gewünschten Studienplan auf Basis der Identifikations-Nummer.
     * Das Ergebnis kann leer sein (leere Option), falls der Studienplan nicht existiert.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @return StudyPlan als Option
     */
    @Override
    public Optional<StudyPlan> getStudyPlanById(UUID studyPlanId) {
        var studyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        if(studyPlan.isPresent() && studyPlan.get().getModulePlans().size() > 1)
        {
            var modulePlans = studyPlan.get().getModulePlans();
            Collections.sort(
                    modulePlans,
                    Comparator.comparingInt(ModulePlan::getSemester).thenComparing(modulePlan -> modulePlan.getYear()));
        }
        return studyPlan;
    }

    /**
     * Liefert den gewünschten Studienplan auf Basis des Namens.
     * Das Ergebnis kann leer sein (leere Option), falls der Studienplan nicht existiert.
     *
     * @param studyPlanName der Name des gesuchten Studienplans
     * @return StudyPlan als Option
     */
    @Override
    public Optional<StudyPlan> getStudyPlanByName(String studyPlanName) {
        var studyPlan = this.iStudyPlanRepository.findStudyPlanByName(studyPlanName);
        if(studyPlan.isPresent() && studyPlan.get().getModulePlans().size() > 1)
        {
            var modulePlans = studyPlan.get().getModulePlans();
            Collections.sort(
                    modulePlans,
                    Comparator.comparingInt(ModulePlan::getSemester).thenComparing(modulePlan -> modulePlan.getYear()));
        }

        return studyPlan;
    }

    /**
     * Fügt dem Modulplan eines Studienplans ein Modul hinzu.
     * Das Ergebnis kann leer sein (leere Option), falls der Studienplan oder das Modul nicht existiert.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @param modulePlanningRequest die Anfrage zur Erstellung eines neuen Modulplans
     * @return Modulplan als Option. Kann leer (empty sein)
     */
    @Override
    public Optional<ModulePlan> addModuleToStudyPlan(UUID studyPlanId, AddModuleToStudyPlanRequest modulePlanningRequest) {
        var mayBeStudyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        var mayBeModule = this.iModuleRepository.findById(modulePlanningRequest.getModuleId());
        if (mayBeStudyPlan.isPresent() && mayBeModule.isPresent()) {
            var module = mayBeModule.get();
            var studyPlan = mayBeStudyPlan.get();
            var newModulePlan = new ModulePlan.Builder()
                    .id(iRandomIdProvider.id())
                    .module(module)
                    .semester(modulePlanningRequest.getSemester())
                    .semesterType(modulePlanningRequest.getSemesterType())
                    .year(modulePlanningRequest.getYear())
                    .build();
            this.iModulePlanRepository.save(newModulePlan);
            var studyPlanModulePlans = Stream.concat(studyPlan.getModulePlans().stream(), Stream.of(newModulePlan)).distinct().collect(Collectors.toList());
            var updatedStudyPlan = new StudyPlan.Builder()
                    .id(studyPlan.getId())
                    .name(studyPlan.getName())
                    .date(new Date())
                    .modulePlans(studyPlanModulePlans)
                    .studyProgram(studyPlan.getStudyProgram())
                    .build();

            this.iStudyPlanRepository.save(updatedStudyPlan);
            return Optional.of(newModulePlan);
        }
        return Optional.empty();
    }

    /**
     * Prüft anhand der Identifikations-Nummern vom Studienplan und des Moduls ob dieses bereits vorhanden ist.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @param moduleId die Identifikations-Nummer des gesuchten Moduls
     * @return true, falls ds modul bereits vorhanden ist
     */
    @Override
    public Boolean checkIfStudyPlanContainsModule(UUID studyPlanId, int moduleId) {
        var mayBeStudyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        if(!mayBeStudyPlan.isPresent() || mayBeStudyPlan.get().getModulePlans() == null)
            return false;

        return mayBeStudyPlan.get().getModulePlans().stream().anyMatch(x -> Objects.equals(x.getModule().getId(), moduleId));
    }

    
    /**
     * Entfernt ein Modul aus dem Modulplan eines Studienplans.
     * Kann FALSE sein, wenn der Studienplan oder das Modul im Modulplan nicht gefunden wird.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @param moduleId die Identifikations-Nummer des gesuchten Moduls
     * @return TRUE, wenn Modul aus dem Modulplan des Studienplans erfolgreich entfernt wurde
     */
    @Override
    public boolean removeModuleFromStudyPlan(UUID studyPlanId, int moduleId) {
        var mayBeStudyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        if (mayBeStudyPlan.isPresent()) {
            if (checkIfStudyPlanContainsModule(studyPlanId, moduleId)) {
                var studyPlan = mayBeStudyPlan.get();

                var newModulePlan = studyPlan.getModulePlans().stream().filter(x -> x.getModule().getId() != moduleId).toList();

                var newStudyPlan = new StudyPlan.Builder()
                        .id(studyPlanId)
                        .name(studyPlan.getName())
                        .date(new Date())
                        .modulePlans(newModulePlan)
                        .studyProgram(studyPlan.getStudyProgram())
                        .build();
                this.iStudyPlanRepository.save(newStudyPlan);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Liefert das Total als Zahlenwerts der ECTS-Punkte im Studienplan.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @return Zahlenwert als int
     */
    @Override
    public int getECTS(UUID studyPlanId) {
        var mayBeStudyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        if (mayBeStudyPlan.isPresent()) {
            return mayBeStudyPlan.get().getModulePlans().stream().mapToInt(x -> x.getModule().getEcts()).sum();
        }
        return 0;
    }

    /**
     * Liefert das Total als Zahlenwerts der Referenzpunkte im Studienplan pro Spezialisierung.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @param specialisation die jeweilige Spezialisierung
     * @return Zahlenwert als int
     */
    @Override
    public int getTotalRelevanceBySpecialisation(UUID studyPlanId, Specialisation specialisation) {
        var mayBeStudyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        if (mayBeStudyPlan.isPresent()) {
            return mayBeStudyPlan.get().getModulePlans().stream()
                    .flatMap(u -> u.getModule().getRelevance().stream())
                    .filter(x -> x.getSpecialisation().equals(specialisation))
                    .map(p -> p.getRelevancePoint())
                    .mapToInt(i -> i.intValue()).sum();
        }
        return 0;
    }

    /**
     * Entfernt einen Studienplan.
     * Kann true sein, wenn der Studienplan noch vorhanden ist.
     *
     * @param studyPlanId die Identifikations-Nummer des gesuchten Studienplans
     * @return false, wenn der Studienplan nicht mehr gefunden wird
     */
    @Override
    public boolean deleteStudyPlan(UUID studyPlanId) {
        var mayBeStudyPlan = this.iStudyPlanRepository.findById(studyPlanId);
        if (mayBeStudyPlan.isPresent()) {
            this.iStudyPlanRepository.delete(mayBeStudyPlan.get());
            return this.iStudyPlanRepository.findById(studyPlanId).isPresent() ? false : true;
        }
        return false;
    }

    /**
     * Prüft anhand des Namens vom Studienplan, ob dieser einzigartig ist.
     *
     * @param studyPlanName der Name des gesuchten Studienplans
     * @return true, wenn der Name des Studienplans einzigartig ist
     */
    @Override
    public Boolean studyPlanNameIsUnique(String studyPlanName) {
        try {
            var mayBeStudyPlan = this.iStudyPlanRepository.findStudyPlanByName(studyPlanName);
            var result = mayBeStudyPlan.isEmpty();
            return result;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Prüft, ob ein Studienplan alle Pflichtfelder enthält.
     *
     * @param studyPlanName der Name des gesuchten Studienplans
     * @param date das Erstellungsdatum des Studienplans
     * @param studyProgram die Art des Studiengangs
     * @return den jeweiligen Eingabewert: Name, Datum oder Art des Studiengangs
     */
    public boolean validateStudyPlan(String studyPlanName, Date date, StudyProgram studyProgram) {
        return studyPlanName != null
                && studyPlanName.length() <= 255
                && date != null
                && studyProgram != null;
    }
}
