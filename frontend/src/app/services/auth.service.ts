import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';

export interface User {
  id: number;
  email: string;
  nombre: string;
  tipo: 'paciente' | 'admin';
}

interface LoginResponse {
  userId?: number;
  nombreCompleto?: string;
  rol?: string;
  mensaje?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Change this baseUrl if your backend runs on a different host/port
  private baseUrl = 'http://localhost:8080/api/auth';

  private currentUserSubject = new BehaviorSubject<User | null>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private router: Router, private http: HttpClient) {}

  /**
   * Realiza el login llamando al backend POST /api/auth/login
   * Mapea la respuesta a un User y lo guarda en localStorage.
   * Devuelve Observable<boolean> indicando éxito o fracaso.
   */
  login(email: string, password: string): Observable<boolean> {
    const payload = { userName: email, password };
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, payload).pipe(
      map(resp => {
        if (resp && resp.userId) {
          const user: User = {
            id: resp.userId,
            email,
            nombre: resp.nombreCompleto || email,
            tipo: resp.rol === 'ADMIN' ? 'admin' : 'paciente'
          };
          this.setUser(user);
          return true;
        }
        return false;
      }),
      catchError(err => {
        // Si el backend responde 401 significa credenciales inválidas -> login fallido esperado
        if (err && err.status === 401) {
          return of(false);
        }
        // Para otros errores (network, 500, etc.) sí los dejamos en la consola para depurar
        console.error('AuthService login error', err);
        return of(false);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null;
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  private setUser(user: User): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  private getUserFromStorage(): User | null {
    if (typeof window !== 'undefined') {
      const userStr = localStorage.getItem('currentUser');
      return userStr ? JSON.parse(userStr) : null;
    }
    return null;
  }
}