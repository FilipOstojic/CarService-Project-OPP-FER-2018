import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../mechanic';

@Component({
  selector: 'app-mechanics',
  templateUrl: './mechanics.component.html',
  styleUrls: ['./mechanics.component.css']
})
export class MechanicsComponent implements OnInit {
  mechanics:Mechanic[] = [];

  constructor() {
  }

  ngOnInit() {
    this.mechanics = [{name:'Ante', surname:'Žužul', email:'ante.zuzul@mehanicar.hr', mobile:'0981234567', oib:'76113742199', password:'1234'},
    {name:'Karlo', surname:'Fruhwirth', email:'karlo.fruhwirth@mehanicar.hr', mobile:'0981234566', oib:'76113742198', password:'1234'},
    {name:'Marko', surname:'Jelović', email:'marko.jelovic@mehanicar.hr', mobile:'0981234565', oib:'76113742197', password:'1234'},
    {name:'Filip', surname:'Ostojić', email:'filip.ostojic@mehanicar.hr', mobile:'0981234564', oib:'76113742196', password:'1234'},
    {name:'Jozo', surname:'Ćaćić', email:'jozo.cacic@mehanicar.hr', mobile:'0981234563', oib:'76113742195', password:'1234'},
    {name:'Nikolina', surname:'Mijoc', email:'nikolina.mijoc@mehanicar.hr', mobile:'0981234562', oib:'76113742194', password:'1234'}];
  }
}
