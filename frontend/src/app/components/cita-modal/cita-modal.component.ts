// components/cita-modal/cita-modal.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CitaService, CitaData } from '../../services/cita.service';

@Component({
  selector: 'app-cita-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cita-modal.component.html'
})
export class CitaModalComponent implements OnInit {
  mostrarModal = false;
  citaData: CitaData | null = null;
  
  fechaMinima: string;
  horasDisponibles: string[] = [
    '08:00', '09:00', '10:00', '11:00', 
    '14:00', '15:00', '16:00', '17:00'
  ];

  constructor(private citaService: CitaService) {
    // Fecha mínima: mañana
    const mañana = new Date();
    mañana.setDate(mañana.getDate() + 1);
    this.fechaMinima = mañana.toISOString().split('T')[0];
  }

  ngOnInit() {
    this.citaService.mostrarModal$.subscribe(mostrar => {
      this.mostrarModal = mostrar;
    });

    this.citaService.citaData$.subscribe(data => {
      this.citaData = data;
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
      
      this.citaData.costo = esFinDeSemana ? 180 : 150;
    }
  }

  confirmarCita() {
    if (this.citaData) {
      this.citaService.confirmarCita(this.citaData);
    }
  }

  cancelar() {
    this.citaService.cerrarModal();
  }
}