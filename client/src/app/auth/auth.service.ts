import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Http, Headers, Response } from '@angular/http';
import { UserService } from '../shared/user/user.service';
import { Observable } from 'rxjs/Observable';
import {TokenStorage} from '../auth/token.storage';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';


@Injectable()
export class AuthService {

  constructor(private userService: UserService, private router: Router, private token: TokenStorage) { }

  isLoggedIn() {
      if(this.token.getToken() === null) {
        return false
      }
      return true;
  }

  attemptAuth(email: string, password: string) {
    if (email !== '' && password != '') {
      const credentials = {email: email, password: password};
      return this.userService.authUser(credentials);
    }
  }

  logout() {
    this.token.signOut();
    this.router.navigate(['/login']);
  }

  public getRole(): string {
    return this.token.getRole();
  }
}
