import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Mechanic } from './mechanic';
import { catchError, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class MechanicService {

  constructor(private http: HttpClient) { }

  private mechanicsURL = '/user/mech';
  private addMechURL = '/user/createMech';

  getMechanics(): Observable<Mechanic[]> {
    return this.http.get<Mechanic[]>(this.mechanicsURL);
  }

  addMechanic(mech:Mechanic): Observable<Mechanic> {
    return this.http.put<Mechanic>(this.addMechURL, mech, httpOptions).pipe(
      tap(_ => console.log('added user ' + mech.name)),
      catchError(
        (error: any, caught: Observable<any>) => {
          console.log(error);
          throw error;
        }
      )
    );
}
}
