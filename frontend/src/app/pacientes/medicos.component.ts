// pages/especialidad/especialidad.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CitaService } from '../services/cita.service';
import { CitaModalComponent } from '../modals/cita-modal.component';
import { MedicoService, Medico } from '../services/medico.service';

@Component({
  selector: 'app-medicos',
  standalone: true,
  imports: [CommonModule, CitaModalComponent],
  templateUrl: "medicos.component.html"
})
export class MedicosComponent implements OnInit {
  especialidadId: string = '';
  medicos: Medico[] = [];
  currentUrl: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private citaService: CitaService,
    private medicoService: MedicoService
  ) { }

  ngOnInit() {
    this.currentUrl = this.router.url;
    console.log('URL actual:', this.currentUrl);

    this.route.paramMap.subscribe(params => {
      this.especialidadId = params.get('id') || '';
      console.log('medico ID:', this.especialidadId);
      this.cargarMedicos();
    });
  }

  cargarMedicos() {
    console.log('Cargando médicos para:', this.especialidadId);
    this.medicoService.findAll().subscribe((list: Medico[]) => {
      // adaptar estructura si backend devuelve firstName/secondName
      this.medicos = list
        .map((m: any) => ({
          ...m,
          nombre: m.nombre || (m.firstName ? m.firstName + (m.secondName ? ' ' + m.secondName : '') : (m.nombre || ''))
        }))
        .filter((m: Medico) => {
          const specFromBackend = (m.especialidad || '').toString();
          const normalize = (s: string) => s.normalize('NFD').replace(/\p{Diacritic}/gu, '').toLowerCase();
          return normalize(specFromBackend) === normalize(this.especialidadId || '');
        });
      console.log('Médicos cargados desde backend:', this.medicos);
    });
  }

  // En medicos.component.ts - modifica el método agendarCita
  agendarCita(medico: Medico) {
    console.log('DEBUG: Click en Agendar Cita');
    console.log('DEBUG: Médico:', medico);
    console.log('DEBUG: Servicio inyectado:', this.citaService);

    this.citaService.abrirModal(
      medico.id,
      medico.nombre || '',
      medico.especialidad
    );

    // Verifica si el modal se abre
    this.citaService.mostrarModal$.subscribe((estado: any) => {
      console.log('DEBUG: Estado del modal:', estado);
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

  getIniciales(nombreCompleto: string | undefined): string {
    if (!nombreCompleto) return 'US';
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 0 || index === 1)
      .map(nombre => nombre[0])
      .join('')
      .toUpperCase();
  }

  volverAEspecialidades() {
    this.router.navigate(['/especialidades']);
  }
}