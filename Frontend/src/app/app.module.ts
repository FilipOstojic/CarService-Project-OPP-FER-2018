import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AboutComponent } from './about/about.component';
import { ServicesComponent } from './services/services.component';
import { ContactsComponent } from './contacts/contacts.component';
import { MechanicsComponent } from './mechanics/mechanics.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { LogoutComponent } from './logout/logout.component';
import { AuthServiceService } from './auth-service.service';
import { MapComponent } from './map/map.component';
import { AgmCoreModule } from '@agm/core'; 
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { FormComponent } from './form/form.component';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AccountComponent } from './account/account.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    AboutComponent,
    ServicesComponent,
    ContactsComponent,
    MechanicsComponent,
    LoginComponent,
    RegistrationComponent,
    LogoutComponent,
    MapComponent,
    FormComponent,
    AccountComponent
  ],
  imports: [
    NgbModule.forRoot(), 
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    AgmCoreModule.forRoot({
      //za generiranje kljuca: https://cloud.google.com/maps-platform/
      apiKey: 'KEY'
    })
  ],
  providers: [AuthServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
