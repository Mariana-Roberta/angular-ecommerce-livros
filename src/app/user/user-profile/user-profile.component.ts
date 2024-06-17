// src/app/components/user-profile/user-profile.component.ts

import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import {Observable} from "rxjs";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  standalone: true,
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  userAtual: User | null = null;

  constructor(private authService: AuthService) {
    this.userAtual = this.authService.userValue;
  }

  ngOnInit(): void {
  }

}
