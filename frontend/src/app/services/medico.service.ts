import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Medico {
  id: number;
  firstName: string;
  secondName?: string;
  nombre?: string; // some frontend code uses 'nombre'
  especialidad: string;
  experiencia?: string;
  disponible?: boolean;
}

@Injectable({ providedIn: 'root' })
export class MedicoService {
  private baseUrl = 'http://localhost:8080/api/medicos';
  constructor(private http: HttpClient) {}

  findAll(): Observable<Medico[]> {
    return this.http.get<Medico[]>(this.baseUrl);
  }
}
