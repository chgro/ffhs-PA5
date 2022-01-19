package ch.ffhs.pa5.backend;

import ch.ffhs.pa5.backend.controller.model.AddModuleToStudyPlanRequest;
import ch.ffhs.pa5.backend.controller.model.NewStudyPlanRequest;
import ch.ffhs.pa5.backend.model.Module;
import ch.ffhs.pa5.backend.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SomeData {
    public final static List<StudyProgram> studyPrograms = Arrays.asList(new StudyProgram[1]);

    public final static Module module = new Module.Builder()
            .id(900)
            .moduleType(ModuleType.ELECTIVE)
            .name("NA: Netzwerk Analyse")
            .semesterPlan(List.of(
                    new SemesterPlan(
                            1,
                            2021,
                            SemesterType.AUTUMN)
            ))
            .relevance(Set.of(
                    new Relevance(
                            800,
                            Specialisation.DATASCIENCE,
                            5)))
            .build();

    public final static Module basicModule = new Module.Builder()
            .id(9056)
            .moduleType(ModuleType.BASIC)
            .name("BasisModul")
            .semesterPlan(List.of(
                    new SemesterPlan(
                            1,
                            2021,
                            SemesterType.AUTUMN)
            ))
            .relevance(Set.of(
                    new Relevance(
                            800,
                            Specialisation.DATASCIENCE,
                            5)))
            .build();

    public final static List<Module> modules = new ArrayList<Module>(
            List.of(
                    new Module.Builder()
                            .id(701)
                            .moduleType(ModuleType.ELECTIVE)
                            .name("NA: Netzwerk Analyse")
                            .relevance(Set.of(
                                    new Relevance(
                                            801,
                                            Specialisation.DATASCIENCE,
                                            2)))
                            .build(),
                    new Module.Builder()
                            .id(702)
                            .moduleType(ModuleType.IMMERSION)
                            .name("INSich: Internet Sicherheit")
                            .relevance(Set.of(
                                    new Relevance(
                                            802,
                                            Specialisation.SECURITY,
                                            2),
                                    new Relevance(
                                            803,
                                            Specialisation.DATASCIENCE,
                                            3))
                            )
                            .build()));


    public final static ModulePlan modulePlan = new ModulePlan.Builder()
            .id(UUID.fromString("c2c85358-55d0-11ec-bf63-0242ac130002"))
            .module(new Module.Builder()
                    .id(900)
                    .moduleType(ModuleType.ELECTIVE)
                    .name("NA: Netzwerk Analyse")
                    .ects(5)
                    .relevance(Set.of(
                            new Relevance(
                                    900,
                                    Specialisation.DATASCIENCE,
                                    5),
                            new Relevance(
                                    901,
                                    Specialisation.ENTERPRISECOMPUTING,
                                    3)))
                    .semesterPlan(
                            List.of(
                                    new SemesterPlan(
                                            1,
                                            2021,
                                            SemesterType.AUTUMN
                                    )
                            )
                    )
                    .build())
            .semester(6)
            .semesterType(SemesterType.AUTUMN)
            .year(2021)
            .build();

    public final static StudyPlan studyPlanWithoutModules = new StudyPlan.Builder()
            .id(UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002"))
            .name("pa5")
            .date(new Date())
            .studyProgram(StudyProgram.PASSARELLE)
            .build();

    public final static StudyPlan studyPlan = new StudyPlan.Builder()
            .id(UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002"))
            .name("pa5")
            .date(new Date())
            .modulePlans(List.of(modulePlan))
            .studyProgram(StudyProgram.PASSARELLE)
            .build();

    public final static NewStudyPlanRequest newStudyPlanRequest = new NewStudyPlanRequest(
            "pa5",
            new Date(),
            StudyProgram.PASSARELLE);

    public final static NewStudyPlanRequest falseStudyPlanRequest = new NewStudyPlanRequest(
            StringUtils.repeat('a', 256),
            new Date(),
            StudyProgram.PASSARELLE);

    public final static AddModuleToStudyPlanRequest moduleToStudyPlanRequest = new AddModuleToStudyPlanRequest(900, Specialisation.DATASCIENCE, 5, SemesterType.AUTUMN, 2021);

    public final static ModulePlan responseOfAddModuleToStudyPlan = new ModulePlan.Builder()
            .id(UUID.fromString("9597b5de-515e-11ec-bf63-0242ac130002"))
            .semester(5)
            .semesterType(SemesterType.AUTUMN)
            .year(2021)
            .module(module)
            .build();
}
