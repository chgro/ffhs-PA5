package ch.ffhs.pa5.backend.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Die Klasse beinhaltet die Informationen zu den einzelnen Modulen wie den Namen und die ECTS-Punkte.
 * Die Daten werden mittels Spring Boot beim Starten initialisiert (data.sql).
 */
@Entity
@Table(name = "module")
public class Module {
    @Id
    @Column(name="id", updatable = false, nullable = false)
    private int id;

    @NotNull
    private int ects;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ModuleType moduleType;

    @Column(name = "relevance")
    @Nullable
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Relevance> relevance;

    @Column(name = "semesterPlan")
    @Nullable
    @ManyToMany(cascade = CascadeType.ALL)
    private List<SemesterPlan> semesterPlan;

    public Module() {
    }

    public Module(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.relevance = builder.relevance;
        this.moduleType = builder.moduleType;
        this.ects = builder.ects;
        this.semesterPlan = builder.semesterPlan;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public Set<Relevance> getRelevance() {
        return relevance;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    @Nullable
    public int getEcts() { return ects; }

    @Nullable
    public List<SemesterPlan> getSemesterPlan() {
        return semesterPlan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        if (id != module.id) return false;
        if (!Objects.equals(name, module.name)) return false;
        if (!Objects.equals(relevance, module.relevance)) return false;
        return moduleType == module.moduleType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (relevance != null ? relevance.hashCode() : 0);
        result = 31 * result + (moduleType != null ? moduleType.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private int id;
        private String name;
        private Set<Relevance> relevance;
        private ModuleType moduleType;
        private int ects;
        private List<SemesterPlan> semesterPlan;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder relevance(Set<Relevance> relevance){
            this.relevance = relevance;
            return this;
        }

        public Builder moduleType(ModuleType moduleType){
            this.moduleType = moduleType;
            return this;
        }

        public Builder ects(int ects){
            this.ects = ects;
            return this;
        }

        public Builder semesterPlan(List<SemesterPlan> semesterPlan){
            this.semesterPlan = semesterPlan;
            return this;
        }

        public Module build() {
            return new Module(this);
        }
    }
}