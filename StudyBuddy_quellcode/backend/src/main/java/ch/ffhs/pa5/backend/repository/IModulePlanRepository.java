package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.model.ModulePlan;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Schnittstelle für generische CRUD-Operationen der ModulePlan-Klasse.
 */
public interface IModulePlanRepository extends CrudRepository<ModulePlan, UUID> {
}
