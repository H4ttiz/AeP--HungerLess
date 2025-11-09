import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe, NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SidebarComponent } from "./sidebar/sidebar.component";
import { AlimentoService, Alimento } from '../../../services/alimento.service';
import { CategoriaService, Categoria } from '../../../services/categoria.service';

type StatusAlimento = 'DISPONIVEL' | 'RESERVADO' | 'DOADO';

@Component({
  selector: 'app-doador',
  standalone: true,
  imports: [CommonModule, FormsModule, DatePipe, NgClass, SidebarComponent],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class DoadorComponent implements OnInit {
  alimentos: Alimento[] = [];
  categorias: Categoria[] = [];
  statusOptions: StatusAlimento[] = ['DISPONIVEL', 'RESERVADO', 'DOADO'];

  isModalOpen = false;
  isViewModalOpen = false;
  isDeleteConfirmOpen = false;
  isEditMode = false;

  currentAlimento: Alimento = this.getEmptyAlimento();
  viewAlimento: Alimento = this.getEmptyAlimento();
  alimentoToDeleteId: number | null = null;

  constructor(
    private alimentoService: AlimentoService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.loadAlimentos();
    this.loadCategorias();
  }

  private getEmptyAlimento(): Alimento {
    return {
      nome: '',
      descricao: '',
      validade: this.getTodayDateString(),
      quantidade: 1,
      categoriaId: 1,
      status: 'DISPONIVEL'
    };
  }

  private getTodayDateString(): string {
    const today = new Date();
    return today.toISOString().split('T')[0];
  }

  loadAlimentos(): void {
  this.alimentoService.getMeusAlimentos().subscribe({
    next: (res) => (this.alimentos = res),
    error: (err) => console.error('Erro ao carregar alimentos:', err)
  });
}

  loadCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (cats) => (this.categorias = cats),
      error: (err) => console.error('Erro ao carregar categorias:', err)
    });
  }

  getCategoriaNome(id: number): string {
    return this.categorias.find(c => c.id === id)?.nome || 'Desconhecida';
  }

  formatStatus(status: StatusAlimento): string {
    return status.charAt(0) + status.slice(1).toLowerCase();
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.currentAlimento = this.getEmptyAlimento();
    this.isModalOpen = true;
  }

  openEditModal(alimento: Alimento): void {
    this.isEditMode = true;
    this.currentAlimento = { ...alimento };
    this.isModalOpen = true;
  }

  openViewModal(alimento: Alimento): void {
    this.viewAlimento = alimento;
    this.isViewModalOpen = true;
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  closeViewModal(): void {
    this.isViewModalOpen = false;
  }

  saveAlimento(): void {
    const saveAction = this.isEditMode
      ? this.alimentoService.atualizar(this.currentAlimento.id!, this.currentAlimento)
      : this.alimentoService.cadastrar(this.currentAlimento);

    saveAction.subscribe({
      next: () => {
        this.loadAlimentos();
        this.closeModal();
      },
      error: (err) => console.error('Erro ao salvar alimento:', err)
    });
  }

  confirmDelete(id: number): void {
    this.alimentoToDeleteId = id;
    this.isDeleteConfirmOpen = true;
  }

  deleteAlimento(): void {
    if (this.alimentoToDeleteId) {
      this.alimentoService.deletar(this.alimentoToDeleteId).subscribe({
        next: () => {
          this.loadAlimentos();
          this.isDeleteConfirmOpen = false;
          this.alimentoToDeleteId = null;
        },
        error: (err) => console.error('Erro ao excluir alimento:', err)
      });
    }
  }
}
