import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Car } from "../car";
import { CarService } from '../car.service';
import { LoginService } from '../login.service';
import { Model } from '../model';
import { ModelService } from '../model.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  owner: User;
  cars: Car[] = [];
  models : Model[] = [];
  addForm: boolean = false;
  newCar: Car = null;

  constructor(
    private carService: CarService,
    private loginService: LoginService,
    private modelService : ModelService
  ) { }

  ngOnInit() {
    this.getModels();
    console.log(this.models.length)
  }

  getModels() {
    const models = this.modelService.getModels();
    models.subscribe((models) => {
      this.models = models;
    });
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
