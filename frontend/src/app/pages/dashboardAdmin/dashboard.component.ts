// pages/dashboardAdmin/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, User } from '../../services/auth.service';

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
  especialidad: string;
  experiencia: string;
  disponible: boolean;
  horaInicio: string;
  horaFin: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  medicoSeleccionado: Medico | null = null;
  mostrarModalAgregar = false;
  
  nuevoMedico: NuevoMedico = {
    nombre: '',
    especialidad: '',
    experiencia: '',
    disponible: true,
    horaInicio: '',
    horaFin: ''
  };

  horasDisponibles: string[] = [
    '07:00', '08:00', '09:00', '10:00', '11:00', 
    '12:00', '13:00', '14:00', '15:00', '16:00', 
    '17:00', '18:00'
  ];

  medicos: Medico[] = [
    {
      id: 1,
      nombre: 'Dr. Carlos Mendoza',
      especialidad: 'Cardiología',
      experiencia: '15 años',
      disponible: true,
      horario: '08:00 - 16:00'
    },
    {
      id: 2,
      nombre: 'Dra. Laura Martínez',
      especialidad: 'Neurología',
      experiencia: '10 años',
      disponible: true,
      horario: '09:00 - 17:00'
    },
    {
      id: 3,
      nombre: 'Dra. María Santos',
      especialidad: 'Cardiología',
      experiencia: '12 años',
      disponible: true,
      horario: '08:00 - 15:00'
    },
    {
      id: 4,
      nombre: 'Dr. Antonio García',
      especialidad: 'Traumatología',
      experiencia: '20 años',
      disponible: true,
      horario: '10:00 - 18:00'
    },
    {
      id: 5,
      nombre: 'Dr. Roberto Fernández',
      especialidad: 'Neurología',
      experiencia: '18 años',
      disponible: true,
      horario: '07:00 - 14:00'
    },
    {
      id: 6,
      nombre: 'Dra. Isabel Rodríguez',
      especialidad: 'Traumatología',
      experiencia: '14 años',
      disponible: true,
      horario: '08:30 - 16:30'
    },
    {
      id: 7,
      nombre: 'Dr. Fernando Ramírez',
      especialidad: 'Endocrinología',
      experiencia: '19 años',
      disponible: true,
      horario: '09:00 - 17:00'
    }
  ];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    
    if (!this.currentUser || this.currentUser.tipo !== 'admin') {
      this.router.navigate(['/login']);
      return;
    }
  }

  seleccionarMedico(medico: Medico): void {
    this.medicoSeleccionado = this.medicoSeleccionado?.id === medico.id ? null : medico;
  }

  eliminarMedicoSeleccionado(): void {
    if (this.medicoSeleccionado) {
      if (confirm(`¿Estás seguro de eliminar a ${this.medicoSeleccionado.nombre}?`)) {
        this.medicos = this.medicos.filter(m => m.id !== this.medicoSeleccionado!.id);
        this.medicoSeleccionado = null;
      }
    }
  }

  guardarMedico(): void {
    if (this.nuevoMedico.nombre && this.nuevoMedico.especialidad && this.nuevoMedico.experiencia && this.nuevoMedico.horaInicio && this.nuevoMedico.horaFin) {
      const nuevoId = this.medicos.length > 0 ? Math.max(...this.medicos.map(m => m.id)) + 1 : 1;
      
      const medico: Medico = {
        id: nuevoId,
        nombre: this.nuevoMedico.nombre,
        especialidad: this.nuevoMedico.especialidad,
        experiencia: `${this.nuevoMedico.experiencia} años`,
        disponible: this.nuevoMedico.disponible,
        horario: `${this.nuevoMedico.horaInicio} - ${this.nuevoMedico.horaFin}`
      };

      this.medicos.push(medico);
      this.cerrarModal();
      
      // Mostrar mensaje de éxito
      alert(`Médico ${medico.nombre} agregado exitosamente`);
    }
  }

  validarHorario(): boolean {
    if (!this.nuevoMedico.horaInicio || !this.nuevoMedico.horaFin) {
      return false;
    }
    
    // Convertir horas a minutos para comparar
    const inicioMinutos = this.horaAMinutos(this.nuevoMedico.horaInicio);
    const finMinutos = this.horaAMinutos(this.nuevoMedico.horaFin);
    
    return finMinutos > inicioMinutos;
  }

  horaAMinutos(hora: string): number {
    const [horas, minutos] = hora.split(':').map(Number);
    return horas * 60 + minutos;
  }

  cerrarModal(): void {
    this.mostrarModalAgregar = false;
    this.resetForm();
  }

  resetForm(): void {
    this.nuevoMedico = {
      nombre: '',
      especialidad: '',
      experiencia: '',
      disponible: true,
      horaInicio: '',
      horaFin: ''
    };
  }

  getIniciales(nombreCompleto: string): string {
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 1 || index === 2)
      .map(nombre => nombre[0])
      .join('')
      .toUpperCase();
  }
}