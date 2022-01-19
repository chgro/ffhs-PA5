import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
  })
export class HttpWrapper {

    private token: String;

    constructor(
        private httpClient: HttpClient) {
    }

    private getBaseUrl() {
        return "http://localhost:8080/"
    }

    public request(url: string, body: any, o: Observable<ArrayBuffer>): Observable<any> {
        return o;
    }

    public get(url: string): Observable<any> {
        return this.request(
            url,
            undefined,
            <any>this.httpClient.get(this.getBaseUrl() + url, this.getOptions()));
    }

    private getOptions(responseType?: string): any {
        const options: any = {
            headers: new HttpHeaders()
                // .set("Authorization", "Bearer " + this.token)
                .set('Content-Type', 'application/json; charset=utf-8')
        };
        if (responseType) {
            options.responseType = responseType;
        }

        return options;
    }

    public post(url: string, data: any): Observable<any> {
        return this.request(
            url,
            data,
            <any>this.httpClient.post(this.getBaseUrl() + url, data, this.getOptions("text")));
    }

    public delete(url: string): Observable<any> {
        return this.request(
            url,
            undefined,
            <any>this.httpClient.delete(this.getBaseUrl() + url, this.getOptions("text")));
    }
}
