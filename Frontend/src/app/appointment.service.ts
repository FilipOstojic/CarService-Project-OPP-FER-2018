import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Appointment } from './appointment';
import { AppointmentReal } from './appointment-real';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  constructor(private http: HttpClient) { }

  private getAppURL = '/appointment/available/';
  private addAppURL = '/appointment/';

  getAllAppointments(): Observable<string[]> {
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

  getMechAppointments(email: string): Observable<string[]> {
    console.log("getMechAppointments CALLED");
    return this.http.get(this.getAppURL + email, httpOptions)
      .pipe(
        tap(_ => console.log('MECH ALL')),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log("ERROR");
            throw error;
          }
        )
      );
  }

  getMechAppointmentsApp(email: string): Observable<Appointment[]> {
    console.log("getMechAppointmentsApp CALLED");
    return this.http.get(this.addAppURL + email, httpOptions)
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

  addAppointment(appointment: Appointment): Observable<any> {
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

  updateAppointment(appointment: AppointmentReal): Observable<any> {
    console.log(JSON.stringify(appointment));
    return this.http.post(this.addAppURL, appointment, httpOptions).pipe(
      tap(_ => console.log("appoinment updated")),
      catchError(
        (error: any, caught: Observable<any>) => {
          console.log("appointment not updated");
          throw error;
        }
      )
    );
  }

  getAppointment(id:number): Observable<AppointmentReal> {
    return this.http.get(this.addAppURL + "id/" + id, httpOptions).pipe(
      tap(_ => console.log("appoinment fetched")),
      catchError(
        (error: any, caught: Observable<any>) => {
          console.log("appointment not fetched");
          throw error;
        }
      )
    );
  }
}
