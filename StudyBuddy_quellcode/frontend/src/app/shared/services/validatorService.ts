import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map, publishReplay, refCount } from "rxjs/operators";
import { HttpWrapper } from "src/app/http.wrapper";

@Injectable()
export class ValidatorService {
  constructor(
    private httpClient: HttpWrapper) {
  }

  public validateStudyPlan(name: string) : Observable<boolean> {
    return this.httpClient.get(`/api/v1/validator/validStudyPlan?studyPlanName=${name}`)
        .pipe(
            map(obj => <boolean>obj),
            publishReplay(),
            refCount());
  }
}
