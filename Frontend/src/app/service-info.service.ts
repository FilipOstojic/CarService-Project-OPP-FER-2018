import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AutoserviceInfo } from './autoservice-info';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class ServiceInfoService {

  constructor(private http: HttpClient) { }

  private autoserviceInfoURL = '/autoservice';
  
  getInfo() : Observable<AutoserviceInfo> {
    return this.http.get<AutoserviceInfo>(this.autoserviceInfoURL, httpOptions);
  }

  updateInfo (autoservice : AutoserviceInfo) {
    return this.http.post<AutoserviceInfo>(this.autoserviceInfoURL, autoservice, httpOptions);
  }
}
