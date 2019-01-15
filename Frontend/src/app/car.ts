import { Model } from './model';
import { User } from './user';

export class Car {
    licensePlate: string;
    model: Model;
    year: string;
    owner: User;

    constructor(licensePlate: string, model: Model, year: string, email:string) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.year = year;
        this.owner = new User(email);
    }
}

