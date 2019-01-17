import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Mechanic } from '../mechanic';
import { DatasharingService } from '../datasharing.service';
import { RegisterService } from '../register.service';
import { MechanicService } from '../mechanic.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrls: ['./admin-info.component.css']
})
export class AdminInfoComponent implements OnInit {
  admin: User;
  isAdmin: boolean;
  addMechForm: boolean = false;
  addUserForm: boolean = false;
  users: User[];
  mechs: Mechanic[];
  selectedMech : Mechanic;
  selectedUser : User;

  constructor(
    private datasharingService: DatasharingService,
    private userService: RegisterService,
    private mechService: MechanicService,
    private modalService: NgbModal
  ) {
    this.datasharingService.loggedInUser.subscribe(value => {
      this.admin = value;
    });
    this.datasharingService.isAdmin.subscribe(value => {
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

  public showMechForm() {
    this.addMechForm = true;
  }

  public showUserForm() {
    this.addUserForm = true;
  }

  public closeForm() {
    this.addMechForm = false;
    this.addUserForm = false;
  }

  addUser(name: string, surname: string, email: string, mobile: string, oib: string, password: string) {
    let user: User = { "name": name, "surname": surname, "oib": oib, "email": email, "mobile": mobile, "password": password, role: null };
    this.userService.addUser(user).subscribe(value => {
      this.getAllUsers();
    });
    this.closeForm();
  }

  addMech(name: string, surname: string, email: string, mobile: string, oib: string, password: string) {
    let mech: User = { "name": name, "surname": surname, "oib": oib, "email": email, "mobile": mobile, "password": password, role: null };
    this.mechService.addMechanic(mech).subscribe(value => {
      this.getMechs();
    });
    this.closeForm();
  }

  openVerticallyCenteredUser(content, user : User) {
    this.selectedUser = user;
    this.modalService.open(content, { centered: true });
  }

  deleteMech(mech : Mechanic){
    console.log("del "+ mech.email);
    this.modalService.dismissAll();
  }

  openVerticallyCenteredMech(content, mech : Mechanic) {
    this.selectedMech = mech;
    this.modalService.open(content, { centered: true });
  }
}
