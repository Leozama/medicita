import { Component } from '@angular/core';

@Component({
  selector: 'app-view-consultar-perfil',
  templateUrl: './view-consultar-perfil.component.html',
  styleUrls: ['./view-consultar-perfil.component.css']
})
export class ViewConsultarPerfilComponent {
  // Variables de datos
  isOpen: boolean = true; // Puedes controlarlo desde otro componente si quieres
  username: string = 'Administrador';
  password: string = 'admin123';
  showPassword: boolean = false;
  profilePhoto: string | null = null;

  // Cierra el diálogo
  onClose(): void {
    this.isOpen = false;
  }

  // Maneja el cambio de archivo
  handleFileChange(event: any): void {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        this.profilePhoto = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  // Alternar visibilidad de la contraseña
  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
}
