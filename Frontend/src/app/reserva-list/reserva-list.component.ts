import {Component, Input} from '@angular/core';
import {Cliente, Puestojuego, ReservaDto, Videojuego} from '../../models/interfaces';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-reserva-list',
  imports: [
    DatePipe
  ],
  templateUrl: './reserva-list.component.html',
  styleUrl: './reserva-list.component.css'
})
export class ReservaListComponent {
  @Input() listaReserva: ReservaDto[]=[];
  @Input() videojuegos: Videojuego[] = [];
  @Input() clientes: Cliente[] = [];
  @Input() puestos: Puestojuego[] = [];

  getNombreCliente(id:number):string{
    if (!this.clientes || this.clientes.length === 0) return '';
    return this.clientes.find(c => c.id === id)?.nombreCompleto ?? 'Desconocido';
  }
  getNombreVideojuego(id: number): string {
    if (!this.videojuegos) return '';
    return this.videojuegos.find(v => v.id === id)?.titulo ?? 'Desconocido';
  }

  getNombrePuesto(id: number): string {
    if (!this.puestos) return '';
    return this.puestos.find(p => p.id === id)?.nombre ?? 'Desconocido';
  }

  protected readonly Number = Number;
}
