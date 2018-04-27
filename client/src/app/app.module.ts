import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { UserService } from './shared/user/user.service';
import { AppService } from './shared/app/app.service';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule } from '@angular/material';
import {MatSelectModule} from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";

import {Interceptor} from "./auth/interceptor";
import {TokenStorage} from "./auth/token.storage";
import { AppComponent } from './app.component';
import { UserListComponent } from './user-list/user-list.component';
import { UserEditComponent } from './user-edit/user-edit.component';
import { AppListComponent } from './app-list/app-list.component';
import { AppEditComponent } from './app-edit/app-edit.component';
import { AuthService } from './auth/auth.service';
import { AuthGuard } from './auth/auth.guard';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';


const appRoutes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
//  { path: '**', redirectTo: '' },
  { path: 'login', component: LoginComponent },
  { path: 'user-list', component: UserListComponent, canActivate: [AuthGuard]},
  { path: 'user-add', component: UserEditComponent, canActivate: [AuthGuard]},
  { path: 'user-edit/:id', component: UserEditComponent, canActivate: [AuthGuard]},
  { path: 'app-list', component: AppListComponent, canActivate: [AuthGuard]},
  { path: 'app-add', component: AppEditComponent, canActivate: [AuthGuard]},
  { path: 'app-edit/:id', component: AppEditComponent, canActivate: [AuthGuard]}
];

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserEditComponent,
    AppListComponent,
    AppEditComponent,
    HeaderComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    MatTableModule,
    MatSelectModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [UserService, AppService, AuthService, AuthGuard, TokenStorage, TokenStorage,
    {provide: HTTP_INTERCEPTORS,
    useClass: Interceptor,
    multi : true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
