// shared/components/layout/user-sidebar/user-sidebar.component.ts
import { Component, EventEmitter, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

export interface Cita {
  id: number;
  nombreMedico: string;
  especialidad: string;
  fecha: string;
  horaAgendada: string;
  estado: string;
  monto?: number;
}

export interface User {
  id: number;
  email: string;
  nombre: string;
  tipo: 'paciente' | 'admin';
  telefono?: string;
  username?: string; 
}

@Component({
  selector: 'app-user-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl:'./user-sidebar.component.html',
})
export class UserSidebarComponent {
  @Input() isOpen: boolean = false;
  @Input() currentUser: User | null = null;
  @Input() citas: Cita[] = [];
  
  @Output() closeSidebar = new EventEmitter<void>();
  @Output() pagarCita = new EventEmitter<Cita>();
  @Output() cancelarCita = new EventEmitter<number>();

  onClose() {
    this.closeSidebar.emit();
  }

  onPagar(cita: Cita) {
    this.pagarCita.emit(cita);
  }

  onCancelar(citaId: number) {
    this.cancelarCita.emit(citaId);
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

  // Método auxiliar para las clases de estado
  getEstadoClass(estado: string): string {
    const clases = {
      'ACTIVA': 'bg-green-100 text-green-800',
      'PENDIENTE': 'bg-yellow-100 text-yellow-800',
      'CANCELADA': 'bg-red-100 text-red-800',
      'COMPLETADA': 'bg-blue-100 text-blue-800'
    };
    return clases[estado as keyof typeof clases] || 'bg-gray-100 text-gray-800';
  }
}