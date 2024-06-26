import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClientModule} from "@angular/common/http";
import {FloatLabelModule} from "primeng/floatlabel";
import {ButtonModule} from "primeng/button";
import {ReactiveFormsModule} from "@angular/forms";
import {CartComponent} from "./user/cart/cart.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HttpClientModule,
    CartComponent,
    FloatLabelModule,
    ButtonModule,
    ReactiveFormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'angular-ecommerce-livros';
}
