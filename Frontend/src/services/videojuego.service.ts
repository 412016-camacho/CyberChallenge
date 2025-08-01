import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Cliente, Videojuego} from '../models/interfaces';

@Injectable({
  providedIn: 'root'
})
export class VideojuegoService {
  private url = "http://localhost:8080/api/v1/videojuegos";
  private http: HttpClient =inject(HttpClient);

  getVideojuegos(){
    return this.http.get<Videojuego[]>(this.url);
  }
}
