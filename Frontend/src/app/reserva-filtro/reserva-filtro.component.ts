import {Component, EventEmitter, inject, OnDestroy, OnInit, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {Cliente, Puestojuego, ReservaDto, Videojuego} from '../../models/interfaces';
import {Subscription} from 'rxjs';
import {ReservaService} from '../../services/reserva.service';
import {VideojuegoService} from '../../services/videojuego.service';
import {PuestoService} from '../../services/puesto.service';
import {ClienteService} from '../../services/cliente.service';

@Component({
  selector: 'app-reserva-filtro',
  imports: [
    FormsModule
  ],
  templateUrl: './reserva-filtro.component.html'
})
export class ReservaFiltroComponent implements OnInit, OnDestroy {
  @Output() filtrado = new EventEmitter<ReservaDto>();

  clientes: Cliente[] = [];
  videojuegos: Videojuego[] = [];
  puestos: Puestojuego[] = [];

  reserva: ReservaDto = {
    clienteId: '',
    videojuegoId: '',
    puestoId: '',
    fechaHora: '',
    duracionMinutos: '',
    observaciones: ''
  }
  private subscription: Subscription = new Subscription();
  private reservaService= inject(ReservaService);
  private videojuegoService= inject(VideojuegoService);
  private clienteService= inject(ClienteService);
  private puestoService= inject(PuestoService);


  ngOnInit(): void {
    this.subscription.add(this.getClientes());
    this.subscription.add(this.getVideojuegos());
    this.subscription.add(this.getPuestos());
  }

  getClientes(): void {
    this.subscription.add(this.clienteService.getClientes().subscribe({
      next: data => {
        this.clientes = data;
      },
      error: err => {
        console.log(err);
      }
    }))
  }

  getVideojuegos(): void {
    this.subscription.add(this.videojuegoService.getVideojuegos().subscribe({
      next: data => {
        this.videojuegos = data;
      },
      error: err => {
        console.log(err);
      }
    }))
  }

  getPuestos(): void {
    this.subscription.add(this.puestoService.getPuestos().subscribe({
      next: data => {
        this.puestos = data;
      },
      error: err => {
        console.log(err);
      }
    }))
  }

  onSubmit() {
    this.filtrado.emit(this.reserva);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
