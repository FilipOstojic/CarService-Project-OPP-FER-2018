import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Car } from "../car";
import { CarService } from '../car.service';
import { LoginService } from '../login.service';

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
    private carService: CarService,
    private loginService: LoginService
  ) { }

  ngOnInit() {
    
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
    });
    this.close();
  }

}
