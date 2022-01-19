package ch.ffhs.pa5.backend.controller.model;

import ch.ffhs.pa5.backend.model.SemesterType;
import ch.ffhs.pa5.backend.model.Specialisation;

/**
 * Diese Klasse dient als Hilfsklasse zum Hinzufügen eines Modules zu einem Studienplan.
 */
public class AddModuleToStudyPlanRequest{
    int moduleId;
    Specialisation specialisation;
    int semester;
    SemesterType semesterType;
    int year;

    public AddModuleToStudyPlanRequest(){}

    public AddModuleToStudyPlanRequest(int moduleId, Specialisation specialisation, int semester, SemesterType semesterType, int year) {
        this.moduleId = moduleId;
        this.specialisation = specialisation;
        this.semester = semester;
        this.semesterType = semesterType;
        this.year = year;
    }

    public int getModuleId() {
        return moduleId;
    }

    public Specialisation getSpecialisation() {
        return specialisation;
    }

    public int getSemester() {
        return semester;
    }

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

        AddModuleToStudyPlanRequest that = (AddModuleToStudyPlanRequest) o;

        if (moduleId != that.moduleId) return false;
        if (semester != that.semester) return false;
        if (year != that.year) return false;
        if (specialisation != that.specialisation) return false;
        return semesterType == that.semesterType;
    }

    @Override
    public int hashCode() {
        int result = moduleId;
        result = 31 * result + (specialisation != null ? specialisation.hashCode() : 0);
        result = 31 * result + semester;
        result = 31 * result + (semesterType != null ? semesterType.hashCode() : 0);
        result = 31 * result + year;
        return result;
    }
}