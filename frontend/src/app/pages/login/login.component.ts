import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

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
    // Si ya está logueado, redirigir a la página principal
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/']);
    }
  }

  onSubmit() {
    this.submitted = true;
    this.error = '';

    // Validacion basica
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
          // Login exitoso - redirigir a la página principal
          this.router.navigate(['/']);
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

  

  
}