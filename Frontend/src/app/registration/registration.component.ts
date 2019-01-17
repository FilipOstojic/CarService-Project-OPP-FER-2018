import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { NgForm, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../user';
import { RegisterService } from '../register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  error : boolean = false;

  registerError : boolean ;
  form: FormGroup;

  private formSumitAttempt: boolean;
  //addUser(name.value,surname.value,email.value,oib.value,cellPhone.value,password.value)

  constructor(private registerService: RegisterService, private formBuilder: FormBuilder, private router: Router) { }

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
  }

  onSubmit(name: string, surname: string, email: string, oib: string, mobile: string, password: string, confPass: string): void {
    console.log("REGISTRIRAJ SE")
    this.formSumitAttempt = true;
    if (this.form.valid) {
      console.log('form submitted');
      let user: User = { "name": name, "surname": surname, "oib": oib, "email": email, "mobile": mobile, "password": password,"role": {"id" : 2,"name":"MECH"}};
      if (user.password === confPass) {
      var result: Observable<User> = this.registerService.addUser(user);
      result.subscribe((prod) => {
        console.log("ADD DONE");
        this.router.navigate(['/pocetna']);
      },
        error => {
          console.log("ERROR OCCURED")
        });
      } else {
        this.registerError = true;
        console.log("ERROR OCCURED")
      }
    }
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
