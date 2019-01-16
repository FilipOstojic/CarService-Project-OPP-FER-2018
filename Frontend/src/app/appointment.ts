import { Service } from './service';
import { Mechanic } from './mechanic';

export class Appointment {
    id : number;
    date : string;  
    description: string;
    mechanic : Mechanic;
    repVehicle: string;
    service : Service;
    vehicle : string;

    constructor(date:string, desc:string, mech:Mechanic, repVehicle:string, service:Service, vehicle:string){
        this.date = date;
        this.description = desc;
        this.mechanic = mech;
        this.repVehicle = repVehicle;
        this.service = service;
        this.vehicle = vehicle;
    }
}