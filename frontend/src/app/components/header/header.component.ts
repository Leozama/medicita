import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService, User } from '../../services/auth.service';
import { CitaService, CitaResponseDTO } from '../../services/cita.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './header.component.html',
  
})
export class HeaderComponent implements OnInit {
  showUserSidebar = false;
  currentUser: User | null = null;
  isLoggedIn = false;
  citas: CitaResponseDTO[] = [];

  constructor(private authService: AuthService, private citaService: CitaService) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.isLoggedIn = !!user;
      if (user && user.tipo === 'paciente') {
        // cargar citas del paciente
        this.citaService.refreshCitasPaciente(user.id);
        this.citaService.citasPaciente$.subscribe(list => this.citas = list || []);
      } else {
        this.citas = [];
      }
    });
  }

  cancelCita(citaId: number) {
    if (!this.currentUser) return;
    const ok = confirm('¿Estás seguro que quieres cancelar esta cita?');
    if (!ok) return;
    this.citaService.deleteCita(citaId, this.currentUser.id).subscribe(success => {
      if (success) {
        alert('Cita cancelada correctamente');
      } else {
        alert('No se pudo cancelar la cita. Intenta nuevamente.');
      }
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
  }

  logout() {
    this.authService.logout();
    this.closeUserSidebar();
  }
}