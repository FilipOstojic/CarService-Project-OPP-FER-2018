import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { LoginService } from '../login.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private router: Router,private loginService : LoginService) { }

  ngOnInit() {
  }

  login(email:string,password:string){
    var result: Observable<any> = this.loginService.login(email,password);
    result.subscribe((ccc) => {
      console.log("LOGIN DONE");
      this.router.navigate(['/pocetna']);
    },
      error => {
        console.log("ERROR OCCURED")
      }
    );
  }

}
