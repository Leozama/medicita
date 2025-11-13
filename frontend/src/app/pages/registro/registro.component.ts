// pages/registro/registro.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

interface UsuarioRegistro {
  nombre: string;
  apellido: string;
  email: string;
  fechaNacimiento: string;
  password: string;
  telefono: string;
  username: string;
}

interface RegistroResponse {
  userId?: number;
  mensaje?: string;
  error?: string;
}

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro.component.html'
})
export class RegistroComponent {
  usuario: UsuarioRegistro = {
    nombre: '',
    apellido: '',
    email: '',
    fechaNacimiento: '',
    password: '',
    telefono: '',
    username: ''
  };

  submitted = false;
  loading = false;
  error = '';
  showPassword = false;
  mostrarPopupExito = false;

  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(
    private router: Router,
    private http: HttpClient
  ) {}

  onSubmit() {
    this.submitted = true;
    this.error = '';

    // Validaciones del frontend
    if (!this.usuario.nombre || !this.usuario.apellido || !this.usuario.email || 
        !this.usuario.fechaNacimiento || !this.usuario.password ||
        !this.usuario.telefono || !this.usuario.username) {
      this.error = 'Por favor completa todos los campos';
      return;
    }

    if (this.usuario.password.length < 6) {
      this.error = 'La contraseña debe tener al menos 6 caracteres';
      return;
    }

    this.loading = true;

    // Preparar payload para el backend - combinar nombre y apellido
    const payload = {
      nombreCompleto: `${this.usuario.nombre} ${this.usuario.apellido}`,
      email: this.usuario.email,
      telefono: this.usuario.telefono,
      fechaNacimiento: this.usuario.fechaNacimiento,
      password: this.usuario.password,
      username: this.usuario.username
    };

    console.log('Enviando registro al backend:', payload);
    console.log('URL:', `${this.baseUrl}/registro`);

    // Llamar al endpoint de registro del backend
    this.http.post<RegistroResponse>(`${this.baseUrl}/registro`, payload)
      .subscribe({
        next: (response) => {
          this.loading = false;
          console.log('Respuesta del backend:', response);
          
          if (response.userId) {
            // Registro exitoso - Mostrar popup en lugar de alert
            this.mostrarPopupExito = true;
          } else {
            this.error = response.mensaje || 'Error al crear la cuenta';
          }
        },
        error: (error: HttpErrorResponse) => {
          this.loading = false;
          console.error('Error completo del backend:', error);
          console.error('Status:', error.status);
          console.error('Error message:', error.message);
          console.error('Error body:', error.error);
          
          if (error.status === 0) {
            // Error de conexión (CORS, servidor caído, etc.)
            this.error = 'No se pudo conectar con el servidor. Verifica que el backend esté ejecutándose en http://localhost:8080';
          } else if (error.status === 400) {
            // Error de validación
            this.error = error.error?.mensaje || 'Datos inválidos. Verifica la información ingresada.';
          } else if (error.status === 409) {
            // Usuario ya existe
            this.error = error.error?.mensaje || 'El usuario ya existe. Intenta con un email o username diferente.';
          } else if (error.status === 500) {
            // Error interno del servidor
            this.error = 'Error interno del servidor. Por favor intenta más tarde.';
          } else {
            this.error = `Error inesperado: ${error.status} - ${error.message}`;
          }
        }
      });
  }

  // Método para cerrar el popup y redirigir al login
  cerrarPopupYRedirigir() {
    this.mostrarPopupExito = false;
    this.router.navigate(['/login']);
  }

  irALogin() {
    this.router.navigate(['/login']);
  }

  // Método para probar la conexión con el backend
  probarConexion() {
    console.log('Probando conexión con el backend...');
    this.http.get(`${this.baseUrl}/health`).subscribe({
      next: (response) => {
        console.log('Conexión exitosa:', response);
        alert('✅ Conexión con el backend exitosa');
      },
      error: (error) => {
        console.error('Error de conexión:', error);
        alert('❌ No se pudo conectar con el backend');
      }
    });
  }
}