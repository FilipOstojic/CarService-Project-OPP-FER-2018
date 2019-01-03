import { Component } from '@angular/core';
import { USERS } from './mock-users';
import { DataService } from './data.service';
import { Router } from '@angular/router';
import { AuthServiceService } from './auth-service.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Autoservis LSE';
  isCollapsed = false;

  close(){
    this.isCollapsed = !this.isCollapsed;
  }

  data: DataService;


  constructor(private dataService: DataService, private route: Router, private auth: AuthServiceService) {
    this.data = dataService;
    this.route = route;
    this.auth = auth;
  }

  getUserOptions() {
    return this.data.getUser().options;
  }

  performOption(option) {

    if (option == 'Odjava') {
      this.route.navigate(['Pocetna'])
      this.auth.logout()
    }

    else {
      this.route.navigate([option])
    }
  }
}
