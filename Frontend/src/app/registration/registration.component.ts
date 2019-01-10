import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { NgForm } from '@angular/forms';
import { User } from '../user';
import { RegisterService } from '../register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  error : boolean = false;
  //addUser(name.value,surname.value,email.value,oib.value,cellPhone.value,password.value)

  constructor(private registerService: RegisterService, private router: Router) { }

  ngOnInit() {
  }

  addUser(name: string, surname: string, email: string, oib: string, mobile: string, password: string, confPass: string): void {
    let user: User = { "name": name, "surname": surname, "oib": oib, "email": email, "mobile": mobile, "password": password };
    var result: Observable<User> = this.registerService.addUser(user);
    if (user.password === confPass) {
      result.subscribe((prod) => {
        console.log("ADD DONE");
        this.router.navigate(['/pocetna']);
      },
        error => {
          console.log("ERROR OCCURED")
        });
    }else{
      this.error==!this.error;
    }
  }

}
