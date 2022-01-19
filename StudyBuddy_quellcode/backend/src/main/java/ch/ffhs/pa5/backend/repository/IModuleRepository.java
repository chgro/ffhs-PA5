package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.model.Module;
import ch.ffhs.pa5.backend.model.ModuleType;
import ch.ffhs.pa5.backend.model.Specialisation;
import org.springframework.data.repository.CrudRepository;

/**
 * Schnittstelle für generische CRUD-Operationen der Module-Klasse.
 */
public interface IModuleRepository extends CrudRepository<Module, Integer> {
    /**
     * Liefert Module basierend auf deren Spezialisierung
     *
     * @param specialisation die gewünschte Specialisation der gesuchten Module
     * @return Module als Iterable
     */
    Iterable<Module> findByRelevanceSpecialisation(Specialisation specialisation);

    /**
     * Liefert die Module basierend auf dem Modultyp
     *
     * @param moduleType Typ der gesuchten Module
     * @return Module als Iterable
     */
    Iterable<Module> findByModuleType(ModuleType moduleType);
}
