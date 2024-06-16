import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api'; // URL do seu back-end

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const headers = new HttpHeaders({
      'username': username,
      'password': password
    });

    return this.http.post<any>(`${this.baseUrl}/login`, {}, { headers });
  }

  logout(): Observable<any> {
    // Logout não precisa enviar cabeçalhos de autenticação
    return this.http.post<any>(`${this.baseUrl}/logout`, {});
  }

  isAuthenticated(): boolean {
    // Implementar a verificação de autenticação com base na sua lógica
    return !!localStorage.getItem('user');
  }
}