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
  newCar: Car = {licensePlate:"", model:"", year:""};

  constructor(
    private carService: CarService,
    private loginService: LoginService
  ) { }

  ngOnInit() {
    /** this.loginService.getLoggedIn().subscribe((value) => {
      this.owner = value;
    }); */
    this.owner = {email:"njn", name:"jjn", surname:"mkk", mobile:"8845", oib:"877", password:"88", cars:null};
    this.cars = this.owner.cars;
  }

  showForm() {
    this.addForm = true;
  }

  close() {
    this.addForm = false;
  }

  addCar(licensePlate: string, model: string, year: string) {
    console.log(licensePlate +" " + model + " " +year )
    this.newCar = new Car(licensePlate, model, year);
    console.log(this.newCar.licensePlate);
    this.carService.addCar(this.newCar).subscribe(car => {
      console.log(car);
      this.getCars();
    });
    this.close();
  }

  getCars() {
    console.log("get cars");
    this.carService.getCars().subscribe(cars => {
      this.cars = cars;
    });
  }

}
