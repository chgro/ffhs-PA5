
export interface Module{
    id: number;
    ects: number;
    name: string;
    moduleType: string;
    relevance: Relevance[];
    semesterPlan: SemesterPlan[];
}

export interface Relevance{
    id: number;
    specialisation: string;
    relevancePoint: number;
}

export interface SemesterPlan{
    id: number;
    year: number;
    semesterType: string;
}

export class NewStudyPlan{
    constructor(
        public name: string,
        public date: Date,
        public studyProgram: string) {
    }
}

export interface StudyPlan {
    id: string;
    name: string;
    date: Date;
    modulePlans: ModulePlan[];
    specialisation: string;
    semesterType: number;
    studyProgram: string;
}

export interface ModulePlan{
    id: string;
    module: Module;
    semester: number;
    semesterType: number;
    year: number;
}

export class NewModulePlan{
    constructor(
        public moduleId: number,
        public specialisation: string,
        public semester: number,
        public semesterType: string,
        public year: number) {
    }
}
