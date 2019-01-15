import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Car } from '../car';
import { CarService } from '../car.service';
import { LoginService } from '../login.service';
import { DatasharingService } from '../datasharing.service';
import { Model } from '../model';
import { ModelService } from '../model.service';


@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  owner: User = { "name": "", "surname": "", "oib": "", "email": "", "mobile": "", "password": "", role:null};
  cars: Car[] = [];
  models : Model[] = [];
  addForm: boolean = false;
  newCar: Car = {licensePlate:"", model:null, year:"", owner:null};
  isUser: boolean;

  constructor(
    private carService: CarService,
    private datasharingService : DatasharingService,
    private modelService : ModelService
  ) {
    this.datasharingService.loggedInUser.subscribe(value => {
      this.owner = value;
    });
    this.datasharingService.isUser.subscribe( value => {
      this.isUser = value;
    });
  }

  ngOnInit() {
    this.getCars();
    this.getModels();
  }

  showForm() {
    this.addForm = true;
  }

  close() {
    this.addForm = false;
  }

  addCar(licensePlate: string, id:number, year: string, email:string) {
    console.log(licensePlate +" " + id + " " +year )
    this.newCar = new Car(licensePlate, new Model(id), year, email);
    console.log(this.newCar.licensePlate);
    this.carService.addCar(this.newCar).subscribe(car => {
      console.log(car);
      this.getCars();
    });
    this.close();
  }

  getCars() {
    console.log("get cars");
    this.carService.getCars(this.owner.email).subscribe(cars => {
      this.cars = cars;
    });    
  }

  getModels() {
    const models = this.modelService.getModels();
    models.subscribe((models) => {
      this.models = models;
    });
  }
}