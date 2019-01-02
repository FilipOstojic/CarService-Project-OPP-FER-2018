import { Injectable } from '@angular/core';
import {USERS} from './mock-users';
import {SERVICE} from './mock-services';
import {MECHANICS} from './mock-mechanics';
import {AuthServiceService} from './auth-service.service';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  service = SERVICE;
  mechanic = MECHANICS;
  user = USERS;
  
  
  
  constructor( private auth: AuthServiceService) { }

  getUser() {

    if (this.auth.person == 'guest') {
        return USERS[0]; }
      
    if (this.auth.person == 'admin') {
        return USERS[1]; }

    if (this.auth.person == 'serviser') {
        return USERS[2]; }
        
    if (this.auth.person == 'korisnik') {
        return USERS[3]; }
      
  }

  getServices() {
      return this.service;
  }

  getMechanics() {
      return this.mechanic;
  }
}
