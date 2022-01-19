package ch.ffhs.pa5.backend.controller.model;

import ch.ffhs.pa5.backend.model.StudyProgram;
import java.util.Date;
import java.util.Objects;

/**
 * Diese Klasse dient als Hilfsklasse für die Anfrage zur Erstellung eines neuen Studienplans.
 */
public class NewStudyPlanRequest{
    String name;
    Date date;
    StudyProgram studyProgram;

    public NewStudyPlanRequest(){}

    public NewStudyPlanRequest(String name, Date date, StudyProgram studyProgram) {
        this.name = name;
        this.date = date;
        this.studyProgram = studyProgram;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewStudyPlanRequest that = (NewStudyPlanRequest) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(date, that.date)) return false;
        return studyProgram == that.studyProgram;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (studyProgram != null ? studyProgram.hashCode() : 0);
        return result;
    }
}