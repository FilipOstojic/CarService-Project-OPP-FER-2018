import { Role } from './role';

export class User {
    name: string;
    surname : string;
    email: string;
    mobile : string;
    oib : string;
    password : string;
    role : Role;

    constructor(email:string) {
        this.email = email;
    }
}