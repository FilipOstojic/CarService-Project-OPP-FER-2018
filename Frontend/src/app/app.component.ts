import { Component } from '@angular/core';
import { User } from './user';
import { LoginService } from './login.service';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  childCurrentValue : string = " ";

  constructor(private loginService : LoginService) {
   
  }

  getOutputVal(selected : string){
    if(selected){
      this.childCurrentValue = selected;
    }
  }

  ngOnInit(){
 }

}
