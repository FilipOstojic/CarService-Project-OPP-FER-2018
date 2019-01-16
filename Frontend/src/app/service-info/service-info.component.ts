import { Component, OnInit } from '@angular/core';
import { AutoserviceInfo } from '../autoservice-info';
import { ServiceInfoService } from '../service-info.service';

@Component({
  selector: 'app-service-info',
  templateUrl: './service-info.component.html',
  styleUrls: ['./service-info.component.css']
})
export class ServiceInfoComponent implements OnInit {

  autoservice : AutoserviceInfo = {id:0,name : "", address : "", email : "", mobile : "", oib : ""};

  constructor(
    private serviceInfo : ServiceInfoService
  ) {}

  ngOnInit() {
    this.getServiceInfo();
  }

  editInfo(ime:string, adresa:string, email:string, oib:string, mobitel:string) {
    this.serviceInfo.updateInfo(new AutoserviceInfo(this.autoservice.id, ime, adresa, email, mobitel, oib)).subscribe(info => {
      this.autoservice = info;
    });
  }

  getServiceInfo() {
    console.log("get info");
    this.serviceInfo.getInfo().subscribe(info => {
      this.autoservice = info;
    });
  }

}
