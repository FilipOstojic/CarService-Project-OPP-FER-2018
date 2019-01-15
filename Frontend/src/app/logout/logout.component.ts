import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { LogoutService } from '../logout.service';
import { Observable } from 'rxjs';

@Component({
  template:''
})
export class LogoutComponent implements OnInit {

  constructor( private logoutService: LogoutService, private router: Router) {
   }

  ngOnInit() {
    console.log("LOGOUT CALLED");
    this.logout();
  }

  logout(){
    var result: Observable<any> = this.logoutService.logout();
    result.subscribe((ccc) => {
      console.log("LOGOUT DONE");
      this.router.navigate(['/pocetna']);
    },
      error => {
        console.log("ERROR OCCURED")
      }
    );
  }

}
