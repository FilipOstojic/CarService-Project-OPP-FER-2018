import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthServiceService} from '../auth-service.service'
import { LogoutService } from '../logout.service';

@Component({
  template:''
})
export class LogoutComponent implements OnInit {

  constructor( private logoutService: LogoutService, private router: Router) {
   }

  ngOnInit() {
    console.log("LOGOUT CALLED");
    this.logoutService.logout();
    this.router.navigate(['pocetna']);
  }

}
