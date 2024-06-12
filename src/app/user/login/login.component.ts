import { Component } from '@angular/core';
import {FloatLabelModule} from "primeng/floatlabel";
import {Button} from "primeng/button";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FloatLabelModule,
    Button
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

}
