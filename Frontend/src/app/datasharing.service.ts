import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class DatasharingService {
  public loggedInUser: BehaviorSubject<User> = new BehaviorSubject<User>({ "name": "", "surname": "", "oib": "", "email": "", "mobile": "", "password": "", role:null});
  public isMech: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isUser: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isAdmin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


  constructor(){
    const currentUser = sessionStorage.getItem("currentUser");
    if (currentUser != null) {
      const user = JSON.parse(currentUser);
      this.loggedInUser.next(user);
      this.determineRole(user);
      this.isLoggedIn.next(true);
    }
  }

  logout(){
    this.loggedInUser.next({ "name": "", "surname": "", "oib": "", "email": "", "mobile": "", "password": "", role:null});
    this.isMech.next(false);
    this.isUser.next(false);
    this.isAdmin.next(false);
    this.isLoggedIn.next(false);
  }

  login(user:User){
    this.loggedInUser.next(user);
    this.determineRole(user);
    this.isLoggedIn.next(true);
  }

  determineRole(user:User){
    if (user.role.name == "MECH") {
      this.isMech.next(true);
      this.isUser.next(false);
      this.isAdmin.next(false);
    } else if (user.role.name == "ADMIN") {
      this.isMech.next(false);
      this.isUser.next(false);
      this.isAdmin.next(true);
    } else if (user.role.name == "USER") {
      this.isMech.next(false);
      this.isUser.next(true);
      this.isAdmin.next(false);
    }
  }
}
