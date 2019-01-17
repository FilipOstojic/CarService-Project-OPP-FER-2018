import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Car } from '../car';
import { CarService } from '../car.service';
import { LoginService } from '../login.service';
import { DatasharingService } from '../datasharing.service';
import { Model } from '../model';
import { ModelService } from '../model.service';
import { RegisterService } from '../register.service';
import { Observable } from 'rxjs';
import { LogoutService } from '../logout.service';
import { Router } from '@angular/router';


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
  editAccountForm: boolean = false;
  newCar: Car = {licensePlate:"", model:null, year:"", owner:null};
  isUser: boolean;

  constructor(
    private carService: CarService,
    private datasharingService : DatasharingService,
    private modelService : ModelService,
    private registerService : RegisterService,
    private router : Router, 
    private logoutService: LogoutService 
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

  closeEdit() {
    this.editAccountForm = false;
  }

  showEditAccount() {
    this.editAccountForm = true;
  }

  deleteAccount() {
    var result: Observable<User> = this.registerService.removeUser(this.owner);
    result.subscribe((prod) => {
      console.log("DELETE DONE");
    },
      error => {
        console.log("ERROR OCCURED")
    });
    this.logoutService.logout().subscribe((prod) => {
      console.log("LOGOUT DONE");
    },
      error => {
        console.log("ERROR OCCURED")
    });
    this.router.navigate(['/pocetna']);
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

  delCar(licensePlate: string) {
    console.log("called delete of " + licensePlate)
    this.carService.delCar(licensePlate).subscribe(car => {
      console.log(JSON.stringify(car));
      this.getCars();
    });
    this.getCars();
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

  editAccount(name: string, surname: string, email: string, oib: string, mobile: string, password: string, confPass: string): void {
    console.log("edit acc")
    let user: User = { "name": name, "surname": surname, "oib": oib, "email": email, "mobile": mobile, "password": password,"role":this.owner.role};
    this.registerService.editUser(user).subscribe((prod) => {
      console.log("EDIT DONE");
      this.owner = prod;
      this.closeEdit();
    },
      error => {
        console.log("ERROR OCCURED")
    });
  }
}