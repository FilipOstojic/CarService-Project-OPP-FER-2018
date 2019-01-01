import { Injectable } from '@angular/core';
import {USERS} from './mock-users';
import {SERVICE} from './mock-services';
import {MECHANICS} from './mock-mechanics';


@Injectable({
  providedIn: 'root'
})
export class DataService {

  service = SERVICE;
  mechanic = MECHANICS;
  user = USERS;
  
  
  constructor() { }

  getUser() {
      return USERS[1];
  }

  getServices() {
      return this.service;
  }

  getMechanics() {
      return this.mechanic;
  }
}
