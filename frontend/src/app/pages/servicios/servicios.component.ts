// pages/servicios/servicios.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-servicios',
  standalone: true,
  templateUrl:"servicios.component.html"
})
export class ServiciosComponent {
  constructor(private router: Router) {}

  verMedicos(especialidad: string) {
    this.router.navigate(['/especialidad', especialidad]);
  }
}