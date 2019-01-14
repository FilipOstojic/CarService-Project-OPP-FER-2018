import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { User } from './user';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  private getAppURL = 'http://192.168.93.1:8080/appointment/available';

  getAllAppointments() : Observable<string[]>{
    console.log("getAllAppointments CALLED");
    return this.http.get(this.getAppURL, httpOptions)
      .pipe(
        tap(_ => {
          console.log("ALL IS ALL");
        }),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log("JEBOTE MRTVI ERROR");
              throw error;
          }
      )
      );
  }

  getMechAppointments(email : string) : Observable<User>{
    console.log("getMechAppointments CALLED");
    return this.http.get(this.getAppURL+"/"+email,httpOptions)
      .pipe(
        tap(_ => console.log('MECH ALL')),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log("JEBOTE MRTVI ERROR");
              throw error;
          }
      )
      );
  }
}
