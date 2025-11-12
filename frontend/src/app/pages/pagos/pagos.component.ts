// pages/pagos/pagos.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

interface Pago {
  id: number;
  paciente: string;
  email: string;
  telefono: string;
  medico: string;
  especialidad: string;
  fecha: string;
  referencia: string;
  fechaCita: string;
  horaCita: string;
  monto: number;
  estado: 'Completado' | 'Pendiente' | 'Fallido';
  metodoPago: string;
  idTransaccion: string;
}

@Component({
  selector: 'app-pagos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pagos.component.html'
})
export class PagosComponent {
  constructor(private router: Router) {}

  volverADashboard(): void {
    this.router.navigate(['/dashboard']);
  }

  pagos: Pago[] = [
    {
      id: 1,
      paciente: 'Juan Pérez',
      email: 'juan@email.com',
      telefono: '+1234567890',
      medico: 'Dr. Carlos Mendoza',
      especialidad: 'Cardiología',
      fecha: '2024-01-15',
      referencia: 'REF-001-2024',
      fechaCita: '2024-01-20',
      horaCita: '10:00 AM',
      monto: 150,
      estado: 'Completado',
      metodoPago: 'Tarjeta de Crédito',
      idTransaccion: 'TXN-001-2024'
    },
    {
      id: 2,
      paciente: 'María García',
      email: 'maria@email.com',
      telefono: '+1234567891',
      medico: 'Dra. Laura Martínez',
      especialidad: 'Neurología',
      fecha: '2024-01-16',
      referencia: 'REF-002-2024',
      fechaCita: '2024-01-22',
      horaCita: '11:00 AM',
      monto: 180,
      estado: 'Completado',
      metodoPago: 'PayPal',
      idTransaccion: 'TXN-002-2024'
    },
    {
      id: 3,
      paciente: 'Carlos López',
      email: 'carlos@email.com',
      telefono: '+1234567892',
      medico: 'Dr. Antonio García',
      especialidad: 'Traumatología',
      fecha: '2024-01-17',
      referencia: 'REF-003-2024',
      fechaCita: '2024-01-25',
      horaCita: '09:00 AM',
      monto: 200,
      estado: 'Pendiente',
      metodoPago: 'Transferencia',
      idTransaccion: 'TXN-003-2024'
    },
    {
      id: 4,
      paciente: 'Ana Rodríguez',
      email: 'ana@email.com',
      telefono: '+1234567893',
      medico: 'Dra. Isabel Rodríguez',
      especialidad: 'Traumatología',
      fecha: '2024-01-18',
      referencia: 'REF-004-2024',
      fechaCita: '2024-01-28',
      horaCita: '02:00 PM',
      monto: 200,
      estado: 'Fallido',
      metodoPago: 'Tarjeta de Débito',
      idTransaccion: 'TXN-004-2024'
    },
    {
      id: 5,
      paciente: 'Pedro Sánchez',
      email: 'pedro@email.com',
      telefono: '+1234567894',
      medico: 'Dr. Roberto Fernández',
      especialidad: 'Neurología',
      fecha: '2024-01-19',
      referencia: 'REF-005-2024',
      fechaCita: '2024-01-30',
      horaCita: '03:00 PM',
      monto: 180,
      estado: 'Pendiente',
      metodoPago: 'Efectivo',
      idTransaccion: 'TXN-005-2024'
    }
  ];

  cambiarEstadoPago(pago: Pago, event: any): void {
    const nuevoEstado = event.target.value as 'Completado' | 'Pendiente' | 'Fallido';
    pago.estado = nuevoEstado;
    
    // Aquí puedes agregar lógica adicional como guardar en una base de datos
    console.log(`Estado del pago ${pago.referencia} cambiado a: ${nuevoEstado}`);
    
    // Opcional: Mostrar mensaje de confirmación
    alert(`Estado del pago ${pago.referencia} actualizado a: ${nuevoEstado}`);
  }

  getIniciales(nombreCompleto: string): string {
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 0 || index === 1)
      .map(nombre => nombre[0])
      .join('')
      .toUpperCase();
  }

  getEstadoClass(estado: string): string {
    const clases = {
      'Completado': 'bg-green-100 text-green-800 border-green-300',
      'Pendiente': 'bg-yellow-100 text-yellow-800 border-yellow-300',
      'Fallido': 'bg-red-100 text-red-800 border-red-300'
    };
    return clases[estado as keyof typeof clases] || 'bg-gray-100 text-gray-800 border-gray-300';
  }
}