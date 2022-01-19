import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map, publishReplay, refCount } from "rxjs/operators";
import { HttpWrapper } from "src/app/http.wrapper";

@Injectable()
export class SpecialisationService {

  specialisationsObserve: Observable<string[]>;

  constructor(
    private httpClient: HttpWrapper) {
      this.specialisationsObserve = this.httpClient
      .get(`api/v1/specialisations`)
      .pipe(
        map(obj => <string[]>obj),
        publishReplay(),
        refCount());
  }

  public getAllSpecialisations() : Observable<string[]> {
    return this.specialisationsObserve;
  }
}