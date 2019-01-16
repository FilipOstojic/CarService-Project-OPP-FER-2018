export class AutoserviceInfo {
    id : number;
    name : string;
    address : string;
    email : string;
    mobile : string;
    oib : string;

    constructor(id:number, name:string, address:string, email:string, mobile:string, oib:string) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.oib = oib;
    }
}
