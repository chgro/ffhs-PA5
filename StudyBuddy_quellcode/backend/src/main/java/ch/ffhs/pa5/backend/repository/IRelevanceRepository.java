package ch.ffhs.pa5.backend.repository;

import ch.ffhs.pa5.backend.model.Relevance;
import org.springframework.data.repository.CrudRepository;

/**
 * Schnittstelle für generische CRUD-Operationen der Relevance-Klasse.
 */
public interface IRelevanceRepository extends CrudRepository<Relevance, Integer> {
}
