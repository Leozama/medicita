// services/cita.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { MedicoService } from './medico.service';

export interface CitaData {
  medicoId: number;
  medicoNombre: string;
  especialidad: string;
  fecha: string;
  hora: string;
  costo: number;
  horasDisponibles?: string[];
}

export interface CitaResponseDTO {
  id: number;
  nombreMedico: string;
  especialidad: string;
  nombrePaciente: string;
  emailPaciente: string;
  horaAgendada: string;
  motivo: string;
  estado?: string;
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
  providedIn: 'root',
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

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private medicoService: MedicoService
  ) {}

  abrirModal(medicoId: number, medicoNombre: string, especialidad: string) {
    console.log('🟢 SERVICIO: Abriendo modal para:', medicoNombre);

    // Intentar obtener horario del médico desde backend para generar horas disponibles
    this.medicoService.findById(medicoId).subscribe(
      (medico) => {
        let horas: string[] = [
          '08:00',
          '09:00',
          '10:00',
          '11:00',
          '14:00',
          '15:00',
          '16:00',
          '17:00',
        ];

        if (medico && medico.horario) {
          try {
            const parts = medico.horario.split('-').map((p) => p.trim());
            if (parts.length === 2) {
              const [start, end] = parts;
              const startHour = parseInt(start.split(':')[0], 10);
              const endHour = parseInt(end.split(':')[0], 10);
              if (!isNaN(startHour) && !isNaN(endHour) && endHour >= startHour) {
                horas = [];
                for (let h = startHour; h <= endHour; h++) {
                  const hh = h.toString().padStart(2, '0') + ':00';
                  horas.push(hh);
                }
              }
            }
          } catch (e) {
            console.warn(
              'No fue posible parsear horario del médico, usando horario por defecto',
              e
            );
          }
        }

        this.citaDataSource.next({
          medicoId,
          medicoNombre,
          especialidad,
          fecha: '',
          hora: '',
          costo: 150, // Costo base
          horasDisponibles: horas,
        });
        this.mostrarModalSource.next(true);
      },
      (err) => {
        // En caso de error al obtener médico, usar horas por defecto
        this.citaDataSource.next({
          medicoId,
          medicoNombre,
          especialidad,
          fecha: '',
          hora: '',
          costo: 150,
          horasDisponibles: [
            '08:00',
            '09:00',
            '10:00',
            '11:00',
            '14:00',
            '15:00',
            '16:00',
            '17:00',
          ],
        });
        this.mostrarModalSource.next(true);
      }
    );
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
      fecha: citaData.fecha,
    };

    // No hacer catch aquí para que el componente pueda manejar errores específicos (p.ej. 409)
    return this.http.post<any>(`${this.baseUrl}/dto`, payload).pipe(
      map((resp) => {
        console.log('Cita creada backend:', resp);
        // Después de crear la cita, refrescar la lista de citas del paciente
        this.refreshCitasPaciente(currentUser.id);
        return true;
      })
    );
  }

  /** Obtener citas del paciente desde backend y emitir en el subject */
  getCitasByPaciente(pacienteId: number) {
    return this.http.get<CitaResponseDTO[]>(`${this.baseUrl}/paciente/${pacienteId}`).pipe(
      catchError((err) => {
        console.error('Error obteniendo citas del paciente:', err);
        return of([] as CitaResponseDTO[]);
      })
    );
  }

  refreshCitasPaciente(pacienteId: number) {
    this.getCitasByPaciente(pacienteId).subscribe((list) => this.citasPacienteSource.next(list));
  }

  /** Marcar cita como CANCELADA y refrescar la lista del paciente */
  deleteCita(citaId: number, pacienteId: number): Observable<boolean> {
    const payload = { estado: 'CANCELADA' };
    return this.http.put<any>(`${this.baseUrl}/dto/${citaId}/estado`, payload).pipe(
      map(() => {
        this.refreshCitasPaciente(pacienteId);
        return true;
      }),
      catchError((err) => {
        console.error('Error actualizando estado de la cita:', err);
        return of(false);
      })
    );
  }

  confirmarCita(citaData: CitaData) {
    console.log('🟢 SERVICIO: Confirmando cita (local):', citaData);
    this.cerrarModal();
    alert(
      `✅ Cita confirmada!\n\nMédico: ${citaData.medicoNombre}\nFecha: ${citaData.fecha}\nHora: ${citaData.hora}\nCosto: $${citaData.costo}`
    );
  }
}
