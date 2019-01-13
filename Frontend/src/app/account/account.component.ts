import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Car } from "../car";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  user: User;
  cars: Car[] = [];

  constructor() { }

  ngOnInit() {
    this.cars = [ {licensePlate:"ZG5959OP", model: "GLA", year: "2017" },
                  {licensePlate:"ZG5129KL", model: "SLS", year: "2014" },
                  {licensePlate:"ZD4559MN", model: "G", year: "2018" }
                 ];
    this.user = { name:"Ivo", surname:"Ivić", email:"ivic.ivo@gmail.com", mobile:"09566442251", oib:"12345678910", password:"454"}
  }


}
