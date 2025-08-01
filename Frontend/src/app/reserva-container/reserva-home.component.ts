import {Component, inject, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ReservaListComponent} from '../reserva-list/reserva-list.component';
import {Cliente, Puestojuego, ReservaDto, Videojuego} from '../../models/interfaces';
import {forkJoin, Subscription} from 'rxjs';
import {ClienteService} from '../../services/cliente.service';
import {VideojuegoService} from '../../services/videojuego.service';
import {PuestoService} from '../../services/puesto.service';
import {ReservaService} from '../../services/reserva.service';
import {ReservaFiltroComponent} from '../reserva-filtro/reserva-filtro.component';

@Component({
  selector: 'app-reserva-container',
  imports: [
    ReservaListComponent,
    ReservaFiltroComponent
  ],
  templateUrl: './reserva-home.component.html',
  styleUrl: './reserva-home.component.css'
})
export class ReservaHomeComponent implements OnInit, OnDestroy {
  clientes: Cliente[] = [];
  videojuegos: Videojuego[] = [];
  puestos: Puestojuego[] = [];
  listaReserva: ReservaDto[] = [];

  private reservaService = inject(ReservaService);
  private clienteService = inject(ClienteService);
  private puestoService = inject(PuestoService);
  private videojuegoService = inject(VideojuegoService);
  private subscription: Subscription = new Subscription();

  ngOnInit(): void {
    const todosLosDatos$ = forkJoin({
      clientes: this.clienteService.getClientes(),
      videojuegos: this.videojuegoService.getVideojuegos(),
      puestos: this.puestoService.getPuestos(),
      reservas: this.reservaService.getReserva()
    });
    this.subscription.add(todosLosDatos$.subscribe(datos => {
      this.clientes = datos.clientes;
      this.videojuegos = datos.videojuegos;
      this.puestos = datos.puestos;
      this.listaReserva = datos.reservas;
    }));
  }

  filtered(evento : ReservaDto) {
    const clienteId = evento.clienteId ? Number(evento.clienteId) : undefined;
    const videojuegoId = evento.videojuegoId ? Number(evento.videojuegoId) : undefined;
    const puestoId = evento.puestoId ? Number(evento.puestoId) : undefined;
    const fechaHora = evento.fechaHora ? new Date(evento.fechaHora) : undefined;

    this.subscription.add(this.reservaService.getReserva(
      clienteId, videojuegoId, puestoId,fechaHora)
      .subscribe(res =>{
        this.listaReserva = res;
        console.log(res);
      }));
  }


  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
