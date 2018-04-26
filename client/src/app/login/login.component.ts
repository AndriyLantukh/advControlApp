import { AuthService } from './../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenStorage } from '../auth/token.storage';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  private email: string;
  private password: string;

  private field: any;

  constructor(private router: Router, private fb: FormBuilder, private authService: AuthService, private token: TokenStorage) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  isFieldInvalid(field: string) {
    // return (
    //   !this.form.get(field).valid && this.form.get(field).touched
    // );
    return false;
  }

  login(): void {
    this.authService.attemptAuth(this.email, this.password).subscribe(
      (data: any) => {
        this.token.saveToken(data.token);

        if(this.token.getRole() === 'ADMIN' || this.token.getRole() === 'ADOPS') {
          this.router.navigate(['/user-list']);
        } else {
          this.router.navigate(['/app-list']);
        }
      }
    );
  }

}
