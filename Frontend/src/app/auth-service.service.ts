import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  person: string ='serviser';

  constructor() {
    
  }

  logout(){

    this.person = 'guest';
  }
  
}
