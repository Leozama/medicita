import { Component } from '@angular/core';

// Modelo de pago
interface Payment {
  id: string;
  patientName: string;
  patientId: string;
  doctorName: string;
  specialty: string;
  date: string;
  paymentMethod: string;
  amount: number;
  paymentReference?: string;
  paymentDate?: string;
  status: 'paid' | 'pending' | 'cancelled';
}

@Component({
  selector: 'app-view-confirmar-pagos',
  templateUrl: './view-confirmar-pagos.component.html',
  styleUrls: ['./view-confirmar-pagos.component.css']
})
export class ViewConfirmarPagosComponent {
  goBack() {
      throw new Error("Method not implemented.");
  }

  // Lista de pagos (puede traerla de un servicio más adelante)
  paymentsList: Payment[] = [
    {
      id: 'P001',
      patientName: 'Carlos Pérez',
      patientId: 'CP123',
      doctorName: 'Dr. Gómez',
      specialty: 'Cardiología',
      date: '2025-11-10',
      paymentMethod: 'Tarjeta',
      amount: 75.5,
      paymentReference: 'REF-001',
      paymentDate: '2025-11-11',
      status: 'pending'
    },
    {
      id: 'P002',
      patientName: 'Ana López',
      patientId: 'AL456',
      doctorName: 'Dra. Ramos',
      specialty: 'Odontología',
      date: '2025-11-09',
      paymentMethod: 'Efectivo',
      amount: 60,
      paymentReference: 'REF-002',
      paymentDate: '2025-11-10',
      status: 'paid'
    }
  ];

  selectedPayment: Payment | null = null;

  // Función para formatear la fecha
  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  // Cambiar el estado del pago
  handleChangeStatus(paymentId: string, newStatus: Payment['status']): void {
    this.paymentsList = this.paymentsList.map(payment =>
      payment.id === paymentId ? { ...payment, status: newStatus } : payment
    );
    this.selectedPayment = null;
  }

  // Mostrar etiqueta de estado
  getStatusBadgeClass(status: Payment['status']): string {
    switch (status) {
      case 'paid': return 'badge bg-green-500 text-white';
      case 'pending': return 'badge bg-yellow-500 text-white';
      case 'cancelled': return 'badge bg-red-500 text-white';
      default: return '';
    }
  }
}
