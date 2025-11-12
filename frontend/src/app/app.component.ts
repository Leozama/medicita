import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { CitaModalComponent } from './components/cita-modal/cita-modal.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HeaderComponent, FooterComponent, CitaModalComponent],
  template: `
    <div class="min-h-screen flex flex-col">
      <!-- Header y Footer solo se muestran cuando NO estamos en login -->
      <app-header *ngIf="!isLoginPage()"></app-header>
      
      <main class="flex-grow">
        <router-outlet></router-outlet>
      </main>
      
      <app-footer *ngIf="!isLoginPage()"></app-footer>
      
      <app-cita-modal></app-cita-modal>
    </div>
  `
})
export class AppComponent {
  isLoginPage(): boolean {
    if (typeof window !== 'undefined') {
      return window.location.pathname === '/login';
    }
    return false;
  }
}