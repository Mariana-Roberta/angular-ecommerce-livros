// src/app/services/auth.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/user.model';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request.model';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  private baseUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {
    this.userSubject = new BehaviorSubject<User | null>(null);
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User | null {
    return this.userSubject.value;
  }

  setUser(user: User) {
    this.userSubject.next(user);
  }

  login(loginRequest: LoginRequest): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, loginRequest).pipe(
      tap((user: User) => {
        if (user) {
          this.userSubject.next(user);
        }
      })
    );
  }

  logout(): void {
    this.userSubject.next(null);
    // Também pode adicionar lógica para limpar o armazenamento local ou cookies, se necessário
  }
}
