import { Injectable } from '@angular/core';
import { Car } from './car';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

const httpOptions = { headers: new HttpHeaders({ 'Content-Type': 'application/json' }) }

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private carsUrl = 'http://192.168.29.1:8080/userVehicle';  // URL to web api

  constructor(
    private http: HttpClient) { }

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.carsUrl).pipe(catchError(this.handleError('getProfessors', [])));
  }

  addCar(car: Car): Observable<any> {
    return this.http.put(this.carsUrl, car, httpOptions).pipe(
      tap(_ => console.log("add professor " + car.licensePlate)),
      catchError(this.handleError<any>('addProfessor'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
