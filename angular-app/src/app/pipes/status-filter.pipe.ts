import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusFilter'
})
export class StatusFilterPipe implements PipeTransform {

  transform(appointments: any[], statuses: string[]): any[] {
    if (!appointments || !statuses || statuses.length === 0) {
      return appointments;
    }
    return appointments.filter(appointment => statuses.includes(appointment.status));
  }

}