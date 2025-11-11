// components/footer/footer.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  standalone: true,
  template: `
    <footer class="bg-gray-800 text-white py-12">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <!-- Logo and Description -->
          <div class="col-span-1 md:col-span-2">
            <div class="flex items-center space-x-2 mb-4">
              <div class="w-8 h-8 bg-white rounded-full"></div>
              <span class="text-xl font-bold">CentroMédico</span>
            </div>
            <p class="text-gray-300 max-w-md">
              Centro médico especializado en brindar atención de calidad con los mejores profesionales de la salud.
            </p>
          </div>

        
          <!-- Contact Info -->
          <div>
            <h3 class="font-semibold text-lg mb-4">Contacto</h3>
            <ul class="space-y-2 text-gray-300">
              <li>📞 (123) 456-7890</li>
              <li>✉️ info@centromedico.com</li>
              <li>📍 Av. Principal 123, Ciudad</li>
            </ul>
          </div>
        </div>
        
        <div class="border-t border-gray-700 mt-8 pt-8 text-center text-gray-300">
          <p>&copy; 2024 CentroMédico. Todos los derechos reservados.</p>
        </div>
      </div>
    </footer>
  `
})
export class FooterComponent {}