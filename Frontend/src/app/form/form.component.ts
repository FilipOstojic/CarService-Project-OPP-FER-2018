import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../mechanic';
import {Router} from '@angular/router';
import { AppointmentService } from '../appointment.service';
import { ServiceService } from '../service.service';
import { Service } from '../service';
import { User } from '../user';
import { Car } from '../car';
import { DatasharingService } from '../datasharing.service';
import { CarService } from '../car.service';
import { MechanicService } from '../mechanic.service';
import { Appointment } from '../appointment';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  cars: Car[] = [];
  owner: User = { "name": "", "surname": "", "oib": "", "email": "", "mobile": "", "password": "", role:null};
  mechanics:Mechanic[] = [];
  services: Service[] = [];
  appointments : string[] = [];
  clicked=false;
  mechanic=false;
  appointment=false;
  mechEmail : string = "null";

  constructor(
    private router : Router, 
    private carService: CarService, 
    private datasharingService : DatasharingService, 
    private appointmentService : AppointmentService, 
    private servicesService : ServiceService,
    private mechanicService : MechanicService
  ) {
    this.datasharingService.loggedInUser.subscribe(value => {
      this.owner = value;
    });
  }

  ngOnInit() {
    this.getServices();
    this.getCars();
  }

  getMechanics() {
    const mechanics = this.mechanicService.getMechanics();
    mechanics.subscribe((mechanics) => {
      this.mechanics = mechanics;
    });
  }

  getAppointments() {
    const appointments = this.appointmentService.getAllAppointments();
    appointments.subscribe((appointments) => {
      this.appointments = appointments;
    });
  }

  getMechAppointments(email:string) {
    const appointments = this.appointmentService.getMechAppointments(email);
    this.mechEmail = email;
    appointments.subscribe((appointments) => {
      this.appointments = appointments;
    });
  }

  getServices() {
    const services = this.servicesService.getServices();
    services.subscribe((services) => {
      this.services = services;
    });
  }

  getCars() {
    this.carService.getCars(this.owner.email).subscribe(cars => {
      this.cars = cars;
    });    
  }

  showMechanics(){
    this.getMechanics();
    if(this.clicked != true){  
      this.clicked = !this.clicked
    }
    this.mechanic = !this.mechanic
  }

  chooseAppointment(){
    this.getAppointments();
    if(this.clicked != true){  
       this.clicked = !this.clicked
    }
    this.appointment = !this.appointment
    this.mechanic = false
  }

  chooseMechAppointment(email:string){
    this.getMechAppointments(email);
    if(this.clicked != true){  
       this.clicked = !this.clicked
    }
    this.appointment = !this.appointment
    this.mechanic = false
  }

  finished(date:string, licensePlate:string, serviceId:string, desc:string, repVehicle:string){
    this.clicked=false;
    this.mechanic=false;
    this.appointment=false; 

    const app = new Appointment(date, desc, this.mechEmail, repVehicle, serviceId, licensePlate);
    this.appointmentService.addAppointment(app).subscribe(value => {
      console.log(value);
    });

    this.router.navigateByUrl('/pocetna');
  }

}
