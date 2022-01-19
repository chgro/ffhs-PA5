import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { map, publishReplay, refCount } from "rxjs/operators";
import { HttpWrapper } from "src/app/http.wrapper";
import { ModulePlan, NewModulePlan, NewStudyPlan, StudyPlan } from "../backendTypes";

@Injectable()
export class StudyPlanService {

    constructor(
        private httpClient: HttpWrapper,
        private http: HttpClient) {
    }

    public getStudyPlanById(id: string): Observable<StudyPlan> {
        return this.httpClient.get(`api/v1/studyPlans?studyPlanId=${id}`)
            .pipe(
                map(obj => <StudyPlan>obj),
                publishReplay(),
                refCount());
    }

    public getStudyPlanByName(name: string): Observable<StudyPlan> {
        return this.httpClient.get(`api/v1/studyPlans?studyPlanName=${name}`)
            .pipe(
                map(obj => <StudyPlan>obj),
                publishReplay(),
                refCount());
    }

    public addNewStudyPlan(operationData: NewStudyPlan): Observable<StudyPlan> {
        console.log("send")
        return this.httpClient.post(`api/v1/studyPlans`, operationData)
            .pipe(
                map(resultString => JSON.parse(resultString)));
    }

    public addModuleToStudyPlan(id: string, operationData: NewModulePlan): Promise<ModulePlan> {
        return this.httpClient.post(`api/v1/studyPlans/${id}/modules`, operationData)
            .pipe(
                map(resultString => JSON.parse(resultString)))
            .toPromise()
            .catch((err: HttpErrorResponse) => of(<string>err.error));
    }

    public canAddModuleToStudyPlan(id: string, moduleId: number): Observable<boolean> {
        return this.httpClient.get(`api/v1/validator/canAdd/studyPlan/${id}/module/${moduleId}`)
            .pipe(
                map(obj => <boolean>obj),
                publishReplay(),
                refCount());
    }

    public deleteStudyPlan(id: string): Promise<any> {
        return this.httpClient.delete(`api/v1/studyPlans/${id}`)
            .pipe(
                map(resultString => JSON.parse(resultString)))
            .toPromise()
            .catch((err: HttpErrorResponse) => of(<string>err.error));
    }

    public removeModuleFromStudyPlan(id: string, moduleId: number): Promise<any> {
        return this.httpClient.delete(`api/v1/studyPlans/${id}/modules/${moduleId}`)
            .pipe(
                map(resultString => JSON.parse(resultString)))
            .toPromise()
            .catch((err: HttpErrorResponse) => of(<string>err.error));
    }

    public getTotalEcts(id: string): Observable<number> {
        return this.httpClient.get(`api/v1/studyPlans/${id}/modules/ects`)
            .pipe(
                map(obj => <number>obj),
                publishReplay(),
                refCount());
    }

    public getTotalRelevanceBySpecialisation(id: string, specialisation: string): Observable<number> {
        return this.httpClient.get(`api/v1/studyPlans/${id}/specialisations/${specialisation}`)
            .pipe(
                map(obj => <number>obj),
                publishReplay(),
                refCount());
    }
}
