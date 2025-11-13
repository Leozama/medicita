// components/header/header.component.ts
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, User } from '../../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { CitaService, CitaResponseDTO } from '../../services/cita.service';

interface Cita {
  id: number;
  nombreMedico: string;
  especialidad: string;
  fecha: string;
  horaAgendada: string;
  estado: string;
  monto?: number;
}

// ya no se usa interfaz de datos del modal de pago

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
  // Ya no usamos modal; solo botón de pagar que llama al backend

  // Lista de citas cargada desde backend
  citas: Cita[] = [];

  private pagosBase = 'http://localhost:8080/api/pagos';

  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient,
    private citaService: CitaService
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe((user) => {
      this.currentUser = user;
      this.isLoggedIn = !!user;

      // Si es paciente, pedir al servicio que refresque las citas desde backend
      if (user && user.tipo === 'paciente') {
        // refresh hará la petición al backend y emitirá en citasPaciente$
        this.citaService.refreshCitasPaciente(user.id);
        this.citaService.citasPaciente$.subscribe((list: CitaResponseDTO[]) => {
          // Mapear la respuesta del backend al tipo local `Cita`
          this.citas = list.map((l: CitaResponseDTO) => ({
            id: l.id,
            nombreMedico: l.nombreMedico,
            especialidad: l.especialidad,
            fecha: l.fecha,
            horaAgendada: l.horaAgendada,
            estado: l.estado || 'ACTIVA',
            monto: (l as any).monto || undefined,
          }));
        });
      } else {
        // No es paciente: no mostrar citas
        this.citas = [];
      }
    });
  }

  getIniciales(): string {
    if (this.currentUser?.nombre) {
      return this.currentUser.nombre
        .split(' ')
        .filter((_, index) => index === 0 || index === 1)
        .map((nombre) => nombre[0])
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

  pagar(cita: Cita) {
    if (!cita || !cita.id) return;

    // Crear el pago directamente en estado PENDIENTE (backend usará fecha y monto de la cita)
    const request = { citaId: cita.id, estado: 'PENDIENTE' };
    this.http.post<any>(this.pagosBase, request).subscribe({
      next: (created) => {
        // Actualizar estado de la cita localmente
        const idx = this.citas.findIndex((c) => c.id === cita.id);
        if (idx !== -1) {
          this.citas[idx].estado = 'PENDIENTE';
        }
        alert('Pago registrado y marcado como PENDIENTE');
        this.closeUserSidebar();
      },
      error: (err) => {
        console.error('Error creando pago en backend:', err);
        const msg = err?.error?.message || err?.message || 'No se pudo procesar el pago.';
        alert(`Error creando pago: ${msg}`);
      },
    });
  }

  cancelCita(citaId: number) {
    if (confirm('¿Estás seguro de que deseas cancelar esta cita?')) {
      const citaIndex = this.citas.findIndex((c) => c.id === citaId);
      if (citaIndex !== -1) {
        this.citas[citaIndex].estado = 'CANCELADA';
        alert('✅ Cita cancelada exitosamente');
      }
    }
  }

  // ya no hay formulario de pago
}
