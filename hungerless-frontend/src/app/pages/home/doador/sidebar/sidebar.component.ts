import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  constructor(private router: Router) {}

  menuItems = [
    { icon: 'fa-home', label: 'Início', route: '/doador' },
    { icon: 'fa-apple-alt', label: 'Meus Alimentos', route: '/doador' },
    { icon: 'fa-plus-circle', label: 'Cadastrar', route: '/doador' },
    { icon: 'fa-user', label: 'Perfil', route: '/perfil' }
  ];

  sair(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }
}
