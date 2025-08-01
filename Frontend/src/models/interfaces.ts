export interface Cliente {
  id: number;
  nombreCompleto: string;
  email: string;
}

export interface Videojuego {
  id: number;
  titulo: string;
  genero: string;
}

export interface Puestojuego {
  id: number;
  nombre: string;
  tipo: string;
}

export interface Reserva {
  id: number;
  cliente: Cliente;
  videojuego: Videojuego;
  puestojuego: Puestojuego;
  fechaHora: Date;
  duracionMinutos: number;
  observaciones: string;
}

export interface ReservaRequestDto {
  cliente_id: number | null;
  videojuego_id: number | null;
  puesto_id: number | null;
  fechaHora: string;
  duracionMinutos: number;
  observaciones?: string;
}

export interface ReservaDto {
  clienteId: string | number | null;
  videojuegoId: string | number | null;
  puestoId: string | number | null;
  fechaHora: string;
  duracionMinutos: string | number;
  observaciones?: string;
}
