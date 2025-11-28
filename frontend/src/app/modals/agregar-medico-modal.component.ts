// features/medicos/components/modals/agregar-medico-modal/agregar-medico-modal.component.ts
import { Component, EventEmitter, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface NuevoMedico {
  nombre: string;
  apellido: string;
  especialidad: string;
  disponible: boolean;
  horaInicio: string;
  horaFin: string;
}

@Component({
  selector: 'app-agregar-medico-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './agregar-medico-modal.component.html'
})
export class AgregarMedicoModalComponent {
  @Input() mostrarModal: boolean = false;
  @Output() cerrarModal = new EventEmitter<void>();
  @Output() guardarMedico = new EventEmitter<NuevoMedico>();

  nuevoMedico: NuevoMedico = {
    nombre: '',
    apellido: '',
    especialidad: '',
    disponible: true,
    horaInicio: '',
    horaFin: '',
  };

  horasDisponibles: string[] = [
    '07:00', '08:00', '09:00', '10:00', '11:00', '12:00',
    '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'
  ];

  horarioInvalido: boolean = false;

  onCerrar() {
    this.cerrarModal.emit();
    this.resetForm();
  }

  onGuardar() {
    if (this.validarFormulario()) {
      this.guardarMedico.emit({ ...this.nuevoMedico });
    }
  }

  validarFormulario(): boolean {
    // Validar campos requeridos
    if (
      !this.nuevoMedico.nombre ||
      !this.nuevoMedico.especialidad ||
      !this.nuevoMedico.horaInicio ||
      !this.nuevoMedico.horaFin
    ) {
      alert('Por favor completa todos los campos requeridos');
      return false;
    }

    // Validar que nombre y apellido solo contengan letras
    const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/;
    if (!nameRegex.test(this.nuevoMedico.nombre.trim())) {
      alert('El campo Nombre solo puede contener letras y espacios');
      return false;
    }
    if (this.nuevoMedico.apellido && !nameRegex.test(this.nuevoMedico.apellido.trim())) {
      alert('El campo Apellido solo puede contener letras y espacios');
      return false;
    }

    // Validar horario
    if (!this.validarHorario()) {
      alert('La hora de inicio debe ser anterior a la hora de fin');
      return false;
    }

    return true;
  }

  validarHorario(): boolean {
    if (!this.nuevoMedico.horaInicio || !this.nuevoMedico.horaFin) {
      this.horarioInvalido = false;
      return false;
    }

    const inicioMinutos = this.horaAMinutos(this.nuevoMedico.horaInicio);
    const finMinutos = this.horaAMinutos(this.nuevoMedico.horaFin);

    const valid = finMinutos > inicioMinutos;
    this.horarioInvalido = !valid;
    return valid;
  }

  horaAMinutos(hora: string): number {
    const [horas, minutos] = hora.split(':').map(Number);
    return horas * 60 + minutos;
  }

  allowOnlyLetters(event: KeyboardEvent): void {
    const key = event.key;
    const regex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]$/;
    if (!regex.test(key)) {
      event.preventDefault();
    }
  }

  handlePaste(event: ClipboardEvent, field: 'nombre' | 'apellido'): void {
    event.preventDefault();
    const clipboard = event.clipboardData?.getData('text') || '';
    const filtered = clipboard.replace(/[^A-Za-zÁÉÍÓÚáéíóúÑñ ]+/g, '').trim();
    if (field === 'nombre') {
      this.nuevoMedico.nombre = filtered;
    } else {
      this.nuevoMedico.apellido = filtered;
    }
  }

  resetForm(): void {
    this.nuevoMedico = {
      nombre: '',
      apellido: '',
      especialidad: '',
      disponible: true,
      horaInicio: '',
      horaFin: '',
    };
    this.horarioInvalido = false;
  }
}