package ch.ffhs.pa5.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Die Klasse beinhaltet die Informationen zu der Planung der Module. Ein Modul findet nur bei bestimmten Kombinationen (Jahr und Semester) statt. Die Daten werden mittels Spring Boot beim Starten initialisiert (data.sql).
 */
@Entity
@Table(name = "semesterPlan")
public class SemesterPlan {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @NotNull
    private int year;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private SemesterType semesterType;

    public SemesterPlan(){
    }

    public SemesterPlan(int id, int year, SemesterType semesterType) {
        this.id = id;
        this.year = year;
        this.semesterType = semesterType;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SemesterPlan that = (SemesterPlan) o;

        if (id != that.id) return false;
        if (year != that.year) return false;
        return semesterType == that.semesterType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        result = 31 * result + (semesterType != null ? semesterType.hashCode() : 0);
        return result;
    }
}
