//Interfaz de Login
import { Component } from '@angular/core';

@Component({
  selector: 'app-view-registrar-perfil',
  templateUrl: './view-registrar-perfil.component.html',
  styleUrls: ['./view-registrar-perfil.component.css']
})
export class ViewRegistrarPerfilComponent {
  email: string = '';
  password: string = '';

  // Método para manejar el envío del formulario
  handleSubmit() {
    // Evita recargar la página
    event?.preventDefault();

    // Verifica si el usuario es admin o paciente
    if (this.email === 'admin' && this.password === '1234') {
      alert('Inicio de sesión como Administrador');
    } else {
      alert('Inicio de sesión como Paciente');
    }
  }

  // Cambiar a vista de registro (puedes personalizarlo)
  switchToRegister() {
    alert('Redirigiendo a creación de perfil...');
  }
}

