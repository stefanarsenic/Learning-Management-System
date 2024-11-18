import {Actions, createEffect, ofType} from '@ngrx/effects';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {authActions} from './actions';
import {catchError, map, of, switchMap, tap} from 'rxjs';
import {CurrentUserInterface} from '../../shared/types/currentUser.interface';
import {HttpErrorResponse} from '@angular/common/http';
import {PersistanceService} from '../../shared/services/persistance.service';
import {Router} from '@angular/router';
import { BackendErrorInterface } from '../../shared/types/backendError.interface';

export const getCurrentUserEffect = createEffect((
  actions$ = inject(Actions),
  authService: AuthService = inject(AuthService),
) => {
  return actions$.pipe(
    ofType(authActions.getCurrentUser),
    switchMap(() => {
      return authService.getCurrentUser().pipe(
        map((currentUser: CurrentUserInterface) => {
          return authActions.getCurrentUserSuccess({currentUser});
        }),
        catchError((error: BackendErrorInterface) => {
          return of(authActions.getCurrentUserFailure({error: error}));
        })
      )
    })
  )
}, {functional: true})

//logout effect

export const registerEffect = createEffect((
  actions$ = inject(Actions),
  authService: AuthService = inject(AuthService),
  persistanceService: PersistanceService = inject(PersistanceService)
) => {
  return actions$.pipe(
    ofType(authActions.register),
    switchMap(({request}) => {
      return authService.register(request).pipe(
        map((currentUser: CurrentUserInterface) => {
          persistanceService.set('token', currentUser.token);
          return authActions.registerSuccess({currentUser})
        }),
        catchError((errorResponse: HttpErrorResponse) => {
          return of(authActions.registerFailure({
            errors: errorResponse?.error?.errors
          }))
        })
      )
    })
  )
}, {functional: true})

export const redirectAfterRegisterEffect = createEffect(
  (
    actions$ = inject(Actions),
    router = inject(Router)
  ) => {
    return actions$.pipe(
      ofType(authActions.registerSuccess),
      tap(() => {
        router.navigateByUrl('/')
      })
    )
  },
  {functional: true, dispatch: false}
)

export const loginEffects = createEffect((
  actions$ = inject(Actions),
  authService: AuthService = inject(AuthService),
  persistanceService: PersistanceService = inject(PersistanceService)
) => {
  return actions$.pipe(
    ofType(authActions.login),
    switchMap(({request}) => {
      return authService.login(request).pipe(
        map((currentUser: CurrentUserInterface) => {
          persistanceService.set('token', currentUser.token);
          return authActions.loginSuccess({currentUser})
        }),
        catchError((errorResponse: HttpErrorResponse) => {
          return of(authActions.loginFailure({
            errors: errorResponse?.error?.errors
          }))
        })
      )
    })
  )
}, {functional: true})

export const redirectAfterLoginEffect = createEffect(
  (
    actions$ = inject(Actions),
    router = inject(Router)
  ) => {
    return actions$.pipe(
      ofType(authActions.loginSuccess),
      tap(() => {
        router.navigateByUrl('/')
      })
    )
  },
  {functional: true, dispatch: false}
)
