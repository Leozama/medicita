// pages/dashboardAdmin/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, User } from '../../services/auth.service';
import { MedicoService } from '../../services/medico.service';

interface Medico {
  id: number;
  nombre: string;
  especialidad: string;
  experiencia: string;
  disponible: boolean;
  horario: string;
}

interface NuevoMedico {
  nombre: string;
  apellido: string;
  especialidad: string;
  disponible: boolean;
  horaInicio: string;
  horaFin: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  medicoSeleccionado: Medico | null = null;
  mostrarModalAgregar = false;

  nuevoMedico: NuevoMedico = {
    nombre: '',
    apellido: '',
    especialidad: '',
    disponible: true,
    horaInicio: '',
    horaFin: '',
  };

  horasDisponibles: string[] = [
    '07:00',
    '08:00',
    '09:00',
    '10:00',
    '11:00',
    '12:00',
    '13:00',
    '14:00',
    '15:00',
    '16:00',
    '17:00',
    '18:00',
  ];

  medicos: Medico[] = [];
  horarioInvalido: boolean = false;

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
    // Cargar médicos desde el backend
    // Si falla la petición, se mantiene la lista vacía y se muestra un error en consola
    // MedicoService inyectado para obtener los registros reales
    // Nota: se asigna directamente la respuesta asumiendo que los campos coinciden.
    // Si backend usa otras claves, adaptar el mapeo aquí.
    // Cargar médicos reales desde el backend
    this.medicoService.findAll().subscribe({
      next: (list) => {
        // Normalizamos la respuesta a la forma esperada por el componente
        this.medicos = list.map((m) => ({
          id: m.id,
          nombre: (m.nombre ?? `${m.firstName ?? ''} ${m.secondName ?? ''}`).trim(),
          especialidad: m.especialidad ?? '',
          experiencia: m.experiencia ?? '',
          disponible: m.disponible ?? true,
          horario: '',
        }));
      },
      error: (err) => {
        console.error('Error cargando médicos:', err);
        // Mantener lista vacía o mostrar mensaje al admin según prefieras
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

    // Llamar al backend para eliminar
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

  guardarMedico(): void {
    // Validaciones básicas: campos requeridos
    if (
      this.nuevoMedico.nombre &&
      this.nuevoMedico.especialidad &&
      this.nuevoMedico.horaInicio &&
      this.nuevoMedico.horaFin
    ) {
      // Validar que nombre y apellido solo contengan letras y espacios
      const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$/;
      if (!nameRegex.test(this.nuevoMedico.nombre.trim())) {
        alert('El campo Nombre solo puede contener letras y espacios');
        return;
      }
      if (this.nuevoMedico.apellido && !nameRegex.test(this.nuevoMedico.apellido.trim())) {
        alert('El campo Apellido solo puede contener letras y espacios');
        return;
      }
      // Validar que el horario sea correcto (horaInicio < horaFin)
      if (!this.validarHorario()) {
        alert('La hora de inicio debe ser anterior a la hora de fin');
        return;
      }
      // Preparar payload para backend usando nombre y apellido separados
      const firstName = this.nuevoMedico.nombre.trim();
      const secondName = this.nuevoMedico.apellido.trim();

      const payload = {
        firstName,
        secondName,
        especialidad: this.nuevoMedico.especialidad,
        horario: `${this.nuevoMedico.horaInicio} - ${this.nuevoMedico.horaFin}`,
        costoConsulta: null,
      };

      // Llamar al backend para crear el médico
      this.medicoService.createMedico(payload).subscribe({
        next: (created) => {
          // Mapear respuesta del backend al formato usado por el componente
          const nuevo: Medico = {
            id: created.id,
            nombre: `${created.firstName || firstName}${
              created.secondName ? ' ' + created.secondName : ''
            }`.trim(),
            especialidad: created.especialidad || this.nuevoMedico.especialidad,
            experiencia: '',
            disponible: this.nuevoMedico.disponible,
            horario:
              created.horario || `${this.nuevoMedico.horaInicio} - ${this.nuevoMedico.horaFin}`,
          };

          this.medicos.push(nuevo);
          this.cerrarModal();
          alert(`Médico ${nuevo.nombre} agregado y guardado en la base de datos`);
        },
        error: (err) => {
          console.error('Error creando medico en backend:', err);
          alert('No se pudo guardar el médico en el servidor. Intenta de nuevo.');
        },
      });
    }
  }

  validarHorario(): boolean {
    if (!this.nuevoMedico.horaInicio || !this.nuevoMedico.horaFin) {
      this.horarioInvalido = false;
      return false;
    }

    // Convertir horas a minutos para comparar
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

  /**
   * Permite solo letras (incluye acentos y ñ) y espacios al teclear.
   */
  allowOnlyLetters(event: KeyboardEvent): void {
    const key = event.key;
    const regex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]$/;
    if (!regex.test(key)) {
      event.preventDefault();
    }
  }

  /**
   * Maneja pegado: filtra caracteres no permitidos y asigna al modelo.
   */
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

  cerrarModal(): void {
    this.mostrarModalAgregar = false;
    this.resetForm();
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
  }

  getIniciales(nombreCompleto: string): string {
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 1 || index === 2)
      .map((nombre) => nombre[0])
      .join('')
      .toUpperCase();
  }
}
