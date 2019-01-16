import { Component } from '@angular/core';
import { User } from './user';
import { DatasharingService } from './datasharing.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isCollapsed = true;
  loggedIn : User = { "name": "", "surname": "", "oib": "", "email": "", "mobile": "", "password": "", role:null};
  isLoggedIn : boolean;
  isUser : boolean;
  isMech : boolean;
  isAdmin : boolean;
  
  constructor(private datasharingService : DatasharingService) {
    this.datasharingService.loggedInUser.subscribe( value => {
      this.loggedIn = value;
    });
    this.datasharingService.isLoggedIn.subscribe( value => {
      this.isLoggedIn = value;
    })
    this.datasharingService.isUser.subscribe( value => {
      this.isUser = value;
    });
    this.datasharingService.isMech.subscribe( value => {
      this.isMech = value;
    });
    this.datasharingService.isAdmin.subscribe( value => {
      this.isAdmin = value;
    });
  }

  ngOnInit(){
  }

}
