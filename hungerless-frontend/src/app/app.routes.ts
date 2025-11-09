import { Routes } from '@angular/router';
import { Auth } from './pages/auth/auth';

export const routes: Routes = [
  {
    path: '',
    component: Auth
  },
   {
    path: 'admin',
    loadComponent:() =>
      import('./pages/home/admin/home').then((m) => m.Home),
  },
   {
    path: 'doador',
    loadComponent: () => import('./pages/home/doador/home').then((m) => m.DoadorComponent)
  },
   {
    path: 'receptor',
    loadComponent:() =>
      import('./pages/home/receptor/home').then((m) => m.ReceptorComponent),
  }
];
