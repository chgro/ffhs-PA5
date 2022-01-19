package ch.ffhs.pa5.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Studyplan bildet die individuelle Planung eines Users ab. Die Informationen, welcher ein User über das UI eingibt, werden in dieser Klasse abgebildet.
 */
@Entity
@Table(name = "studyplan")
public class StudyPlan {
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition="CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Zurich")
    private Date date;

    @Nullable
    @Column(name = "modulePlan")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<ModulePlan> modulePlans;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private StudyProgram studyProgram;

    public StudyPlan() {
    }

    public StudyPlan(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.date = builder.date;
        this.modulePlans = builder.modulePlans;
        this.studyProgram = builder.studyProgram;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    @Nullable
    public List<ModulePlan> getModulePlans() {
        return modulePlans;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyPlan studyPlan = (StudyPlan) o;

        if (!Objects.equals(id, studyPlan.id)) return false;
        if (!Objects.equals(name, studyPlan.name)) return false;
        if (!Objects.equals(date, studyPlan.date)) return false;
        if (!Objects.equals(modulePlans, studyPlan.modulePlans))
            return false;
        return studyProgram == studyPlan.studyProgram;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (modulePlans != null ? modulePlans.hashCode() : 0);
        result = 31 * result + (studyProgram != null ? studyProgram.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private Date date;
        private List<ModulePlan> modulePlans;
        private StudyProgram studyProgram;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder modulePlans(List<ModulePlan> modulePlans) {
            this.modulePlans = modulePlans;
            return this;
        }

        public Builder studyProgram(StudyProgram studyProgram) {
            this.studyProgram = studyProgram;
            return this;
        }

        public StudyPlan build() {
            return new StudyPlan(this);
        }
    }
}
