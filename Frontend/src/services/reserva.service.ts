import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { ReservaDto, ReservaRequestDto} from '../models/interfaces';
import {map} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private url = "http://localhost:8080/api/v1/reservas";
  private http: HttpClient =inject(HttpClient);

  getReserva(clienteId?: number, videojuegoId?:number, puestoId?:number, fechaHora?: Date){
    let params = new HttpParams();
    if(clienteId) params = params.set('clienteId', clienteId);
    if(videojuegoId) params = params.set('videojuegoId', videojuegoId);
    if(puestoId) params = params.set('puestoId', puestoId);
    if(fechaHora) params = params.set('fechaHora', fechaHora.toISOString().split('.')[0]);

    return this.http.get<ReservaRequestDto[]>(this.url, {params}).pipe(
      map((res: ReservaRequestDto[]) => res.map(r => ({
        clienteId: r.cliente_id,
        videojuegoId: r.videojuego_id,
        puestoId: r.puesto_id,
        fechaHora: r.fechaHora,
        duracionMinutos: r.duracionMinutos,
        observaciones: r.observaciones,
      } as ReservaDto)))
    );
  }

  postReserva(reserva: ReservaDto) {
    const clienteId = (reserva.clienteId === "undefined" || reserva.clienteId === "") ? null : Number(reserva.clienteId);
    const videojuegoId = (reserva.videojuegoId === "undefined" || reserva.videojuegoId === "") ? null : Number(reserva.videojuegoId);
    const puestoId = (reserva.puestoId === "undefined" || reserva.puestoId === "") ? null : Number(reserva.puestoId);

    const reservaRequest: ReservaRequestDto = {
      cliente_id: clienteId,
      videojuego_id:videojuegoId,
      puesto_id: puestoId,
      fechaHora: reserva.fechaHora,
      duracionMinutos: Number(reserva.duracionMinutos),
      observaciones: reserva.observaciones,
    };
    console.log('Mapping result:', {
      original: reserva,
      mapped: reservaRequest
    });
    return this.http.post<ReservaRequestDto>(this.url, reservaRequest);
  }
}
