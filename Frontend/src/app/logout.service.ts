import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { DatasharingService } from './datasharing.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class LogoutService {

  private logoutURL = '/logout';

  constructor(private http: HttpClient, private datasharingService : DatasharingService) { }

  logout(){
    console.log("logout service called");
    return this.http.post(this.logoutURL, httpOptions)
    .pipe(
      tap(_ => {
        this.datasharingService.logout();
        sessionStorage.removeItem("currentUser");
        console.log('logged out');
      }),
      catchError(
        (error: any, caught: Observable<any>) => {
          console.log(error);
            throw error;
        }
    )
    );
  }
}
