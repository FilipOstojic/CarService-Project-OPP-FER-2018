import { Injectable } from '@angular/core';
import { Service } from './service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private http: HttpClient) { }

  private servicesURL = 'http://10.129.141.183:8080/service';

  getServices(): Observable<Service[]> {
    return this.http.get<Service[]>(this.servicesURL)
      .pipe(
        tap(_ => console.log('fetched services'))
      );
  }
}
