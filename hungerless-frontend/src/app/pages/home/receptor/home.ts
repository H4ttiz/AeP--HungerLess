// FILE: src/app/pages/home/receptor/home.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/usuario.service';
import { AlimentoService } from '../../../services/alimento.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { SidebarComponent } from "../doador/sidebar/sidebar.component";

@Component({
  selector: 'app-receptor',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class ReceptorComponent implements OnInit {
  doadores: any[] = [];
  doadorSelecionado: any = null;
  carregando = false;
  erro: string | null = null;

  constructor(
    private usuarioService: UsuarioService,
    private alimentoService: AlimentoService
  ) {}

  ngOnInit(): void {
    this.carregarDoadores();
  }

  /** 🔹 Carrega todos os doadores */
  carregarDoadores(): void {
    this.carregando = true;
    this.erro = null;

    this.usuarioService
      .getDoadores()
      .pipe(
        finalize(() => (this.carregando = false)),
        catchError((err) => {
          console.error('Erro ao buscar doadores:', err);
          this.erro = 'Não foi possível carregar os doadores.';
          return of([]);
        })
      )
      .subscribe((dados: any[]) => {
        this.doadores = dados || [];
      });
  }

  /** 🔹 Seleciona um doador e busca seus alimentos */
  selecionarDoador(doador: any): void {
    this.doadorSelecionado = { ...doador, alimentos: [] };
    this.carregando = true;

    this.alimentoService
      .getAlimentosPorDoador(doador.id)
      .pipe(
        finalize(() => (this.carregando = false)),
        catchError((err) => {
          console.error('Erro ao buscar alimentos do doador:', err);
          this.erro = 'Não foi possível carregar os alimentos deste doador.';
          return of([]);
        })
      )
      .subscribe((alimentos: any[]) => {
        this.doadorSelecionado.alimentos = alimentos || [];
      });
  }

  /** 🔹 Volta para a lista */
  voltarParaLista(): void {
    this.doadorSelecionado = null;
    this.erro = null;
  }
}
