// app.component.ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { CitaModalComponent } from './components/cita-modal/cita-modal.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, CitaModalComponent],
  template: `
    <div class="min-h-screen flex flex-col">
      <app-header></app-header>
      <main class="flex-grow">
        <router-outlet></router-outlet>
      </main>
      <app-footer></app-footer>
      <!-- MODAL GLOBAL -->
      <app-cita-modal></app-cita-modal>
    </div>
  `
})
export class AppComponent {
  title = 'Centro Médico';
}