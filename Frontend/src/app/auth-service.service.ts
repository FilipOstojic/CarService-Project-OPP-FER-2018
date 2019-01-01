import { Injectable } from '@angular/core';
import { AppComponent} from './app.component';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  person: string ='guest';

  constructor( private app: AppComponent) {
    
    this.app = app;
  }

  logout(){

   
  }
}
