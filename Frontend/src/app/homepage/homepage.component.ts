import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})

export class HomepageComponent implements OnInit {

  data: DataService;

  constructor(private dataService: DataService) {
      this.data = dataService;
   }

  ngOnInit() {
  }

  getUserType() {
      return this.data.getUser().type;
  }

}
