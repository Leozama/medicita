// pages/dashboardAdmin/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, User } from '../../../core/services/auth.service';
import { MedicoService } from '../../../core/services/medico.service';
import { AgregarMedicoModalComponent, NuevoMedico } from '../../modals/agregar-medico-modal/agregar-medico-modal.component';

interface Medico {
  id: number;
  nombre: string;
  especialidad: string;
  disponible: boolean;
  horario: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, AgregarMedicoModalComponent],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  medicoSeleccionado: Medico | null = null;
  mostrarModalAgregar = false;

  medicos: Medico[] = [];

  constructor(
    private authService: AuthService,
    private router: Router,
    private medicoService: MedicoService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();

    if (!this.currentUser || this.currentUser.tipo !== 'admin') {
      this.router.navigate(['/login']);
      return;
    }

    this.cargarMedicos();
  }

  cargarMedicos(): void {
    this.medicoService.findAll().subscribe({
      next: (list) => {
        this.medicos = list.map((m) => ({
          id: m.id,
          nombre: (m.nombre ?? `${m.firstName ?? ''} ${m.secondName ?? ''}`).trim(),
          especialidad: m.especialidad ?? '',
          disponible: m.disponible ?? true,
          horario: m.horario ?? '',
        }));
      },
      error: (err) => {
        console.error('Error cargando médicos:', err);
      },
    });
  }

  seleccionarMedico(medico: Medico): void {
    this.medicoSeleccionado = this.medicoSeleccionado?.id === medico.id ? null : medico;
  }

  eliminarMedicoSeleccionado(): void {
    if (!this.medicoSeleccionado) return;

    if (!confirm(`¿Estás seguro de eliminar a ${this.medicoSeleccionado.nombre}?`)) return;

    const id = this.medicoSeleccionado.id;

    // Si el id es un id temporal/cliente (por ejemplo 0), eliminamos solo en el cliente
    if (!id || id <= 0) {
      this.medicos = this.medicos.filter((m) => m.id !== id);
      this.medicoSeleccionado = null;
      alert('Médico eliminado localmente');
      return;
    }

    this.medicoService.deleteMedico(id).subscribe({
      next: (ok) => {
        if (ok) {
          this.medicos = this.medicos.filter((m) => m.id !== id);
          this.medicoSeleccionado = null;
          alert('Médico eliminado correctamente');
        } else {
          alert('No se pudo eliminar el médico. Revisa la consola para más detalles.');
        }
      },
      error: (err) => {
        console.error('Error eliminando medico:', err);
        alert('Ocurrió un error eliminando el médico. Revisa la consola.');
      },
    });
  }

  onGuardarMedico(nuevoMedico: NuevoMedico): void {
    const firstName = nuevoMedico.nombre.trim();
    const secondName = nuevoMedico.apellido.trim();

    const payload = {
      firstName,
      secondName,
      especialidad: nuevoMedico.especialidad,
      horario: `${nuevoMedico.horaInicio} - ${nuevoMedico.horaFin}`,
      costoConsulta: null,
    };

    this.medicoService.createMedico(payload).subscribe({
      next: (created) => {
        const nuevo: Medico = {
          id: created.id,
          nombre: `${created.firstName || firstName}${
            created.secondName ? ' ' + created.secondName : ''
          }`.trim(),
          especialidad: created.especialidad || nuevoMedico.especialidad,
          disponible: nuevoMedico.disponible,
          horario: created.horario || `${nuevoMedico.horaInicio} - ${nuevoMedico.horaFin}`,
        };

        this.medicos.push(nuevo);
        this.mostrarModalAgregar = false;
        alert(`Médico ${nuevo.nombre} agregado y guardado en la base de datos`);
      },
      error: (err) => {
        console.error('Error creando medico en backend:', err);
        alert('No se pudo guardar el médico en el servidor. Intenta de nuevo.');
      },
    });
  }

  onCerrarModal(): void {
    this.mostrarModalAgregar = false;
  }

  getIniciales(nombreCompleto: string): string {
    if (!nombreCompleto) return 'US';
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 0 || index === 1)
      .map(nombre => nombre[0])
      .join('')
      .toUpperCase();
  }
}