import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomepageComponent} from './homepage/homepage.component';
import {ServicesComponent} from './services/services.component';
import {AboutComponent} from './about/about.component';
import {ContactsComponent} from './contacts/contacts.component';
import {MechanicsComponent} from './mechanics/mechanics.component';
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import { LogoutComponent } from './logout/logout.component';


const routes: Routes = [
    {path: '', redirectTo: '/Pocetna', pathMatch: 'full'},
    {path: 'Usluge', component: ServicesComponent},
    {path: 'Pocetna', component: HomepageComponent},
    {path: 'O-nama', component: AboutComponent},
    {path: 'Serviseri', component: MechanicsComponent},
    {path: 'Kontakt', component: ContactsComponent},
    {path: 'Prijava', component: LoginComponent},
    {path: 'Registracija', component: RegistrationComponent},
    {path: 'Odjava', component: LogoutComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
