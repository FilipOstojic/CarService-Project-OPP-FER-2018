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
export class LoginService {

  constructor(private http: HttpClient) { }
  name : string = "";

  private loginURL = 'http://192.168.93.1:8080/login';

  private loggedInURL = 'http://192.168.93.1:8080/user/loggedIn';

  login(username:string,password:string) :  Observable<any>{
    console.log("LOGIN CALLED");
    return this.http.post(this.loginURL, {username,password}, httpOptions)
      .pipe(
        tap(_ => {
          console.log('logging in ' + username);
          this.name=username; 
        }),
        catchError(
          (error: any, caught: Observable<any>) => {
              throw error;
          }
      )
      );
  }

  getUsername():string{
    return this.name;
  }

  getLoggedIn() : Observable<User>{
    console.log("GET LOGGED IN CALLED");
    return this.http.get(this.loggedInURL)
      .pipe(
        tap(_ => console.log('fetched user')),
        catchError(
          (error: any, caught: Observable<any>) => {
              throw error;
          }
      )
      );
  }
}
