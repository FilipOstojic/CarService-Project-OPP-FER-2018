import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { User } from './user';
import { AppComponent } from './app.component';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  constructor(private http: HttpClient) { }
  name : string = "";
  user : User;

  private logoutURL = 'http://192.168.93.1:8080/logout';
  private logedIn = 'http://192.168.93.1:8080/user/loggedIn'

  logout() : Observable<any>{
    console.log("logout service called");
    return this.http.post(this.logoutURL,httpOptions)
    .pipe(
      tap(_ => {
        console.log('loged out');
      }),
      catchError(
        (error: any, caught: Observable<any>) => {
          console.log(error);
            throw error;
        }
    )
    );
  }

  getLoggedIn() : Observable<User>{
    return this.http.get<User>(this.logedIn);
  }

}
