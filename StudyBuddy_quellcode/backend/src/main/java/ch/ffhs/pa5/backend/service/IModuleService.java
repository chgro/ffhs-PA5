package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.model.Module;
import ch.ffhs.pa5.backend.model.ModuleType;
import ch.ffhs.pa5.backend.model.SemesterType;
import ch.ffhs.pa5.backend.model.Specialisation;

import java.util.List;

/**
 * Interface der Module-Dienste. Die vorhandenen Module müssen nur abgefragt werden, da diese mittels data.sql automatisch in die Datenbank persistiert werden.
 */
public interface IModuleService {
    /**
     * Liefert Module basierend auf deren Spezialisierung
     *
     * @param specialisation
     * @param year
     * @param semesterType
     * @return Liste von Modulen
     */
    List<Module> getModulesByFilter(Specialisation specialisation, int year, SemesterType semesterType);

    /**
     *
     *
     * @param moduleType der
     * @return Liste von Modulen
     */
    List<Module> getByModuleType(ModuleType moduleType);
}
