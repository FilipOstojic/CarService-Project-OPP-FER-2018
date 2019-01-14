import { Injectable } from '@angular/core';
import { Service } from './service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Model } from './model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class ModelService {

  constructor(private http: HttpClient) { }

  private modelURL = 'http://192.168.1.3:8080/model';

  getModels(): Observable<Model[]> {
    return this.http.get<Model[]>(this.modelURL)
      .pipe(
        tap(_ => console.log('fetched models'))
      );
  }
}