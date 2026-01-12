import { Component } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';
import { HeaderComponent } from './shared/header.component';
import { FooterComponent } from './shared/footer.component';
import { CitaModalComponent } from './modals/cita-modal.component';
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

  constructor(private router: Router) { }

  isLoginPage(): boolean {
    return this.router.url === '/login';
  }
}