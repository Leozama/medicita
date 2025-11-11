import { Routes } from '@angular/router';
import { ServiciosComponent } from './pages/servicios/servicios.component';
import { EspecialidadComponent } from './pages/especialidad/especialidad.component';

export const routes: Routes = [
  { path: '', component: ServiciosComponent }, // ← Servicios como página principal
  { path: 'especialidad/:id', component: EspecialidadComponent },
  { path: '**', redirectTo: '' }
];