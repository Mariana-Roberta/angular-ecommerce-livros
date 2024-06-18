import {Injectable} from '@angular/core';
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "./auth.service";
import {User} from "../models/user.model";
import {Cart} from "../models/cart.model";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartSubject: BehaviorSubject<Cart | null>;
  public cart: Observable<Cart | null>;
  private apiUrl = 'http://localhost:8080/usuario/carrinho';

  constructor(private http: HttpClient) {
    this.cartSubject = new BehaviorSubject<Cart | null>(null);
    this.cart = this.cartSubject.asObservable();
  }

  public get cartValue(): Cart | null {
    return this.cartSubject.value;
  }

  setCart(cart: Cart) {
    this.cartSubject.next(cart);
  }

  getCart(carrinhoId: Number): Observable<any> {
    const url = `${this.apiUrl}/cart/${carrinhoId}`; // Ajuste o endpoint da sua API conforme necessário
    return this.http.get<any>(url);
  }

  getCartByUserId(userId: Number): Observable<any> {
    const url = `${this.apiUrl}/user/${userId}`; // Ajuste o endpoint da sua API conforme necessário
    return this.http.get<any>(url);
  }

  getCartItemsByUserId(userId: Number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/users/${userId}/carrinho/items`);
  }

  getCartItems(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/listaItens`)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  getItemsByCarrinhoIdAndLivroId(carrinhoId: Number, livroId: number): Observable<any[]> {
    const url = `${this.apiUrl}/livro?livroId=${livroId}/cart?cartId=${carrinhoId}`;
    const requestBody = {carrinhoId: carrinhoId, livroId: livroId};
    return this.http.put<any>(url, requestBody)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  updateItemQuantity(carrinhoId: Number, livroId: number, quantidade: number): Observable<any> {
    const url = `${this.apiUrl}/book/cart`;
    const request = {quantidade};
    const requestBody = {carrinhoId: carrinhoId, livroId: livroId,quantidade: quantidade};

    return this.http.put<any>(url, requestBody)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  updateItemQuantityByItemId(carrinhoId: Number, itemId: number, quantidade: number): Observable<any> {
    const url = `${this.apiUrl}/item/cart`;
    const request = {quantidade};
    const requestBody = {carrinhoId: carrinhoId, itemId: itemId,quantidade: quantidade};

    return this.http.put<any>(url, requestBody)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  createCart(usuarioId: Number): Observable<any> {
    const requestBody = {usuarioId: usuarioId};
    return this.http.post<any>(`${this.apiUrl}/criar`, requestBody)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  addItemToCart(carrinhoId: number, livroId: number, quantidade: number): Observable<any> {
    console.log(carrinhoId);
    console.log(livroId);
    console.log(quantidade);
    const itemCarrinho = {carrinhoId, livroId, quantidade};
    return this.http.post<any>(`${this.apiUrl}/addItem`, itemCarrinho)
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  removeItemFromCart(itemId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/deleteItem`, { body: { itemId } })
      .pipe(
        map(response => response),
        catchError(this.handleError)
      );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred', error);
    return throwError('Something went wrong; please try again later.');
  }
}
