import { Service } from './service';

export class Appointment {
    id : number;
    date : string;  
    description: string;
    mechanic : string;
    repVehicle: string;
    service : string;
    vehicle : string;

    constructor(date:string, desc:string, mech:string, repVehicle:string, service:string, vehicle:string){
        this.date = date;
        this.description = desc;
        this.mechanic = mech;
        this.repVehicle = repVehicle;
        this.service = service;
        this.vehicle = vehicle;
    }
}