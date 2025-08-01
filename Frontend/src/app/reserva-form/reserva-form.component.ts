import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {Cliente, Puestojuego, ReservaDto, ReservaRequestDto, Videojuego} from '../../models/interfaces';
import {ClienteService} from '../../services/cliente.service';
import {VideojuegoService} from '../../services/videojuego.service';
import {PuestoService} from '../../services/puesto.service';
import {Subscription} from 'rxjs';
import {FormsModule, NgForm} from '@angular/forms';
import {ReservaService} from '../../services/reserva.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reserva-form',
  imports: [
    FormsModule
  ],
  templateUrl: './reserva-form.component.html',
  styleUrl: './reserva-form.component.css'
})
export class ReservaFormComponent implements OnInit , OnDestroy{

  clientes: Cliente[] = [];
  videojuegos: Videojuego[] = [];
  puestos: Puestojuego[] = [];
  reservas: ReservaDto = {
    clienteId: '',
    videojuegoId: '',
    puestoId: '',
    fechaHora: '',
    duracionMinutos: '',
    observaciones: ''
  }

  fechaString: string = '';
  horaString: string = '';

  getFechaHora(): string {
    const date = new Date(`${this.fechaString}T${this.horaString}`);

    //si el string tiene menos de 2 caracteres, agrega 0 a la izq
    const pad = (n: number) => n.toString().padStart(2, '0');
    const fechaFormateada = `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:00`;
    console.log('Fecha formateada:', fechaFormateada);
    return fechaFormateada;
  }

  //inicializar fecha
  //defaultDate: string = today.toISOString().split('T')[0]; --> format: yyyy-MM-dd

  private clienteService = inject(ClienteService);
  private videojuegoService = inject(VideojuegoService);
  private puestoService = inject(PuestoService);
  private reservaService = inject(ReservaService);
  private router = inject(Router);

  private subscription: Subscription = new Subscription();

  ngOnInit(): void {
    this.getClientes();
    this.getVideojuegos();
    this.getPuestos();
  }

  getClientes(): void {
    console.log('Cargando clientes...');
    this.subscription.add(this.clienteService.getClientes().subscribe({
      next: data => {
        console.log('Clientes cargados:', data);
        this.clientes = data;
      },
      error: err => {
        console.log(err);
        this.clientes = [];
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
        this.videojuegos = [];
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
        this.puestos = [];
      }
    }))
  }

  reservar(form: NgForm) {

    if (form.invalid){
      alert("Faltan algunos campos")
      return;
    }
    this.reservas.fechaHora = this.getFechaHora();

    this.subscription.add(this.reservaService.postReserva(this.reservas).subscribe({
      next: data => {
        console.log("Reserva creada: ",data);
        this.resetForm();
        this.router.navigate(['/reservas']);
      },
      error: err => {
        console.log(err);
      }
    }))
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  cancelar() {
    this.resetForm();
  }

  private resetForm() {
    this.reservas = {
      clienteId: null,
      videojuegoId: null,
      puestoId: null,
      fechaHora: '',
      duracionMinutos: 0,
      observaciones: ''
    }
    this.fechaString = '';
    this.horaString = '';
  }
}
