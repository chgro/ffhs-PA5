package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.model.Module;
import ch.ffhs.pa5.backend.model.ModuleType;
import ch.ffhs.pa5.backend.model.SemesterType;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.repository.IModuleRepository;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Diese Service-Klasse ModulService dient der Abfrage der Module.
 * Die Modul-Daten werden mittels data.sql importiert.
 * Es stehen nur Abfragen zur Verfügung, da die Spezialisierungen nicht geändert werden können.
 */
@Component
public class ModuleService implements IModuleService {

    /**
     * Feld für DI des Modul-Repositories
     */
    private final IModuleRepository iModuleRepository;

    /**
     * Konstruktor der Module-Service Klasse
     *
     * @param iModuleRepository Module-Datenbank Repository - Interface oder konkrete Implementierung
     */
    public ModuleService(
            IModuleRepository iModuleRepository) {
        this.iModuleRepository = iModuleRepository;
    }

    /**
     * Diese Methode liefert auf Basis der Filter (Parameter) eine Liste von Modulen.
     * Die Liste beinhaltet in jedem Fall Werte, da unabhängig von den Filtern die Basis-Module angefügt werden.
     * Die Basis-Module müssen unabhängig von den Filtern zur Verfügung stehen, da diese in jedem Semester und jedem Jahr eingeplant werden können.
     *
     * @param specialisation die Spezialisierung, auf dessen Basis gefiltert werden soll
     * @param year           das Jahr, auf dessen Basis gefiltert werden soll
     * @param semesterType   der Semester-Typ, auf dessen Basis gefiltert werden soll
     * @return Liste von Modulen
     */
    @Override
    public List<Module> getModulesByFilter(Specialisation specialisation, int year, SemesterType semesterType) {
        Iterable<Module> modules = null;
        var basicModules = this.getByModuleType(ModuleType.BASIC);

        if (specialisation.equals(Specialisation.NONE)) {
            modules = this.iModuleRepository.findAll();
        } else {
            modules = this.iModuleRepository.findByRelevanceSpecialisation(specialisation);
        }
        var allModulesStream = StreamSupport.stream(modules.spliterator(), false);
        var filteredModules = allModulesStream
                .filter(i -> semesterType == SemesterType.ALL || i.getSemesterPlan() != null &&
                        i.getSemesterPlan().stream().anyMatch(x -> x.getSemesterType() == semesterType))
                .filter(i -> year == 0 || i.getSemesterPlan() != null &&
                        i.getSemesterPlan().stream().anyMatch(x -> x.getYear() == year))
                .collect(Collectors.toList());
        return Stream.of(basicModules, filteredModules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Liefert eine Liste von Modulen basierend auf dem Modul-Typ.
     * Falls zum gewählten Modul-Typ keine Module existieren, wird eine leere Liste zurückgegeben.
     * Modul-Typen sind in einem ENUM hinterlegt.
     *
     * @param moduleType Module zum gewählten Modul-Typ
     * @return Liste von Modulen
     */
    @Override
    public List<Module> getByModuleType(ModuleType moduleType) {
        var result = this.iModuleRepository.findByModuleType(moduleType);
        return StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());
    }
}
