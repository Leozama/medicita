// pages/servicios/servicios.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-servicios',
  standalone: true,
  template: `
    <!-- Header -->
    <section class="bg-blue-600 text-white py-16">
      <div class="container mx-auto px-4">
        <h1 class="text-4xl md:text-5xl font-bold text-center mb-4">
          Nuestros Servicios Médicos
        </h1>
        <p class="text-xl text-center max-w-2xl mx-auto">
          Descubre todos los servicios especializados que ofrecemos para tu salud y bienestar.
        </p>
      </div>
    </section>

    <!-- Services Grid -->
    <section class="py-16">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <!-- Service Card 1 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
               (click)="verMedicos('cardiologia')">
            <div class="h-48 bg-blue-100 flex items-center justify-center">
              <span class="text-6xl">❤️</span>
            </div>
            <div class="p-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-3">Cardiología</h3>
              <p class="text-gray-600 mb-4">
                Diagnóstico y tratamiento de enfermedades del corazón y sistema cardiovascular.
              </p>
              <div class="text-blue-600 font-semibold">
                Ver médicos especialistas →
              </div>
            </div>
          </div>

          <!-- Service Card 2 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
               (click)="verMedicos('pediatria')">
            <div class="h-48 bg-green-100 flex items-center justify-center">
              <span class="text-6xl">👶</span>
            </div>
            <div class="p-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-3">Pediatría</h3>
              <p class="text-gray-600 mb-4">
                Cuidado integral de la salud infantil desde recién nacidos hasta adolescentes.
              </p>
              <div class="text-blue-600 font-semibold">
                Ver médicos especialistas →
              </div>
            </div>
          </div>

          <!-- Service Card 3 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
               (click)="verMedicos('odontologia')">
            <div class="h-48 bg-purple-100 flex items-center justify-center">
              <span class="text-6xl">🦷</span>
            </div>
            <div class="p-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-3">Odontología</h3>
              <p class="text-gray-600 mb-4">
                Servicios dentales completos para mantener una salud bucal óptima.
              </p>
              <div class="text-blue-600 font-semibold">
                Ver médicos especialistas →
              </div>
            </div>
          </div>

          <!-- Service Card 4 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
               (click)="verMedicos('oftalmologia')">
            <div class="h-48 bg-red-100 flex items-center justify-center">
              <span class="text-6xl">👁️</span>
            </div>
            <div class="p-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-3">Oftalmología</h3>
              <p class="text-gray-600 mb-4">
                Cuidado especializado de la visión y salud ocular para todas las edades.
              </p>
              <div class="text-blue-600 font-semibold">
                Ver médicos especialistas →
              </div>
            </div>
          </div>

          <!-- Service Card 5 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
               (click)="verMedicos('ortopedia')">
            <div class="h-48 bg-yellow-100 flex items-center justify-center">
              <span class="text-6xl">🦴</span>
            </div>
            <div class="p-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-3">Ortopedia</h3>
              <p class="text-gray-600 mb-4">
                Diagnóstico y tratamiento de problemas musculoesqueléticos y lesiones.
              </p>
              <div class="text-blue-600 font-semibold">
                Ver médicos especialistas →
              </div>
            </div>
          </div>

          <!-- Service Card 6 -->
          <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
               (click)="verMedicos('neurologia')">
            <div class="h-48 bg-indigo-100 flex items-center justify-center">
              <span class="text-6xl">🧠</span>
            </div>
            <div class="p-6">
              <h3 class="text-xl font-semibold text-gray-800 mb-3">Neurología</h3>
              <p class="text-gray-600 mb-4">
                Especialistas en el diagnóstico y tratamiento de enfermedades del sistema nervioso.
              </p>
              <div class="text-blue-600 font-semibold">
                Ver médicos especialistas →
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  `
})
export class ServiciosComponent {
  constructor(private router: Router) {}

  verMedicos(especialidad: string) {
    this.router.navigate(['/especialidad', especialidad]);
  }
}