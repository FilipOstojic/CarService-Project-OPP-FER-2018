import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  data: DataService;

  constructor(private dataService: DataService) {
      this.data = dataService;
   }

  ngOnInit() {
  }

  getServices() {
      return this.data.getServices();
  }
}
