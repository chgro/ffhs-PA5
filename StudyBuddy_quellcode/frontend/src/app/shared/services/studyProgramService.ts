import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {map, publishReplay, refCount} from "rxjs/operators";
import {HttpWrapper} from "src/app/http.wrapper";

@Injectable()
export class StudyProgramService {
  constructor(
    private httpClient: HttpWrapper) {
  }

  public getAllStudyPrograms(): Observable<string[]> {
    return this.httpClient.get(`api/v1/studyPrograms`)
      .pipe(
        map(obj => <string[]>obj),
        publishReplay(),
        refCount());
  }
}
