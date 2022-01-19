package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.model.SemesterPlan;
import org.springframework.data.repository.CrudRepository;

/**
 * Schnittstelle für generische CRUD-Operationen der SemesterPlan-Klasse.
 */
public interface ISemesterPlanRepository extends CrudRepository<SemesterPlan, Integer> {
}
