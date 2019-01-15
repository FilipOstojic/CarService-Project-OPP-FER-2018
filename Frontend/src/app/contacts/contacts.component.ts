import { Component, OnInit } from '@angular/core';
import { InfoService } from '../info.service';
import { Info } from '../info';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {
  information: Info = {name: "", address : "", email: "", mobile : "", oib : ""};
  constructor(private infoService: InfoService) { }

  ngOnInit() {
    this.getInfo();
  }

  getInfo() {
    const information = this.infoService.getInfo();
    information.subscribe((information) => {
      this.information = information;
    })
  }

}
