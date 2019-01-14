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

  private carsUrl = 'http://10.129.137.63:8080/userVehicle';  // URL to web api

  constructor(
    private http: HttpClient) { }

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.carsUrl).pipe(catchError(this.handleError('getCars', [])));
  }

  addCar(car: Car): Observable<any> {
    console.log("service");
    return this.http.put(this.carsUrl, car, httpOptions).pipe(
      tap(_ => console.log("add car " + car.licensePlate)),
      catchError(this.handleError<any>('addCar'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
