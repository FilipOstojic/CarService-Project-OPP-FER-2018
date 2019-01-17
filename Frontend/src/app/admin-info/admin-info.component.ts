import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Mechanic } from '../mechanic';
import { DatasharingService } from '../datasharing.service';
import { RegisterService } from '../register.service';
import { MechanicService } from '../mechanic.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

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
  form: FormGroup;
  private formSumitAttempt: boolean;
  registerError : boolean ;
  sameUser : boolean;


  constructor(
    private datasharingService: DatasharingService,
    private userService: RegisterService,
    private mechService: MechanicService,
    private modalService: NgbModal,private formBuilder: FormBuilder
  ) {
    this.datasharingService.loggedInUser.subscribe(value => {
      this.admin = value;
    });
    this.datasharingService.isAdmin.subscribe(value => {
      this.isAdmin = value;
    });
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      name: [null, Validators.required],
      surname: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
      confPass: [null, Validators.required],
      cellPhone: [null, Validators.required],
      oib: [null, Validators.required]
    });
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

  onSubmit(name: string, surname: string, email: string, oib: string,mobile: string, password: string, confPass: string) :void{
    console.log("DODAJ SERVISERA")
    this.formSumitAttempt = true;
    if (this.form.valid) {
      console.log('form submitted');
      let user: User = { "name": name, "surname": surname, "oib": oib, "email": email, "mobile": mobile, "password": password, role: null };
      if (user.password === confPass) {

        var result: Observable<User> = this.userService.addUser(user);
      result.subscribe((prod) => {
        console.log("ADD DONE");
        this.userService.addUser(user).subscribe(value => {
          this.getAllUsers();
        });
        this.closeForm();
      },
        error => {
          console.log(error);
          console.log("ERROR SAME OCCURED");
          this.sameUser = true;
        });
      }else {
        this.registerError = true;
        console.log("ERROR OCCURED")
      }
    }
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

  isFieldValid(field: string) {
    return (
      (!this.form.get(field).valid && this.form.get(field).touched) ||
      (this.form.get(field).untouched && this.formSumitAttempt)
    );
  }

  displayFieldCss(field: string) {
    return {
      'has-error': this.isFieldValid(field),
      'has-feedback': this.isFieldValid(field),
      'is-invalid':this.isFieldValid(field)
    };
  }
}
