import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomepageComponent } from './homepage/homepage.component';
import { ServicesComponent } from './services/services.component';
import { AboutComponent } from './about/about.component';
import { ContactsComponent } from './contacts/contacts.component';
import { MechanicsComponent } from './mechanics/mechanics.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LogoutComponent } from './logout/logout.component';
import { AccountComponent } from './account/account.component';


const routes: Routes = [
  { path: '', redirectTo: '/pocetna', pathMatch: 'full' },
  { path: 'usluge', component: ServicesComponent },
  { path: 'pocetna', component: HomepageComponent },
  { path: 'about', component: AboutComponent },
  { path: 'serviseri', component: MechanicsComponent },
  { path: 'kontakt', component: ContactsComponent },
  { path: 'prijava', component: LoginComponent },
  { path: 'registracija', component: RegistrationComponent },
  { path: 'odjava', component: LogoutComponent },
  { path: 'racun', component: AccountComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
