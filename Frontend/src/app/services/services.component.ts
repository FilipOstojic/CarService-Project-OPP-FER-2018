import { Component, OnInit } from '@angular/core';
import { Service } from '../service';
import { ServiceService } from '../service.service';
import { Model } from '../model';
import { ModelService } from '../model.service';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {
  services: Service[] = [];
  models : Model[] = [];

  constructor(private servicesService : ServiceService,private modelService : ModelService) {
   }

  ngOnInit() {
    this.getServices();
  }

  getServices() {
    const services = this.servicesService.getServices();
    services.subscribe((services) => {
      this.services = services;
    });
  }
}
