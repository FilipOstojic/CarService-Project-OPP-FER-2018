import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user';
import { Appointment } from './appointment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  private getAppURL = '/appointment/available';
  private addAppURL = '/appointment/';

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

  addAppointment(appointment:Appointment): Observable<any> {
    console.log(JSON.stringify(appointment));
    return this.http.put(this.addAppURL, appointment, httpOptions).pipe(
      tap(_ => console.log("appoinment added")),
      catchError(
        (error: any, caught: Observable<any>) => {
          console.log("appoinment nije dodan");
            throw error;
        }
    )
    );
  }
}
