import { Injectable } from '@angular/core';
import { Info } from './info';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class InfoService {

  constructor(private http: HttpClient) { }

  private infoURL = 'http://192.168.1.9:8080/autoservice';

  
  getInfo(): Observable<Info> {
    return this.http.get<Info>(this.infoURL)
      .pipe(
        tap(_ => console.log('fetched info'))
      );
  }
}
