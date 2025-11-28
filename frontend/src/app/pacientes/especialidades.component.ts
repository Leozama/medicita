
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-especialidades',
  standalone: true,
  templateUrl:"especialidades.component.html"
})
export class EspecialidadesComponent {
  constructor(private router: Router) {}

  verMedicos(medicos: string) {
    this.router.navigate(['/medicos', medicos]);
  }
}