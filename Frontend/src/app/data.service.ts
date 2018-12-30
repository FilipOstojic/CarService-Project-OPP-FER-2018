import { Injectable } from '@angular/core';
import {USERS} from './mock-users';
import {SERVICE} from './mock-services';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  service = SERVICE;
  
  constructor() { }

  getUser() {
      return USERS[0];
  }

  getServices() {
      return this.service;
  }
}
