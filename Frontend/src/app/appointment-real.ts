import { Mechanic } from './mechanic';
import { Service } from './service';
import { Car } from './car';

export class AppointmentReal {
    id : number;
    date : Date;  
    description: string;
    mechanic : Mechanic;
    repVehicle: string;
    service : Service;
    vehicle : Car;

    constructor(date:Date, desc:string, mech:Mechanic, repVehicle:string, service:Service, vehicle:Car){
        this.date = date;
        this.description = desc;
        this.mechanic = mech;
        this.repVehicle = repVehicle;
        this.service = service;
        this.vehicle = vehicle;
    }
}