import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user';
import { DatasharingService } from './datasharing.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private datasharingService : DatasharingService) { }
  
  private loginURL = '/login';
  private loggedInURL = '/user/loggedIn';

  login(username:string,password:string) :  Observable<any>{
    console.log("LOGIN CALLED");
    return this.http.post(this.loginURL, {username,password}, httpOptions)
      .pipe(
        tap(_ => {
          this.getLoggedIn().subscribe(value => {
            this.datasharingService.login(value);
            sessionStorage.setItem("currentUser", JSON.stringify(value));
          });   
          console.log('logging in ' + username);
        }),
        catchError(
          (error: any, caught: Observable<any>) => {
              throw error;
          }
      )
      );
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
