import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: Book[] = [];
  private cartSubject = new BehaviorSubject<Book[]>(this.cart);

  constructor() {}

  getCart() {
    return this.cartSubject.asObservable();
  }

  addToCart(book: Book) {
    this.cart.push(book);
    this.cartSubject.next(this.cart);
  }
}
