import { Injectable } from '@angular/core';
import { Service } from './service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private http: HttpClient) { }

  private servicesURL = '/service';

  getServices(): Observable<Service[]> {
    return this.http.get<Service[]>(this.servicesURL)
      .pipe(
        tap(_ => console.log('fetched services'))
      );
  }
}
