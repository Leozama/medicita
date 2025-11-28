// shared/components/layout/header/header.component.ts
import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, User } from '../../core/services/auth.service';
import { HttpClient } from '@angular/common/http';
import { CitaService, CitaResponseDTO } from '../../core/services/cita.service';
import { UserSidebarComponent } from '../sidebar/user-sidebar.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, UserSidebarComponent],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  showUserSidebar = false;
  currentUser: User | null = null;
  isLoggedIn = false;

  // Lista de citas cargada desde backend
  citas: any[] = [];

  private pagosBase = 'http://localhost:8080/api/pagos';
  private pacientesBase = 'http://localhost:8080/api/pacientes';

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

      console.log('🔍 DEBUG - Usuario desde AuthService:', user);
    console.log('🔍 DEBUG - Email:', user?.email);
    console.log('🔍 DEBUG - Username:', user?.username);
    console.log('🔍 DEBUG - Tipo:', user?.tipo);

      // Si es paciente, cargar información completa y citas
      if (user && user.tipo === 'paciente') {
        this.cargarInformacionCompletaPaciente(user.id);
        this.cargarCitasPaciente(user.id);
      } else {
        this.citas = [];
      }

      



    });
  }

  private cargarInformacionCompletaPaciente(pacienteId: number) {
    // Llamar al backend para obtener información completa del paciente
    this.http.get<any>(`${this.pacientesBase}/${pacienteId}`).subscribe({
      next: (pacienteInfo) => {
        // Actualizar el usuario actual con la información completa
        if (this.currentUser) {
          this.currentUser = {
            ...this.currentUser,
            email: pacienteInfo.email,
            telefono: pacienteInfo.telefono,
            username: pacienteInfo.userName,
           
          };
        }
        console.log('Información completa del paciente cargada:', pacienteInfo);
      },
      error: (err) => {
        console.error('Error cargando información del paciente:', err);
        // Si falla, usar información básica
        if (this.currentUser) {
          this.currentUser.telefono = 'No disponible';
          this.currentUser.username = this.currentUser.email.split('@')[0];
        }
      }
    });
  }

  private cargarCitasPaciente(pacienteId: number) {
    this.citaService.refreshCitasPaciente(pacienteId);
    this.citaService.citasPaciente$.subscribe((list: CitaResponseDTO[]) => {
      this.citas = list.map((l: CitaResponseDTO) => ({
        id: l.id,
        nombreMedico: l.nombreMedico,
        especialidad: l.especialidad,
        fecha: l.fecha,
        horaAgendada: l.horaAgendada,
        estado: l.estado || 'ACTIVA',
        monto: (l as any).monto || 150,
      }));
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

  pagar(cita: any) {
    if (!cita || !cita.id) return;

    const request = { citaId: cita.id, estado: 'PENDIENTE' };
    this.http.post<any>(this.pagosBase, request).subscribe({
      next: (created) => {
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
}