import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Cliente, Puestojuego} from '../models/interfaces';

@Injectable({
  providedIn: 'root'
})
export class PuestoService {
  private url = "http://localhost:8080/api/v1/puestos";
  private http: HttpClient =inject(HttpClient);

  getPuestos(){
    return this.http.get<Puestojuego[]>(this.url);
  }
}
