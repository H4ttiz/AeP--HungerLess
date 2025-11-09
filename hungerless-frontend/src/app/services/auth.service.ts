import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  login(credentials: { email: string; senha: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((res: any) => {

        localStorage.setItem('token', res.token);
        localStorage.setItem('tipo', res.tipo);
      })
    );
  }

  logout() {
    localStorage.clear();
  }

  getUserTipo(): string | null {
    return localStorage.getItem('tipo');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
