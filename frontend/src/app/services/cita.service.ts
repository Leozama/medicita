// services/cita.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface CitaData {
  medicoId: number;
  medicoNombre: string;
  especialidad: string;
  fecha: string;
  hora: string;
  costo: number;
}

@Injectable({
  providedIn: 'root'
})
export class CitaService {
  private mostrarModalSource = new BehaviorSubject<boolean>(false);
  private citaDataSource = new BehaviorSubject<CitaData | null>(null);

  mostrarModal$ = this.mostrarModalSource.asObservable();
  citaData$ = this.citaDataSource.asObservable();

  abrirModal(medicoId: number, medicoNombre: string, especialidad: string) {
     console.log('🟢 SERVICIO: Abriendo modal para:', medicoNombre);

    this.citaDataSource.next({
      medicoId,
      medicoNombre,
      especialidad,
      fecha: '',
      hora: '',
      costo: 150 // Costo base
    });
    this.mostrarModalSource.next(true);
  }

  cerrarModal() {
    console.log('🟢 SERVICIO: Cerrando modal');
    this.mostrarModalSource.next(false);
    this.citaDataSource.next(null);
  }

 
  confirmarCita(citaData: CitaData) {
    console.log('🟢 SERVICIO: Confirmando cita:', citaData);
    this.cerrarModal();
    alert(`✅ Cita confirmada!\n\nMédico: ${citaData.medicoNombre}\nFecha: ${citaData.fecha}\nHora: ${citaData.hora}\nCosto: $${citaData.costo}`);
  }
}