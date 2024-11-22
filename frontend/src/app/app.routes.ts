import { Routes } from '@angular/router';
import {RegisterComponent} from './auth/components/register/register.component';
import {LoginComponent} from './auth/components/login/login.component';
import { notLoggedInGuard } from './auth/guards/not-logged-in.guard';
import { loggedInGuard } from './auth/guards/logged-in.guard';
import { AppComponent } from './app.component';

export const routes: Routes = [
  {
    path: 'register',
    loadComponent: () => import('./auth/components/register/register.component').then(c => c.RegisterComponent),
    canActivate: [notLoggedInGuard]
  },
  {
    path: 'login',
    loadComponent: () => import('./auth/components/login/login.component').then(c => c.LoginComponent),
    canActivate: [notLoggedInGuard]
  }
];
