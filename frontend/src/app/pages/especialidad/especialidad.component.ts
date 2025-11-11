// pages/especialidad/especialidad.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CitaService } from '../../services/cita.service';
import { CitaModalComponent } from '../../components/cita-modal/cita-modal.component';

// Interface para el médico
interface Medico {
  id: number;
  nombre: string;
  especialidad: string;
  experiencia: string;
  disponible: boolean;
  calificacion?: number;
  reseñas?: number;
}

@Component({
  selector: 'app-especialidad',
  standalone: true,
  imports: [CommonModule, CitaModalComponent], // Agregar CitaModalComponent aquí
  template: `
    <!-- Header -->
    <section class="bg-blue-600 text-white py-12">
      <div class="container mx-auto px-4">
        <h1 class="text-4xl font-bold capitalize mb-2">{{ getNombreEspecialidad() }}</h1>
        <p class="text-xl text-blue-100">
          {{ medicos.length }} doctor{{ medicos.length !== 1 ? 'es' : '' }} disponible{{ medicos.length !== 1 ? 's' : '' }} en esta especialidad
        </p>
      </div>
    </section>

    <button (click)="volverAServicios()" class="flex items-center text-blue-600 hover:text-gray-600 mt-4 mb-4 transition-colors">
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/>
          </svg>
          Volver a servicios
        </button>

    <!-- Lista de Médicos -->
    <div class="container mx-auto px-4 py-8" *ngIf="medicos.length > 0">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-4xl mx-auto">
        <div *ngFor="let medico of medicos" class="bg-white p-6 rounded-lg shadow-lg border border-gray-200">
          <div class="flex items-start justify-between mb-4">
            <div class="flex items-center space-x-4">
              <!-- Foto del médico -->
              <div class="w-16 h-16 bg-gradient-to-br from-blue-500 to-blue-600 rounded-full flex items-center justify-center text-white font-bold text-lg">
                {{ getIniciales(medico.nombre) }}
              </div>
              <div>
                <h3 class="text-xl font-bold text-gray-800">{{ medico.nombre }}</h3>
                <p class="text-gray-600 capitalize">{{ medico.especialidad }}</p>
              </div>
            </div>
            
            <!-- Disponibilidad -->
            <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
              Disponible
            </span>
          </div>

          <p class="text-gray-700 mb-4">Experiencia: {{ medico.experiencia }}</p>
          
          <!-- Botón Agendar Cita -->
          <button 
            (click)="agendarCita(medico)"
            class="w-full bg-blue-600 text-white py-3 px-6 rounded-lg font-semibold hover:bg-blue-700 transition-colors">
            Agendar Cita
          </button>
        </div>
      </div>
    </div>

    <!-- Mensaje si no hay médicos -->
    <div *ngIf="medicos.length === 0 && especialidadId" class="container mx-auto px-4 py-8 text-center">
      <p class="text-red-500 text-xl">No se encontraron médicos para esta especialidad.</p>
      <p class="text-gray-600">Especialidad: {{ especialidadId }}</p>
    </div>

    <!-- Modal de Cita -->
    <app-cita-modal></app-cita-modal>
  `
})
export class EspecialidadComponent implements OnInit {
  especialidadId: string = '';
  medicos: Medico[] = [];
  currentUrl: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private citaService: CitaService
  ) {}

  ngOnInit() {
    this.currentUrl = this.router.url;
    console.log('URL actual:', this.currentUrl);
    
    this.route.paramMap.subscribe(params => {
      this.especialidadId = params.get('id') || '';
      console.log('Especialidad ID:', this.especialidadId);
      this.cargarMedicos();
    });
  }

  cargarMedicos() {
    console.log('Cargando médicos para:', this.especialidadId);
    
    // Datos de prueba COMPLETOS con IDs
    const todosLosMedicos: any = {
      'cardiologia': [
        { 
          id: 1, 
          nombre: 'Dr. Carlos Mendoza', 
          especialidad: 'Cardiología', 
          experiencia: '15 años',
          disponible: true,
          calificacion: 4.9,
          reseñas: 127
        },
        { 
          id: 2, 
          nombre: 'Dra. María Santos', 
          especialidad: 'Cardiología', 
          experiencia: '12 años',
          disponible: true,
          calificacion: 4.8,
          reseñas: 98
        }
      ],
      'pediatria': [
        { 
          id: 3, 
          nombre: 'Dr. Roberto Lima', 
          especialidad: 'Pediatría', 
          experiencia: '10 años',
          disponible: true,
          calificacion: 4.7,
          reseñas: 85
        },
        { 
          id: 4, 
          nombre: 'Dra. Ana García', 
          especialidad: 'Pediatría', 
          experiencia: '8 años',
          disponible: true,
          calificacion: 4.9,
          reseñas: 112
        }
      ],
      'odontologia': [
        { 
          id: 5, 
          nombre: 'Dr. Javier Rodríguez', 
          especialidad: 'Odontología', 
          experiencia: '9 años',
          disponible: true,
          calificacion: 4.8,
          reseñas: 76
        },
        { 
          id: 6, 
          nombre: 'Dra. Laura Martínez', 
          especialidad: 'Odontología', 
          experiencia: '11 años',
          disponible: true,
          calificacion: 4.9,
          reseñas: 134
        }
      ],
      'oftalmologia': [
        { 
          id: 7, 
          nombre: 'Dr. Miguel Torres', 
          especialidad: 'Oftalmología', 
          experiencia: '14 años',
          disponible: true
        },
        { 
          id: 8, 
          nombre: 'Dra. Carmen López', 
          especialidad: 'Oftalmología', 
          experiencia: '7 años',
          disponible: true
        }
      ],
      'ortopedia': [
        { 
          id: 9, 
          nombre: 'Dr. Andrés Castro', 
          especialidad: 'Ortopedia', 
          experiencia: '13 años',
          disponible: true
        },
        { 
          id: 10, 
          nombre: 'Dra. Patricia Ruiz', 
          especialidad: 'Ortopedia', 
          experiencia: '6 años',
          disponible: true
        }
      ],
      'neurologia': [
        { 
          id: 11, 
          nombre: 'Dr. Fernando Díaz', 
          especialidad: 'Neurología', 
          experiencia: '16 años',
          disponible: true
        },
        { 
          id: 12, 
          nombre: 'Dra. Sofía Herrera', 
          especialidad: 'Neurología', 
          experiencia: '9 años',
          disponible: true
        }
      ]
    };

    this.medicos = todosLosMedicos[this.especialidadId] || [];
    console.log('Médicos cargados:', this.medicos);
  }

    // En especialidad.component.ts - modifica el método agendarCita
    agendarCita(medico: Medico) {
    console.log('🔴 DEBUG: Click en Agendar Cita');
    console.log('🔴 DEBUG: Médico:', medico);
    console.log('🔴 DEBUG: Servicio inyectado:', this.citaService);
    
    this.citaService.abrirModal(
        medico.id,
        medico.nombre,
        medico.especialidad
    );
    
    // Verifica si el modal se abre
    this.citaService.mostrarModal$.subscribe(estado => {
        console.log('🔴 DEBUG: Estado del modal:', estado);
    });
    }
    
  getNombreEspecialidad(): string {
    const nombres: { [key: string]: string } = {
      'cardiologia': 'Cardiología',
      'pediatria': 'Pediatría',
      'odontologia': 'Odontología',
      'oftalmologia': 'Oftalmología',
      'ortopedia': 'Ortopedia',
      'neurologia': 'Neurología'
    };
    return nombres[this.especialidadId] || this.especialidadId;
  }

  getIniciales(nombreCompleto: string): string {
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 1 || index === 2)
      .map(nombre => nombre[0])
      .join('')
      .toUpperCase();
  }

  volverAServicios() {
    this.router.navigate(['/servicios']);
  }
}