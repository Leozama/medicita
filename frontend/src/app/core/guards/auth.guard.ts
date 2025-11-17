// guards/auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    const user = this.authService.getCurrentUser();
    
    if (user) {
      // Si es admin, redirigir al dashboard
      if (user.tipo === 'admin') {
        this.router.navigate(['/dashboard']);
        return false;
      }
      // Si es paciente, permitir acceso a la página principal
      return true;
    } else {
      // Si no está logueado, redirigir al login
      this.router.navigate(['/login']);
      return false;
    }
  }
}