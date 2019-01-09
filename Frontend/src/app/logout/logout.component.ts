import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthServiceService} from '../auth-service.service'

@Component({
  template:''
})
export class LogoutComponent implements OnInit {

  constructor( private auth: AuthServiceService, private router: Router) {

    this.auth = auth;
    this.router = router;
   }

  ngOnInit() {
    this.auth.logout();
    this.router.navigate(['pocetna']);
  }

}
