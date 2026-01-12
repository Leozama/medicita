// app.routes.ts
import { Routes } from '@angular/router';
import { EspecialidadesComponent } from './pacientes/especialidades.component';
import { MedicosComponent } from './pacientes/medicos.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { DashboardComponent } from './admin/dashboard.component';
import { AdminGuard } from './guards/admin.guard';
import { PagosComponent } from './admin/pagos.component';
import { RegistroComponent } from './login/registro.component';



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