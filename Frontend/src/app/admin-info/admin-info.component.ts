import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Mechanic } from '../mechanic';
import { DatasharingService } from '../datasharing.service';
import { RegisterService } from '../register.service';
import { MechanicService } from '../mechanic.service';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrls: ['./admin-info.component.css']
})
export class AdminInfoComponent implements OnInit {
  admin:User;
  isAdmin:boolean;
  addMechForm: boolean = false;
  addUserForm: boolean = false;
  users: User[];
  mechs: Mechanic[];

  constructor(
    private datasharingService : DatasharingService,
    private userService: RegisterService,
    private mechService: MechanicService
  ) {
    this.datasharingService.loggedInUser.subscribe(value => {
      this.admin = value;
    });
    this.datasharingService.isAdmin.subscribe( value => {
      this.isAdmin = value;
    });
   }

  ngOnInit() {
    this.getMechs();
    this.getAllUsers();
  }

  
  private getMechs() {
    this.mechService.getMechanics().subscribe(mechs => {
      this.mechs = mechs;
    })
  }

  private getAllUsers() {
    this.userService.getUsers().subscribe(users => {
      this.users = users;
    })
  }

  public showMechForm(){
    this.addMechForm = true;
  }

  public showUserForm(){
    this.addUserForm = true;
  }

  public closeForm() {
    this.addMechForm = false;
    this.addUserForm = false;
  }
}
