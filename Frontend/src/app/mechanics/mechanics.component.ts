import { Component, OnInit } from '@angular/core';
import { MECHANICS} from '../mock-mechanics'

@Component({
  selector: 'app-mechanics',
  templateUrl: './mechanics.component.html',
  styleUrls: ['./mechanics.component.css']
})
export class MechanicsComponent implements OnInit {

  mechanics = MECHANICS;
  
  constructor() { }

  ngOnInit() {
  }

}
