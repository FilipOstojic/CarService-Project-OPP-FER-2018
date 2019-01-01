import { Component } from '@angular/core';
import { USERS } from './mock-users';
import { DataService } from './data.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front-end';

  data: DataService;
  

  constructor(private dataService: DataService, private route: Router) {
    this.data = dataService;
    this.route = route;
   
  }

  getUserOptions() {
    return this.data.getUser().options;
  }

  performOption( option ){

    if (option == 'Odjava'){

      this.route.navigate(['Pocetna'])
      

    }

    else{

      this.route.navigate([option])
    }
  }
}
