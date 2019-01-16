import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Appointment } from '../appointment';
import { AppointmentService } from '../appointment.service';
import { DatasharingService } from '../datasharing.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppointmentReal } from '../appointment-real';

@Component({
  selector: 'app-mec-info',
  templateUrl: './mec-info.component.html',
  styleUrls: ['./mec-info.component.css']
})
export class MecInfoComponent implements OnInit {
  mech: User = { "name": "", "surname": "", "oib": "", "email": "", "mobile": "", "password": "", role: null };
  selectedAppointment : Appointment;
  appointments: Appointment[] = [];
  updateForm: boolean = false;
  isMech: boolean;
  newAppointment : AppointmentReal = null;

  constructor(
    private appointmentService: AppointmentService,
    private datasharingService: DatasharingService,
    private modalService: NgbModal
  ) { 
    this.datasharingService.loggedInUser.subscribe(value => {
      this.mech = value;
    });
    this.datasharingService.isMech.subscribe( value => {
      this.isMech = value;
    })
  }

  ngOnInit() {
    this.getAppointments();
  }

  getAppointments() {
    console.log("get appointments");
    this.appointmentService.getMechAppointmentsApp(this.mech.email).subscribe((appointments) => {
      appointments.forEach(element => {
        element.date = element.date.substr(11,8);
      });
      this.appointments = appointments;
    });
  }

  editApp(desc:string, id:number){
    this.appointmentService.getAppointment(id).subscribe(value => {
      this.newAppointment = value;
      this.newAppointment.description = desc;
      this.appointmentService.updateAppointment(this.newAppointment).subscribe(data => {
        this.getAppointments();
        this.modalService.dismissAll();
      });
    });
  }

  openVerticallyCentered(content, app : Appointment) {
    this.selectedAppointment = app;
    this.modalService.open(content, { centered: true });
  }
  
}
