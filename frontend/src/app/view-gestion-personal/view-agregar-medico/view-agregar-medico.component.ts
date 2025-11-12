import { Component } from '@angular/core';

interface Doctor {
  id: string;
  name: string;
  specialtyName: string;
  experience: string;
  photo?: string;
}

@Component({
  selector: 'app-view-agregar-medico',
  templateUrl: './view-agregar-medico.component.html',
  styleUrls: ['./view-agregar-medico.component.css']
})
export class ViewAgregarMedicoComponent {
  doctors: Doctor[] = [];
  newDoctor: Doctor = { id: '', name: '', specialtyName: '', experience: '' };
  showForm = false;

  openForm() {
    this.showForm = true;
  }

  closeForm() {
    this.showForm = false;
  }

  addDoctor() {
    if (!this.newDoctor.name || !this.newDoctor.specialtyName || !this.newDoctor.experience) {
      alert('Por favor, complete todos los campos.');
      return;
    }

    this.newDoctor.id = (this.doctors.length + 1).toString();
    this.doctors.push({ ...this.newDoctor });
    alert('Médico agregado exitosamente.');
    this.newDoctor = { id: '', name: '', specialtyName: '', experience: '' };
    this.showForm = false;
  }
}

