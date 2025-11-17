// app.routes.ts
import { Routes } from '@angular/router';
import { EspecialidadesComponent } from './pages/pacientes/especialidades/especialidades.component';
import { MedicosComponent } from './pages/pacientes/medicos/medicos.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/guards/auth.guard';
import { DashboardComponent } from './pages/admin/inicio/dashboard.component';
import { AdminGuard } from './core/guards/admin.guard';
import { PagosComponent } from './pages/admin/pagos/pagos.component'; 
import { RegistroComponent } from './pages/login/registro/registro.component'; 



export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { 
    path: '', 
    component: EspecialidadesComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [AdminGuard]
  },
  { 
    path: 'pagos', 
    component: PagosComponent, 
    canActivate: [AdminGuard] // Solo admin puede ver pagos
  },
  { 
    path: 'medicos/:id', 
    component: MedicosComponent,
    canActivate: [AuthGuard]
  },
  { path: '**', redirectTo: '' }
];