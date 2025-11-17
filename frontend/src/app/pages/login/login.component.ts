// pages/login/login.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "login.component.html"
})
export class LoginComponent {
  credentials = {
    email: '',
    password: '',
    rememberMe: false
  };

  submitted = false;
  loading = false;
  error = '';
  showPassword = false;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {

    if (this.authService.isLoggedIn()) {
      this.redirectBasedOnUserType();
    }
  }

  onSubmit() {
    this.submitted = true;
    this.error = '';

    // Validación básica
    if (!this.credentials.email || !this.credentials.password) {
      this.error = 'Por favor completa todos los campos';
      return;
    }

    this.loading = true;

    // Usar el AuthService para login
    this.authService.login(this.credentials.email, this.credentials.password).subscribe({
      next: (success) => {
        this.loading = false;
        if (success) {
          // La redirección ahora se maneja automáticamente en el AuthService
          // según el tipo de usuario
        } else {
          this.error = 'Credenciales incorrectas o error de autenticación. Verifica usuario/contraseña.';
        }
      },
      error: (error) => {
        this.loading = false;
        this.error = 'Error de conexión. Por favor intenta nuevamente.';
      }
    });
  }

  private redirectBasedOnUserType() {
    const user = this.authService.getCurrentUser();
    if (user?.tipo === 'admin') {
      this.router.navigate(['/dashboard']);
    } else {
      this.router.navigate(['/']);
    }
  }

  irARegistro() {
  this.router.navigate(['/registro']);
  }

  
}