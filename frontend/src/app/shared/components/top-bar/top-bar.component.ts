import { Component } from '@angular/core';
import {combineReducers, Store} from '@ngrx/store';
import {selectCurrentUser} from '../../../auth/store/reducers';
import {combineLatest} from 'rxjs/internal/operators/combineLatest';
import {AsyncPipe, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {combineLatestAll, combineLatestWith, map, Observable, of, OperatorFunction} from 'rxjs';
import {CurrentUserInterface} from '../../types/currentUser.interface';

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

  data$ = this.store.select(selectCurrentUser).pipe(
    combineLatestWith(of({})),
    map(([currentUser]) => ({
      currentUser
    }))
  );
  constructor(private store: Store) {
  }
}
