import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../../services/usuario.service';

interface User {
  id: number;
  nome: string;
  email: string;
  tipo: string;
  documento: string;
  status?: string;
  celular?: string;
  cep?: string;
  logradouro?: string;
  numero?: string;
  bairro?: string;
  cidade?: string;
  estado?: string;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class Home implements OnInit {
  users: User[] = [];
  showModal = false;
  editingUser: User | null = null;
  formData: Partial<User & { senha?: string }> = {};
  showSidebar = false;
  showConfirmDelete = false;
  userToDelete: User | null = null;

  // O cadastroData original não será mais usado, pois o formData será o único modelo para o modal.
  // Manter apenas para referência de campos, mas o código de saveUser será ajustado.
  // O template já foi ajustado para usar formData.

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.carregarUsuarios();
  }

  currentStep = 1;

  nextStep() {
    if (this.currentStep < 3) this.currentStep++;
  }

  prevStep() {
    if (this.currentStep > 1) this.currentStep--;
  }

  /** 🔹 Buscar usuários do backend */
  carregarUsuarios(): void {
    this.usuarioService.listar().subscribe({
      next: (data) => (this.users = data),
      error: (err) => console.error('Erro ao carregar usuários', err)
    });
  }

  /** 🔹 Abrir modal (edição ou criação) */
  openModal(user?: User): void {
    this.editingUser = user || null;
    this.formData = user
      ? { ...user, senha: '' }
      : {
          nome: '',
          email: '',
          tipo: 'DOADOR',
          documento: '',
          senha: '',
          celular: '',
          cep: '',
          logradouro: '',
          numero: '',
          bairro: '',
          cidade: '',
          estado: ''
        };
    this.showModal = true;
  }

  /** 🔹 Fechar modal */
  closeModal(): void {
    this.showModal = false;
    this.currentStep = 1; // Resetar o passo do formulário
  }

  /** 🔹 Salvar usuário (POST ou PUT) */
  saveUser(): void {
    if (!this.formData.nome || !this.formData.email) return;

    // O objeto a ser enviado para o backend
    const usuarioPayload: any = {
      nome: this.formData.nome,
      email: this.formData.email,
      tipo: this.formData.tipo,
      documento: this.formData.documento,
      celular: this.formData.celular,
      cep: this.formData.cep,
      logradouro: this.formData.logradouro,
      numero: this.formData.numero,
      bairro: this.formData.bairro,
      cidade: this.formData.cidade,
      estado: this.formData.estado
    };

    // Adiciona a senha apenas se estiver presente (necessário para cadastro e opcional para edição)
    if (this.formData.senha) {
      usuarioPayload.senha = this.formData.senha;
    }

    if (this.editingUser) {
      // PUT - Atualizar
      this.usuarioService.atualizar(this.editingUser.id, usuarioPayload).subscribe({
        next: () => {
          this.closeModal();
          this.carregarUsuarios();
          alert('Usuário atualizado com sucesso!');
        },
        error: (err) => console.error('Erro ao atualizar usuário', err)
      });
    } else {
      // POST - Cadastrar
      this.usuarioService.cadastrar(usuarioPayload).subscribe({
        next: () => {
          this.closeModal();
          this.carregarUsuarios();
          alert('Usuário cadastrado com sucesso!');
        },
        error: (err) => console.error('Erro ao cadastrar usuário', err)
      });
    }
  }

  /** 🔹 Abrir modal de confirmação de exclusão */
  confirmDelete(user: User): void {
    this.userToDelete = user;
    this.showConfirmDelete = true;
  }

  /** 🔹 Cancelar exclusão */
  cancelDelete(): void {
    this.showConfirmDelete = false;
    this.userToDelete = null;
  }

  /** 🔹 Excluir usuário após confirmação */
  deleteUser(): void {
    if (!this.userToDelete) return;

    this.usuarioService.excluir(this.userToDelete.id).subscribe({
      next: () => {
        this.showConfirmDelete = false;
        this.carregarUsuarios();
        alert(`Usuário "${this.userToDelete?.nome}" excluído com sucesso!`);
        this.userToDelete = null;
      },
      error: (err) => console.error('Erro ao excluir usuário', err)
    });
  }

  /** 🔹 Sidebar */
  toggleSidebar(state?: boolean): void {
    this.showSidebar = state !== undefined ? state : !this.showSidebar;
  }

  logout(): void {
    localStorage.removeItem('token');
    alert('Sessão encerrada.');
  }
}
