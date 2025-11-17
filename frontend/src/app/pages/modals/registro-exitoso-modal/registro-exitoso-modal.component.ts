import { Component, EventEmitter, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registro-exitoso-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './registro-exitoso-modal.component.html'
})
export class RegistroExitosoModalComponent {
  @Input() mostrarModal: boolean = false;
  @Output() cerrarModal = new EventEmitter<void>();

  onCerrar() {
    this.cerrarModal.emit();
  }
}