import { Component } from '@angular/core';

interface Doctor {
  id: string;
  name: string;
  specialtyName: string;
  experience: string;
  photo?: string;
}

@Component({
  selector: 'app-view-eliminar-medico',
  templateUrl: './view-eliminar-medico.component.html',
  styleUrls: ['./view-eliminar-medico.component.css']
})
export class ViewEliminarMedicoComponent {
  doctors: Doctor[] = [
    { id: '1', name: 'Dr. Juan Pérez', specialtyName: 'Cardiología', experience: '8 años' },
    { id: '2', name: 'Dra. Ana Gómez', specialtyName: 'Pediatría', experience: '5 años' }
  ];
  selectedDoctor: Doctor | null = null;

  selectDoctor(doctor: Doctor) {
    this.selectedDoctor = this.selectedDoctor === doctor ? null : doctor;
  }

  deleteDoctor() {
    if (!this.selectedDoctor) {
      alert('Debe seleccionar un médico para eliminar.');
      return;
    }

    const confirmDelete = confirm(`¿Seguro que desea eliminar a ${this.selectedDoctor.name}?`);
    if (confirmDelete) {
      this.doctors = this.doctors.filter(d => d !== this.selectedDoctor);
      alert('Médico eliminado exitosamente.');
      this.selectedDoctor = null;
    }
  }
}

