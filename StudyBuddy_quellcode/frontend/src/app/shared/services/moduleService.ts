import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map, refCount, publishReplay } from "rxjs/operators";
import { HttpWrapper } from "src/app/http.wrapper";
import { Module } from "../backendTypes";

// This class is a helper service for communicating with the backend
@Injectable()
export class ModuleService {
    modulesObserve: Observable<Module[]>;

    constructor(
        private httpClient: HttpWrapper) {
        this.modulesObserve = this.httpClient
            .get(`api/v1/modules`)
            .pipe(
                map(obj => <Module[]>obj),
                publishReplay(),
                refCount());
    }

    getModulesByFilter(specialisation: String, year = 0, semesterType = ""): Observable<Module[]> {
        return this.modulesObserve = this.httpClient
            .get(`api/v1/modules?specialisation=${specialisation}&year=${year}&semesterType=${semesterType}`)
            .pipe(
                map(obj => <Module[]>obj),
                publishReplay(),
                refCount());
    }
}
