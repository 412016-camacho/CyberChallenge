import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Cliente} from '../models/interfaces';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private url = "http://localhost:8080/api/v1/clientes";
  private http: HttpClient =inject(HttpClient);

  getClientes(){
    return this.http.get<Cliente[]>(this.url);
  }
}
