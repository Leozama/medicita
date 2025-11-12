import { Routes } from '@angular/router';
import { ServiciosComponent } from './pages/servicios/servicios.component';
import { EspecialidadComponent } from './pages/especialidad/especialidad.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { 
    path: '', 
    component: ServiciosComponent,
    canActivate: [AuthGuard]  // Proteger la ruta principal
  },
  { 
    path: 'especialidad/:id', 
    component: EspecialidadComponent,
    canActivate: [AuthGuard]  // Proteger esta ruta también
  },
  { path: '**', redirectTo: '' }
];