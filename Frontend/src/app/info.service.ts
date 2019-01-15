import { Injectable } from '@angular/core';
import { Info } from './info';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class InfoService {

  constructor(private http: HttpClient) { }

  private infoURL = '/autoservice';

  
  getInfo(): Observable<Info> {
    return this.http.get<Info>(this.infoURL)
      .pipe(
        tap(_ => console.log('fetched info'))
      );
  }
}
