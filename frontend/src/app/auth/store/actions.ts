import {createActionGroup, emptyProps, props} from '@ngrx/store';
import {RegisterRequestInterface} from '../types/registerRequest.interface';
import {CurrentUserInterface} from '../../shared/types/currentUser.interface';
import {BackendErrorsInterface} from '../../shared/types/backendErrors.interface';
import {LoginRequestInterface} from '../types/loginRequest.interface';
import { BackendErrorInterface } from '../../shared/types/backendError.interface';

export const authActions = createActionGroup({
  source: 'auth',
  events: {
    'Get current user': emptyProps(),
    'Get current user Success': props<{currentUser: CurrentUserInterface}>(),
    'Get current user Failure': props<{error: BackendErrorInterface}>(),

    Register: props<{request: RegisterRequestInterface}>(),
    'Register Success': props<{currentUser: CurrentUserInterface}>(),
    'Register Failure': props<{errors: BackendErrorsInterface}>(),

    Login: props<{request: LoginRequestInterface}>(),
    'Login Success': props<{currentUser: CurrentUserInterface}>(),
    'Login Failure': props<{errors: BackendErrorsInterface}>(),

    Logout: emptyProps()
  },
})
