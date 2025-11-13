import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

export interface Medico {
  id: number;
  firstName: string;
  secondName?: string;
  nombre?: string; // some frontend code uses 'nombre'
  especialidad: string;
  horario?: string;
  experiencia?: string;
  disponible?: boolean;
  costoConsulta?: number;
}

@Injectable({ providedIn: 'root' })
export class MedicoService {
  private baseUrl = 'http://localhost:8080/api/medicos';
  constructor(private http: HttpClient) {}

  findAll(): Observable<Medico[]> {
    return this.http.get<Medico[]>(this.baseUrl);
  }

  /**
   * Elimina un médico por id en el backend.
   * Devuelve Observable<boolean> indicando éxito (true) o fracaso (false).
   */
  deleteMedico(id: number): Observable<boolean> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`).pipe(
      map(() => true),
      catchError((err) => {
        console.error('Error eliminando medico', err);
        return of(false);
      })
    );
  }

  /**
   * Crea un nuevo médico en el backend.
   * Se espera un objeto con las propiedades usadas por el backend (firstName, secondName, especialidad, horario, costoConsulta?).
   * Devuelve el Medico creado (con id asignado).
   */
  createMedico(payload: any): Observable<Medico> {
    return this.http.post<Medico>(this.baseUrl, payload).pipe(
      catchError((err) => {
        console.error('Error creando medico', err);
        throw err;
      })
    );
  }
}
