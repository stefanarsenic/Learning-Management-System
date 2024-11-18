import { Component } from '@angular/core';
import {combineReducers, Store} from '@ngrx/store';
import {selectCurrentUser} from '../../../auth/store/reducers';
import {combineLatest} from 'rxjs/internal/operators/combineLatest';
import {AsyncPipe, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {combineLatestAll, combineLatestWith, map, Observable, of, OperatorFunction} from 'rxjs';
import {CurrentUserInterface} from '../../types/currentUser.interface';
import { authActions } from '../../../auth/store/actions';

@Component({
  selector: 'top-bar',
  standalone: true,
  imports: [
    NgIf,
    AsyncPipe,
    RouterLink
  ],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.css'
})
export class TopBarComponent {

  currentUser$ = this.store.select(selectCurrentUser);

  constructor(private store: Store) {
  }

  logout(): void {
    
  }
}
