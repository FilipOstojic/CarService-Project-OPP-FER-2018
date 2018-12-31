import { Component, OnInit } from '@angular/core';
import { DataService} from '../data.service'

@Component({
  selector: 'app-mechanics',
  templateUrl: './mechanics.component.html',
  styleUrls: ['./mechanics.component.css']
})
export class MechanicsComponent implements OnInit {

  
  
  data: DataService;

  constructor(private dataService: DataService) {
      this.data = dataService;
   }

  ngOnInit() {
  }

  getMechanics() {
      return this.data.getMechanics();
  }

}
