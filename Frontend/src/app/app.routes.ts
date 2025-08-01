import { Routes } from '@angular/router';
import {ReservaFormComponent} from './reserva-form/reserva-form.component';
import {ReservaHomeComponent} from './reserva-container/reserva-home.component';

export const routes: Routes = [
  {path: "", redirectTo: "reservas", pathMatch: "full"},
  {path: "reservas", component: ReservaHomeComponent},
  {path: "nueva-reserva", component: ReservaFormComponent},
  {path: "**", redirectTo: "reservas"},
];
