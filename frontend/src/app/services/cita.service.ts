// services/cita.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { AuthService } from './auth.service';

export interface CitaData {
  medicoId: number;
  medicoNombre: string;
  especialidad: string;
  fecha: string;
  hora: string;
  costo: number;
}

export interface CitaResponseDTO {
  id: number;
  nombreMedico: string;
  especialidad: string;
  nombrePaciente: string;
  emailPaciente: string;
  horaAgendada: string;
  motivo: string;
  fecha: string;
}

interface CitaRequestDTO {
  medicoId: number;
  pacienteId: number;
  horaAgendada: string;
  motivo: string;
  fecha: string;
}

@Injectable({
  providedIn: 'root'
})
export class CitaService {
  private mostrarModalSource = new BehaviorSubject<boolean>(false);
  private citaDataSource = new BehaviorSubject<CitaData | null>(null);
  private citasPacienteSource = new BehaviorSubject<CitaResponseDTO[]>([]);

  mostrarModal$ = this.mostrarModalSource.asObservable();
  citaData$ = this.citaDataSource.asObservable();
  citasPaciente$ = this.citasPacienteSource.asObservable();

  // Base URL del backend
  private baseUrl = 'http://localhost:8080/api/citas';

  constructor(private http: HttpClient, private authService: AuthService) {}

  abrirModal(medicoId: number, medicoNombre: string, especialidad: string) {
     console.log('🟢 SERVICIO: Abriendo modal para:', medicoNombre);

    this.citaDataSource.next({
      medicoId,
      medicoNombre,
      especialidad,
      fecha: '',
      hora: '',
      costo: 150 // Costo base
    });
    this.mostrarModalSource.next(true);
  }

  cerrarModal() {
    console.log('🟢 SERVICIO: Cerrando modal');
    this.mostrarModalSource.next(false);
    this.citaDataSource.next(null);
  }

  /**
   * Confirma la cita y la crea en el backend usando DTO
   */
  confirmarCitaBackend(citaData: CitaData, motivo = 'Consulta general'): Observable<boolean> {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      console.error('Usuario no autenticado');
      return of(false);
    }

    const payload: CitaRequestDTO = {
      medicoId: citaData.medicoId,
      pacienteId: currentUser.id,
      horaAgendada: citaData.hora,
      motivo: motivo,
      fecha: citaData.fecha
    };

    return this.http.post<any>(`${this.baseUrl}/dto`, payload).pipe(
      map(resp => {
        console.log('Cita creada backend:', resp);
        // Después de crear la cita, refrescar la lista de citas del paciente
        this.refreshCitasPaciente(currentUser.id);
        return true;
      }),
      catchError(err => {
        console.error('Error creando cita:', err);
        return of(false);
      })
    );
  }

  /** Obtener citas del paciente desde backend y emitir en el subject */
  getCitasByPaciente(pacienteId: number) {
    return this.http.get<CitaResponseDTO[]>(`${this.baseUrl}/paciente/${pacienteId}`).pipe(
      catchError(err => {
        console.error('Error obteniendo citas del paciente:', err);
        return of([] as CitaResponseDTO[]);
      })
    );
  }

  refreshCitasPaciente(pacienteId: number) {
    this.getCitasByPaciente(pacienteId).subscribe(list => this.citasPacienteSource.next(list));
  }

  /** Eliminar una cita por id y refrescar la lista del paciente */
  deleteCita(citaId: number, pacienteId: number): Observable<boolean> {
    return this.http.delete<void>(`${this.baseUrl}/${citaId}`).pipe(
      map(() => {
        // refrescar lista
        this.refreshCitasPaciente(pacienteId);
        return true;
      }),
      catchError(err => {
        console.error('Error eliminando cita:', err);
        return of(false);
      })
    );
  }

  confirmarCita(citaData: CitaData) {
    console.log('🟢 SERVICIO: Confirmando cita (local):', citaData);
    this.cerrarModal();
    alert(`✅ Cita confirmada!\n\nMédico: ${citaData.medicoNombre}\nFecha: ${citaData.fecha}\nHora: ${citaData.hora}\nCosto: $${citaData.costo}`);
  }
}