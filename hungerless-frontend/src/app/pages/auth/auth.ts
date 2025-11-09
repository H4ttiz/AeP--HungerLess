import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { UsuarioService } from '../../services/usuario.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.html',
  styleUrls: ['./auth.css'],
})
export class Auth {
  isSignUpPanelActive = false;
  step = 1;
  loading = false;

  loginData = { email: '', senha: '' };
  cadastroData = {
    nome: '',
    email: '',
    senha: '',
    tipo: 'DOADOR',
    documento: '',
    celular: '',
    telefoneFixo: '',
    whatsapp: '',
    cep: '',
    logradouro: '',
    numero: '',
    complemento: '',
    bairro: '',
    cidade: '',
    estado: ''
  };

  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  togglePanel() {
    this.isSignUpPanelActive = !this.isSignUpPanelActive;
    this.step = 1;
  }

  avancar() {
    if (this.step < 3) this.step++;
  }

  voltar() {
    if (this.step > 1) this.step--;
  }

  isNextButtonDisabled(): boolean {
    if (this.step === 1) {
      return !(
        this.cadastroData.nome &&
        this.cadastroData.email &&
        this.cadastroData.senha &&
        this.cadastroData.tipo &&
        this.cadastroData.documento
      );
    } else if (this.step === 2) {
      return !this.cadastroData.celular;
    }
    return false;
  }

  /** LOGIN REAL */
  logar() {
    this.loading = true;
    this.authService.login(this.loginData).subscribe({
      next: (res) => {
        this.loading = false;
        console.log('Login realizado com sucesso:', res);

        const tipo = res.tipo?.toUpperCase();

        if (tipo === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else if (tipo === 'DOADOR') {
          this.router.navigate(['/doador']);
        } else if (tipo === 'RECEPTOR') {
          this.router.navigate(['/receptor']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: (err) => {
        this.loading = false;
        alert('Erro ao fazer login: ' + (err.error?.message || 'Verifique seus dados.'));
      },
    });
  }

  /** CADASTRO REAL */
  cadastrar() {
    this.usuarioService.cadastrar(this.cadastroData).subscribe({
      next: (res) => {
        console.log('Cadastro realizado com sucesso:', res);
        alert('Cadastro concluído! Fazendo login automático...');

        // login automático
        this.authService.login({
          email: this.cadastroData.email,
          senha: this.cadastroData.senha
        }).subscribe(() => {
          const tipo = this.authService.getUserTipo();
          if (tipo === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else if (tipo === 'DOADOR') {
            this.router.navigate(['/doador']);
          } else {
            this.router.navigate(['/receptor']);
          }
        });
      },
      error: (err) => {
        console.error(err);
        alert('Erro ao cadastrar: ' + (err.error?.message || 'Verifique os campos.'));
      },
    });
  }
}
