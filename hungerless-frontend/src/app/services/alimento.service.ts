import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Alimento {
  id?: number;
  nome: string;
  descricao?: string;
  validade: string;
  quantidade: number;
  categoriaId: number;
  status: 'DISPONIVEL' | 'RESERVADO' | 'DOADO';
}

@Injectable({
  providedIn: 'root'
})
export class AlimentoService {
  private apiUrl = 'http://localhost:8080/alimentos';

  constructor(private http: HttpClient) {}

  listar(): Observable<{ content: Alimento[] }> {
    return this.http.get<{ content: Alimento[] }>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<Alimento> {
    return this.http.get<Alimento>(`${this.apiUrl}/${id}`);
  }

  cadastrar(alimento: Alimento): Observable<Alimento> {
    return this.http.post<Alimento>(this.apiUrl, alimento);
  }

  atualizar(id: number, alimento: Alimento): Observable<Alimento> {
    return this.http.put<Alimento>(`${this.apiUrl}/${id}`, alimento);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAlimentosPorDoador(doadorId: number): Observable<Alimento[]> {
    return this.http.get<Alimento[]>(`${this.apiUrl}/doador/${doadorId}`);
  }

  getMeusAlimentos(): Observable<Alimento[]> {
  return this.http.get<Alimento[]>(`${this.apiUrl}/meus-alimentos`);
}
}
