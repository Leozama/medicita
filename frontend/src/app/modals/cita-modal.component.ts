// components/cita-modal/cita-modal.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CitaService, CitaData } from '../../../core/services/cita.service';

@Component({
  selector: 'app-cita-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cita-modal.component.html',
})
export class CitaModalComponent implements OnInit {
  mostrarModal = false;
  citaData: CitaData | null = null;

  fechaMinima: string;
  horasDisponibles: string[] = [
    '08:00',
    '09:00',
    '10:00',
    '11:00',
    '14:00',
    '15:00',
    '16:00',
    '17:00',
  ];
  fechaEsFinDeSemana: boolean = false;

  constructor(private citaService: CitaService) {
    // Fecha mínima: mañana
    const mañana = new Date();
    mañana.setDate(mañana.getDate() + 1);
    this.fechaMinima = mañana.toISOString().split('T')[0];
  }

  ngOnInit() {
    this.citaService.mostrarModal$.subscribe((mostrar) => {
      this.mostrarModal = mostrar;
    });

    this.citaService.citaData$.subscribe((data) => {
      this.citaData = data;
      // Si el servicio proporcionó horas disponibles para el médico, usarlas
      if (this.citaData?.horasDisponibles && this.citaData.horasDisponibles.length) {
        this.horasDisponibles = this.citaData.horasDisponibles;
      }
    });
  }

  getCosto(): number {
    return this.citaData?.costo || 150;
  }

  actualizarCosto() {
    if (this.citaData) {
      // Ejemplo: costo base + recargo por fines de semana
      const fecha = new Date(this.citaData.fecha);
      const esFinDeSemana = fecha.getDay() === 0 || fecha.getDay() === 6; // 0=Domingo, 6=Sábado
      this.fechaEsFinDeSemana = esFinDeSemana;
      this.citaData.costo = esFinDeSemana ? 180 : 150;
    }
  }

  onFechaChange() {
    if (!this.citaData) return;

    if (!this.citaData.fecha) {
      this.fechaEsFinDeSemana = false;
      return;
    }

    const fecha = new Date(this.citaData.fecha);
    const dia = fecha.getDay(); // 0 domingo, 6 sabado
    if (dia === 0 || dia === 6) {
      // No permitimos fines de semana
      this.fechaEsFinDeSemana = true;
      // Limpiar la fecha seleccionada para forzar nueva elección
      this.citaData.fecha = '';
      // Actualizar costo por si corresponde
      this.citaData.costo = 150;
      return;
    }

    this.fechaEsFinDeSemana = false;
    this.actualizarCosto();
  }

  confirmarCita() {
    if (this.citaData) {
      // Validación adicional: no permitir fines de semana
      if (this.fechaEsFinDeSemana) {
        alert('No se pueden agendar citas en sábado o domingo.');
        return;
      }
      if (!this.citaData.fecha) {
        alert('Selecciona una fecha válida para la cita.');
        return;
      }
      // Intentar crear la cita en el backend
      this.citaService.confirmarCitaBackend(this.citaData).subscribe({
        next: (success) => {
          if (success) {
            this.citaService.cerrarModal();
            alert(
              `✅ Cita agendada correctamente para ${this.citaData?.fecha} a las ${this.citaData?.hora}`
            );
          } else {
            alert(' No se pudo agendar la cita. Verifica tu conexión o inicia sesión.');
          }
        },
        error: (err) => {
          console.error('Error reservando cita:', err);
          if (err && err.status === 409) {
            alert(err.error?.message || 'El turno ya está ocupado para ese médico, fecha y hora.');
          } else if (err && err.status === 400) {
            alert(err.error?.message || 'Datos inválidos para agendar la cita.');
          } else {
            alert('Error al agendar la cita. Intenta de nuevo más tarde.');
          }
        },
      });
    }
  }

  cancelar() {
    this.citaService.cerrarModal();
  }
}
