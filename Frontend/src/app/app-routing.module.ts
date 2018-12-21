import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomepageComponent} from './homepage/homepage.component';
import {ServicesComponent} from './services/services.component';
import {AboutComponent} from './about/about.component';


const routes: Routes = [
    {path: '', redirectTo: '/pocetna', pathMatch: 'full'},
    {path: 'usluge', component: ServicesComponent},
    {path: 'pocetna', component: HomepageComponent},
    {path: 'o-nama', component: AboutComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
