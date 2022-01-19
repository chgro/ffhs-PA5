package ch.ffhs.pa5.backend.service;

import ch.ffhs.pa5.backend.controller.model.AddModuleToStudyPlanRequest;
import ch.ffhs.pa5.backend.controller.model.NewStudyPlanRequest;
import ch.ffhs.pa5.backend.model.ModulePlan;
import ch.ffhs.pa5.backend.model.Specialisation;
import ch.ffhs.pa5.backend.model.StudyPlan;

import java.util.Optional;
import java.util.UUID;

public interface IStudyPlanService {
    Optional<StudyPlan> addNewStudyPlan(NewStudyPlanRequest studyPlanRequest);
    Optional<StudyPlan> getStudyPlanById(UUID studyPlanId);
    Optional<StudyPlan> getStudyPlanByName(String studyPlanName);
    Boolean studyPlanNameIsUnique(String name);
    boolean deleteStudyPlan(UUID studyPlanId);
    Optional<ModulePlan> addModuleToStudyPlan(UUID id, AddModuleToStudyPlanRequest module);
    Boolean checkIfStudyPlanContainsModule(UUID studyPlanId, int moduleId);
    boolean removeModuleFromStudyPlan(UUID studyPlanId, int moduleId);
    int getECTS(UUID studyPlanId);
    int getTotalRelevanceBySpecialisation(UUID studyPlanId, Specialisation specialisation);
}
