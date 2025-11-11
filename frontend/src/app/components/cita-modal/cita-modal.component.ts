// components/cita-modal/cita-modal.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CitaService, CitaData } from '../../services/cita.service';

@Component({
  selector: 'app-cita-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <!-- Overlay -->
    <div *ngIf="mostrarModal" class="fixed inset-0 bg-black/30  z-50 flex items-center justify-center p-4">
      <!-- Modal -->
      <div class="bg-white rounded-2xl shadow-2xl max-w-md w-full max-h-[90vh] overflow-y-auto">
        
        <!-- Header -->
        <div class="bg-blue-600 text-white p-6 rounded-t-2xl">
          <h2 class="text-2xl font-bold">Agendar Cita Médica</h2>
          <p class="text-blue-100 mt-2" *ngIf="citaData">
            Con {{ citaData.medicoNombre }} - {{ citaData.especialidad }}
          </p>
        </div>

        <!-- Formulario -->
        <div class="p-6" *ngIf="citaData">
          <form (ngSubmit)="confirmarCita()" #citaForm="ngForm">
            
            <!-- Fecha -->
            <div class="mb-6">
              <label class="block text-gray-700 font-semibold mb-3">
                📅 Fecha de la cita
              </label>
              <input
                type="date"
                [(ngModel)]="citaData.fecha"
                name="fecha"
                required
                [min]="fechaMinima"
                class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                (change)="actualizarCosto()">
            </div>

            <!-- Hora -->
            <div class="mb-6">
              <label class="block text-gray-700 font-semibold mb-3">
                ⏰ Hora de la cita
              </label>
              <select
                [(ngModel)]="citaData.hora"
                name="hora"
                required
                class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
                <option value="">Selecciona una hora</option>
                <option *ngFor="let hora of horasDisponibles" [value]="hora">
                  {{ hora }}
                </option>
              </select>
            </div>

            <!-- Costo -->
            <div class="mb-6 p-4 bg-gray-50 rounded-lg">
              <label class="block text-gray-700 font-semibold mb-2">
                💰 Costo de la consulta
              </label>
              <div class="flex justify-between items-center">
                <span class="text-2xl font-bold text-green-600">
                  {{ getCosto() }}
                </span>
                <span class="text-sm text-gray-500">USD</span>
              </div>
              <p class="text-sm text-gray-600 mt-2">
                Incluye consulta médica y diagnóstico inicial
              </p>
            </div>

            <!-- Botones -->
            <div class="flex space-x-4">
              <button
                type="button"
                (click)="cancelar()"
                class="flex-1 bg-gray-500 text-white py-3 px-6 rounded-lg font-semibold hover:bg-gray-600 transition-colors">
                Cancelar
              </button>
              <button
                type="submit"
                [disabled]="!citaForm.valid"
                class="flex-1 bg-blue-600 text-white py-3 px-6 rounded-lg font-semibold hover:bg-blue-700 transition-colors disabled:bg-blue-400 disabled:cursor-not-allowed">
                Confirmar Cita
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  `
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