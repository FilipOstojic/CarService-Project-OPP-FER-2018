import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { NgForm } from '@angular/forms';
import { User } from '../user';
import { RegisterService } from '../register.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  users : User[] = [];
  //addUser(name.value,surname.value,email.value,oib.value,cellPhone.value,password.value)

  constructor(private registerService : RegisterService) { }

  ngOnInit() {
  }

  
  addUser(name:string,surname:string,email:string,oib:string,mobile:string,password:string): void {
    let user :User ={"name":name,"surname":surname,"oib":oib,"email":email,"mobile":mobile, "password":password};
    console.log(user.name);
  }

  consolLog(){
    console.log("AAAAAAAA")
  }

}
