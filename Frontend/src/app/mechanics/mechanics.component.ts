import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../mechanic';
import { MechanicService } from '../mechanic.service';

@Component({
  selector: 'app-mechanics',
  templateUrl: './mechanics.component.html',
  styleUrls: ['./mechanics.component.css']
})
export class MechanicsComponent implements OnInit {
  mechanics:Mechanic[] = [];

  constructor(private mechanicService : MechanicService) {
  }

  ngOnInit() {
    this.getMechanics();
  }

  getMechanics() {
    const mechanics = this.mechanicService.getMechanics();
    mechanics.subscribe((mechanics) => {
      this.mechanics = mechanics;
    });
  }
}
