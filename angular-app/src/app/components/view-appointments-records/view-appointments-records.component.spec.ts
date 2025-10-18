import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAppointmentsRecordsComponent } from './view-appointments-records.component';

describe('ViewAppointmentsRecordsComponent', () => {
  let component: ViewAppointmentsRecordsComponent;
  let fixture: ComponentFixture<ViewAppointmentsRecordsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewAppointmentsRecordsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewAppointmentsRecordsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
