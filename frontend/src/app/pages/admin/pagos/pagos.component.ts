// pages/pagos/pagos.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Pago {
  pagoId?: number;
  paciente: string;
  email?: string;
  telefono?: string;
  medico?: string;
  especialidad?: string;
  fecha: string; // fecha del registro/creación o fechaCita
  referencia?: string;
  fechaCita?: string;
  horaCita?: string;
  monto?: number;
  estado: 'Completado' | 'Pendiente' | 'Fallido';
}

// DTO que responde el backend
interface PagoResponseDTO {
  pagoId: number;
  citaId: number;
  pacienteId: number;
  nombrePaciente: string;
  nombreMedico: string;
  especialidad: string;
  fechaCita: string;
  monto: number;
  estado: string; // PENDIENTE | PAGADO | CANCELADO
}

@Component({
  selector: 'app-pagos',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './pagos.component.html',
})
export class PagosComponent implements OnInit {
  private baseUrl = 'http://localhost:8080/api/pagos';

  pagos: Pago[] = [];

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    this.loadPagos();
  }

  volverADashboard(): void {
    this.router.navigate(['/dashboard']);
  }

  private mapDtoToPago(dto: PagoResponseDTO): Pago {
    // Mapear estado del backend a etiquetas UI
    const estadoMap: Record<string, Pago['estado']> = {
      PAGADO: 'Completado',
      PENDIENTE: 'Pendiente',
      CANCELADO: 'Fallido',
    };

    return {
      pagoId: dto.pagoId,
      paciente: dto.nombrePaciente || 'N/A',
      email: undefined,
      telefono: undefined,
      medico: dto.nombreMedico,
      especialidad: dto.especialidad,
      fecha: dto.fechaCita,
      referencia: undefined,
      fechaCita: dto.fechaCita,
      horaCita: undefined,
      monto: dto.monto,
      estado: estadoMap[dto.estado] || 'Pendiente',
    };
  }

  loadPagos() {
    this.http.get<PagoResponseDTO[]>(this.baseUrl).subscribe({
      next: (list) => {
        this.pagos = list.map((dto) => this.mapDtoToPago(dto));
      },
      error: (err) => {
        console.error('Error cargando pagos desde backend:', err);
        this.pagos = [];
      },
    });
  }

  cambiarEstadoPago(pago: Pago, event: any): void {
    const nuevoEstadoUI = event.target.value as 'Completado' | 'Pendiente' | 'Fallido';

    // Mapear a estado backend
    const backendMap: Record<string, string> = {
      Completado: 'PAGADO',
      Pendiente: 'PENDIENTE',
      Fallido: 'CANCELADO',
    };

    const estadoBackend = backendMap[nuevoEstadoUI];
    if (!pago.pagoId) {
      alert('Pago sin id, no se puede actualizar en backend');
      return;
    }

    this.http
      .put<any>(`${this.baseUrl}/${pago.pagoId}/estado`, { estado: estadoBackend })
      .subscribe({
        next: (resp) => {
          pago.estado = nuevoEstadoUI;
          alert(`Estado del pago actualizado a: ${nuevoEstadoUI}`);
        },
        error: (err) => {
          console.error('Error actualizando estado de pago en backend:', err);
          const msg = err?.error?.message || err?.message || 'Error actualizando estado';
          alert(`Error actualizando estado: ${msg}`);
        },
      });
  }

  getIniciales(nombreCompleto: string): string {
    return nombreCompleto
      .split(' ')
      .filter((_, index) => index === 0 || index === 1)
      .map((nombre) => nombre[0])
      .join('')
      .toUpperCase();
  }

  getEstadoClass(estado: string): string {
    const clases = {
      Completado: 'bg-green-100 text-green-800 border-green-300',
      Pendiente: 'bg-yellow-100 text-yellow-800 border-yellow-300',
      Fallido: 'bg-red-100 text-red-800 border-red-300',
    };
    return clases[estado as keyof typeof clases] || 'bg-gray-100 text-gray-800 border-gray-300';
  }
}
