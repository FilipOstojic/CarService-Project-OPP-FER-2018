import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient ) { }

  private usersURL = '/user/createUser';

  addUser(user: User): Observable<User> {
    console.log("USER ADD CALLED");
    console.log(JSON.stringify(user));
    return this.http.put(this.usersURL, user, httpOptions)
      .pipe(
        tap(_ => console.log('added user' + user.name)),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log(error);
              throw error;
          }
      )
      );
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersURL)
      .pipe(
        tap(_ => console.log('fetched users'))
      );
  }

  updateUser(user: User): Observable<any> {
    console.log("SERVICE UPDATE CALLED");
   return this.http.post(this.usersURL, user, httpOptions)
     .pipe(
       tap(_ => console.log('updated user' + user.name)),
       catchError(
         (error: any, caught: Observable<any>) => {
             throw error;
         }
     )
     );
 }
}
