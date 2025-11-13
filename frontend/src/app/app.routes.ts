// app.routes.ts
import { Routes } from '@angular/router';
import { ServiciosComponent } from './pages/servicios/servicios.component';
import { EspecialidadComponent } from './pages/especialidad/especialidad.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { DashboardComponent } from './pages/dashboardAdmin/dashboard.component';
import { AdminGuard } from './guards/admin.guard';
import { PagosComponent } from './pages/pagos/pagos.component';
import { RegistroComponent } from './pages/registro/registro.component';

export const routes: Routes = [
  { 
    path: '', 
    redirectTo: 'login',  
    pathMatch: 'full' 
  },
  { 
    path: '', 
    component: LoginComponent 
  },
  { 
    path: 'servicios', 
    component: ServiciosComponent,
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
    canActivate: [AdminGuard]
  },
  { 
    path: 'especialidad/:id', 
    component: EspecialidadComponent,
    canActivate: [AuthGuard]
  },
  { 
    path: 'registro', 
    component: RegistroComponent 
  },
  { 
    path: '**', 
    redirectTo: 'login'  // ← Redirige a la raíz (login)
  }
];