import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MecInfoComponent } from './mec-info.component';

describe('MecInfoComponent', () => {
  let component: MecInfoComponent;
  let fixture: ComponentFixture<MecInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MecInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MecInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
