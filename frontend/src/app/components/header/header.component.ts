// components/header/header.component.ts
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, User } from '../../services/auth.service';

interface Cita {
  id: number;
  nombreMedico: string;
  especialidad: string;
  fecha: string;
  horaAgendada: string;
  estado: string;
  monto?: number;
}

interface DatosPago {
  fechaPago: string;
  referencia: string;
}

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  showUserSidebar = false;
  currentUser: User | null = null;
  isLoggedIn = false;
  mostrarModalPago = false;
  citaSeleccionada: Cita | null = null;
  
  datosPago: DatosPago = {
    fechaPago: '',
    referencia: ''
  };

  // Datos de ejemplo para citas
  citas: Cita[] = [
    {
      id: 1,
      nombreMedico: 'Dr. Carlos Mendoza',
      especialidad: 'Cardiología',
      fecha: '2024-01-20',
      horaAgendada: '10:00 AM',
      estado: 'PENDIENTE_PAGO',
      monto: 150
    },
    {
      id: 2,
      nombreMedico: 'Dra. Laura Martínez',
      especialidad: 'Neurología',
      fecha: '2024-01-25',
      horaAgendada: '11:00 AM',
      estado: 'ACTIVA',
      monto: 180
    }
  ];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.isLoggedIn = !!user;
    });
  }

  getIniciales(): string {
    if (this.currentUser?.nombre) {
      return this.currentUser.nombre
        .split(' ')
        .filter((_, index) => index === 0 || index === 1)
        .map(nombre => nombre[0])
        .join('')
        .toUpperCase();
    }
    return 'US';
  }

  openUserSidebar() {
    this.showUserSidebar = true;
  }

  closeUserSidebar() {
    this.showUserSidebar = false;
    this.mostrarModalPago = false;
    this.citaSeleccionada = null;
    this.resetFormPago();
  }

  logout() {
    this.authService.logout();
    this.closeUserSidebar();
  }

  navigateToDashboard() {
    this.router.navigate(['/dashboard']);
    this.closeUserSidebar();
  }

  irALogin() {
    this.router.navigate(['/login']);
  }

  isLoginPage(): boolean {
    return this.router.url === '/login';
  }

  // Métodos para el modal de pago
  abrirModalPago(cita: Cita) {
    this.citaSeleccionada = cita;
    this.mostrarModalPago = true;
    // Establecer fecha actual por defecto
    this.datosPago.fechaPago = new Date().toISOString().split('T')[0];
  }

  cerrarModalPago() {
    this.mostrarModalPago = false;
    this.citaSeleccionada = null;
    this.resetFormPago();
  }

  procesarPago() {
    if (this.citaSeleccionada && this.datosPago.fechaPago && this.datosPago.referencia) {
      // Aquí iría la lógica para procesar el pago con el backend
      console.log('Procesando pago:', {
        cita: this.citaSeleccionada,
        datosPago: this.datosPago
      });

      // Simular procesamiento exitoso
      alert(`✅ Pago registrado exitosamente\nReferencia: ${this.datosPago.referencia}\nMonto: $${this.citaSeleccionada.monto || '150'}`);
      
      // Actualizar estado de la cita
      const citaIndex = this.citas.findIndex(c => c.id === this.citaSeleccionada!.id);
      if (citaIndex !== -1) {
        this.citas[citaIndex].estado = 'PAGADA';
      }

      this.cerrarModalPago();
      this.closeUserSidebar();
    }
  }

  cancelCita(citaId: number) {
    if (confirm('¿Estás seguro de que deseas cancelar esta cita?')) {
      const citaIndex = this.citas.findIndex(c => c.id === citaId);
      if (citaIndex !== -1) {
        this.citas[citaIndex].estado = 'CANCELADA';
        alert('✅ Cita cancelada exitosamente');
      }
    }
  }

  private resetFormPago() {
    this.datosPago = {
      fechaPago: '',
      referencia: ''
    };
  }
}