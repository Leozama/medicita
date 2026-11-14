import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';

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
  private baseUrl = `${environment.apiUrl}/medicos`;
  constructor(private http: HttpClient) { }

  findById(id: number): Observable<Medico | null> {
    return this.http.get<Medico>(`${this.baseUrl}/${id}`).pipe(
      catchError((err) => {
        console.error('Error obteniendo medico por id', err);
        return of(null);
      })
    );
  }

  findAll(): Observable<Medico[]> {
    return this.http.get<Medico[]>(this.baseUrl);
  }

  // VULNERABILIDAD SQL INJECTION: llama al endpoint de búsqueda vulnerable del backend
  buscarInseguro(esp: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/buscar?esp=${encodeURIComponent(esp)}`);
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

  /**
   * Actualiza un médico existente en el backend.
   * Se espera un objeto con las propiedades usadas por el backend (firstName, secondName, especialidad, horario, costoConsulta?).
   * Devuelve el Medico actualizado.
   */
  updateMedico(id: number, payload: any): Observable<Medico> {
    return this.http.put<Medico>(`${this.baseUrl}/${id}`, payload).pipe(
      catchError((err) => {
        console.error('Error actualizando medico', err);
        throw err;
      })
    );
  }
}
