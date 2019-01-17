import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  private createUserURL = '/user/createUser';
  private userURL = '/user';

  addUser(user: User): Observable<User> {
    console.log("USER ADD CALLED");
    console.log(JSON.stringify(user));
    return this.http.put(this.createUserURL, user, httpOptions)
      .pipe(
        tap(_ => console.log('added user ' + user.name)),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log(error);
            throw error;
          }
        )
      );
  }

  editUser(user: User): Observable<User> {
    console.log("service: edit user");
    console.log(JSON.stringify(user));
    return this.http.post(this.userURL, user, httpOptions)
      .pipe(
        tap(_ => console.log('edited user ' + user.name)),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log(error);
            throw error;
          }
        )
      );
  }

  removeUser(user: User): Observable<User> {
    console.log("REMOVE ADD CALLED");
    console.log(JSON.stringify(user));

    return this.http.delete(this.userURL + "/" + user.email, httpOptions)
      .pipe(
        tap(_ => console.log('removed user ' + user.name)),
        catchError(
          (error: any, caught: Observable<any>) => {
            console.log("nije dobro");
            throw error;
          }
        )
      );
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userURL + "/user")
      .pipe(
        tap(_ => console.log('fetched users'))
      );
  }
}
