import { Component, OnInit } from '@angular/core';
import { AuthService } from './../auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {
  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  isLoggedInFn(): boolean {
    return this.authService.isLoggedIn() || false;
  }

  isDisplayButtons(): boolean {
    if (this.authService.isLoggedIn() || false) {
      return this.authService.getRole() === 'ADOPS';
    }
    return false;
  }

  logout() {
    this.authService.logout();
  }
}
