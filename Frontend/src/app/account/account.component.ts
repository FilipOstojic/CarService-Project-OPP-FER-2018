import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Car } from "../car";
import { CarService } from '../car.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  owner: User;
  cars: Car[] = [];
  addForm: boolean = false;
  newCar: Car = null;

  constructor(
    private carService: CarService
  ) { }

  ngOnInit() {
    this.owner = { name:"Ivo", surname:"Ivić", email:"ivic.ivo@gmail.com", mobile:"09566442251", oib:"12345678910", password:"454"}

    this.cars = [ {licensePlate:"ZG5959OP", model: "GLA", year: "2017" },
                  {licensePlate:"ZG5129KL", model: "SLS", year: "2014" },
                  {licensePlate:"ZD4559MN", model: "G", year: "2018" }
    ];
    
  }

  showForm() {
    this.addForm = true;
  }

  close() {
    this.addForm = false;
  }

  addCar(licensePlate: string, model: string, year: string) {
    this.newCar =  new Car(licensePlate, model, year);
    this.carService.addCar(this.newCar).subscribe( car => {
      console.log(car);
      this.getCars();
    });
    this.close();
  }

  getCars() {
    window.location.reload();
  }

}
