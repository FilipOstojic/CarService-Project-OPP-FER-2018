import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Model } from './model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class ModelService {

  constructor(private http: HttpClient) { }

  private modelURL = '/model';

  getModels(): Observable<Model[]> {
    return this.http.get<Model[]>(this.modelURL)
      .pipe(
        tap(_ => console.log('fetched models'))
      );
  }
}