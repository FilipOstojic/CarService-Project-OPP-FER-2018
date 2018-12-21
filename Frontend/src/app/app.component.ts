import { Component } from '@angular/core';
import { USERS } from './mock-users';
import { DataService } from './data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front-end';

  data: DataService;

  constructor(private dataService: DataService) {
    this.data = dataService;
  }

  getUserOptions() {
    return this.data.getUser().options;
  }
}
