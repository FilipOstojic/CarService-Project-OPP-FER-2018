import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import { HomepageComponent } from './homepage/homepage.component';
import { ServicesComponent } from './services/services.component';
import { AboutComponent } from './about/about.component';
import { ContactsComponent } from './contacts/contacts.component';
import { MechanicsComponent } from './mechanics/mechanics.component';
import { FormComponent } from './form/form.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LogoutComponent } from './logout/logout.component';
import { AccountComponent } from './account/account.component';
import { MecInfoComponent } from './mec-info/mec-info.component';
import { ServiceInfoComponent } from './service-info/service-info.component';


const routes: Routes = [

  { path: '', redirectTo: '/pocetna', pathMatch: 'full' },
  { path: 'usluge', component: ServicesComponent },
  { path: 'pocetna', component: HomepageComponent },
  { path: 'about', component: AboutComponent },
  { path: 'serviseri', component: MechanicsComponent },
  { path: 'kontakt', component: ContactsComponent },
  { path: 'rezervacija', component: FormComponent },
  { path: 'prijava', component: LoginComponent },
  { path: 'registracija', component: RegistrationComponent },
  { path: 'odjava', component: LogoutComponent },
  { path: 'racun', component: AccountComponent },
  { path: 'mechInfo', component: MecInfoComponent},
  { path: 'serviceInfo', component: ServiceInfoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
