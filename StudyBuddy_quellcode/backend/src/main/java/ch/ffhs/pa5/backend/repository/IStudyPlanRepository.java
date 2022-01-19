package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.model.StudyPlan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Schnittstelle für generische CRUD-Operationen der StudyPlan-Klasse.
 */
public interface IStudyPlanRepository extends CrudRepository<StudyPlan, UUID> {
    /**
     * Liefert einen Studienplan auf Basis des Namens
     *
     * @param studyPlanName Name des Studienplans
     * @return ein Studienplan als Option
     */
    Optional<StudyPlan> findStudyPlanByName(String studyPlanName);
}
