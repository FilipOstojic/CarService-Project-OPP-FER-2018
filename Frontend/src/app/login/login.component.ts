import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { LoginService } from '../login.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  private formSumitAttempt: boolean;
  private loginError : boolean;

  constructor(private router: Router, private loginService: LoginService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });
  }

  onSubmit(email: string, password: string) {
    this.formSumitAttempt = true;
    if (this.form.valid) {
      console.log('form submitted');
      var result: Observable<any> = this.loginService.login(email, password);
      result.subscribe((ccc) => {
        console.log("LOGIN DONE");
        this.router.navigate(['/pocetna']);
      },
        error => {
          this.loginError = true;
          console.log("ERROR OCCURED")
        }
      );
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
