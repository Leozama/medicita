import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  template: `
    <header class="bg-white shadow-sm border-b">
      <nav class="container mx-auto px-4 py-4">
        <div class="flex justify-between items-center">
          <!-- Lado Izquierdo: Avatar + Logo -->
          <div class="flex items-center space-x-4">
            <!-- Avatar del Usuario -->
            <div class="relative">
              <button
                (click)="openUserSidebar()"
                class="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100 transition-colors"
              >
                <div class="w-10 h-10 bg-gradient-to-br from-blue-500 to-blue-600 rounded-full flex items-center justify-center text-white font-semibold">
                  {{ getIniciales() }}
                </div>
                <span class="hidden md:block text-gray-700 font-medium">Juan Pérez</span>

              </button>
            </div>

            <!-- Separador visual -->
            <div class="h-6 w-px bg-gray-300 hidden md:block"></div>

            <!-- Logo -->
            <div class="flex items-center space-x-2">
              <div class="w-10 h-10 bg-blue-600 rounded-full"></div>
              <span class="text-xl font-bold text-gray-800">CentroMédico</span>
            </div>
          </div>

          <!-- Lado Derecho: Botones -->
          <div class="flex items-center space-x-4">
            <!-- Cerrar Sesión (Desktop) -->
            <button class="hidden md:block bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors">
              Cerrar Sesión
            </button>

                      </div>
        </div>
      </nav>
    </header>

    <!-- Barra Lateral del Usuario -->
    <div
      *ngIf="showUserSidebar"
      class="fixed inset-0 z-50 flex"
    >
      <!-- Overlay -->
      <div
        class="fixed inset-0 bg-black/60 "
        (click)="closeUserSidebar()"
      ></div>

      <!-- Sidebar -->
      <div
        class="relative w-80 bg-white shadow-xl h-full animate-slide-in-left"
      >
        <!-- Header del Sidebar -->
        <div class="bg-blue-600 text-white p-6">
          <div class="flex justify-between items-center mb-4">
            <h2 class="text-xl font-bold">Mi Perfil</h2>
            <button
              (click)="closeUserSidebar()"
              class="text-white hover:text-blue-200 transition-colors"
            >
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
              </svg>
            </button>
          </div>

          <!-- Avatar e Info -->
          <div class="flex items-center space-x-4">
            <div class="w-16 h-16 bg-white bg-opacity-20 rounded-full flex items-center justify-center text-blue-900 font-bold text-xl">
              {{ getIniciales() }}
            </div>
            <div>
              <p class="font-semibold text-lg">Juan Pérez</p>
              <p class="text-blue-100 text-sm">Paciente</p>
            </div>
          </div>
        </div>

        <!-- Contenido del Sidebar -->
        <div class="p-6 space-y-6">
          <!-- Información Personal -->
          <div>
            <h3 class="font-semibold text-gray-800 mb-3"> Información Personal</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">Email:</span>
                <span class="text-gray-800">juan.perez(arroba symbol)email.com</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Teléfono:</span>
                <span class="text-gray-800">+58 414-2851567 </span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Edad:</span>
                <span class="text-gray-800">35 años</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">CI:</span>
                <span class="text-gray-800">12345678</span>
              </div>
            </div>
          </div>



          <!-- Citas Programadas -->
          <div>
            <h3 class="font-semibold text-gray-800 mb-3">Próximas Citas</h3>
            <div class="space-y-3">
              <div class="bg-blue-50 p-3 rounded-lg border border-blue-200">
                <p class="text-sm font-semibold text-blue-800">Dr. Carlos Mendoza</p>
                <p class="text-xs text-blue-600">Cardiología - 20 Nov 2024, 10:00 AM</p>
                <span class="inline-block mt-1 px-2 py-1 bg-green-100 text-green-800 text-xs rounded-full">Confirmada</span>
              </div>
              <div class="bg-yellow-50 p-3 rounded-lg border border-yellow-200">
                <p class="text-sm font-semibold text-yellow-800">Dra. Ana García</p>
                <p class="text-xs text-yellow-600">Pediatría - 25 Nov 2024, 09:00 AM</p>
                <span class="inline-block mt-1 px-2 py-1 bg-yellow-100 text-yellow-800 text-xs rounded-full">Pendiente</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `

})
export class HeaderComponent {
  showUserSidebar = false;

  openUserSidebar() {
    this.showUserSidebar = true;
  }

  closeUserSidebar() {
    this.showUserSidebar = false;
  }

  getIniciales(): string {
    return 'JP'; // Iniciales de "Juan Pérez"
  }
}
