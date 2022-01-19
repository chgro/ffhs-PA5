package ch.ffhs.pa5.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Die Klasse beinhaltet die Informationen zu Relevanzpunkten (Relevanzpunkte pro Vertiefungsrichtung). Die Daten werden mittels Spring Boot beim Starten initialisiert (data.sql).
 */
@Entity
@Table(name = "relevance")
public class Relevance {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Specialisation specialisation;

    @NotNull
    private int relevancePoint;

    public Relevance(int id, Specialisation specialisation, int relevancePoint) {
        this.id = id;
        this.specialisation = specialisation;
        this.relevancePoint = relevancePoint;
    }

    public Relevance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public int getRelevancePoint() {
        return relevancePoint;
    }

    public void setRelevancePoint(int relevancePoint) {
        this.relevancePoint = relevancePoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relevance relevance = (Relevance) o;

        if (id != relevance.id) return false;
        if (relevancePoint != relevance.relevancePoint) return false;
        return specialisation == relevance.specialisation;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (specialisation != null ? specialisation.hashCode() : 0);
        result = 31 * result + relevancePoint;
        return result;
    }
}
