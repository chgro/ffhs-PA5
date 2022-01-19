package ch.ffhs.pa5.backend.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

/**
 * Klasse, welche für die Klasse StudyPlan verwendet wird. Da zu einer Studienplanung mehrere Module mit Informationen
 * (Semester, Semestertyp usw.) hinzugefügt werden können, werden diese als eigene Klasse repräsentiert.
 */
@Entity
@Table(name = "modulePlan")
public class ModulePlan {
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @ManyToOne
    private Module module;

    @NotNull
    private int semester;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private SemesterType semesterType;

    @NotNull
    private int year;

    public ModulePlan(Builder builder) {
        this.id = builder.id;
        this.module = builder.module;
        this.semester = builder.semester;
        this.semesterType = builder.semesterType;
        this.year = builder.year;
    }

    public ModulePlan() {
    }

    public UUID getId() {
        return id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public int getSemester() { return semester; }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModulePlan that = (ModulePlan) o;

        if (semester != that.semester) return false;
        if (year != that.year) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(module, that.module)) return false;
        return semesterType == that.semesterType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (module != null ? module.hashCode() : 0);
        result = 31 * result + semester;
        result = 31 * result + (semesterType != null ? semesterType.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }

    public static class Builder {
        private UUID id;
        private Module module;
        private int semester;
        private SemesterType semesterType;
        private int year;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder module(Module module) {
            this.module = module;
            return this;
        }

        public Builder semester(int semester) {
            this.semester = semester;
            return this;
        }

        public Builder semesterType(SemesterType semesterType) {
            this.semesterType = semesterType;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public ModulePlan build() {
            return new ModulePlan(this);
        }
    }
}
