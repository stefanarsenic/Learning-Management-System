import {createFeature, createReducer, on} from '@ngrx/store';
import {AuthStateInterface} from '../types/authState.interface';
import {authActions} from './actions';
import {routerNavigatedAction} from '@ngrx/router-store';

const initialState: AuthStateInterface = {
  isSubmitting: false,
  isLoading: false,
  currentUser: undefined,
  validationError: null,
  validationErrors: null
}

const authFeature = createFeature({
  name: "auth",
  reducer: createReducer(
    initialState,
    //register 
    on(authActions.register, state => ({
      ...state,
      isSubmitting: true,
      validationErrors: null
    })),
    on(authActions.registerSuccess, (state, action) => ({
      ...state,
      isSubmitting: false,
      currentUser: action.currentUser
    })),
    on(authActions.registerFailure, (state, action) => ({
      ...state,
      isSubmitting: false,
      validationErrors: action.errors
    })),
    //login
    on(authActions.login, state => ({
      ...state,
      isSubmitting: true,
      validationErrors: null
    })),
    on(authActions.loginSuccess, (state, action) => ({
      ...state,
      isSubmitting: false,
      currentUser: action.currentUser
    })),
    on(authActions.loginFailure, (state, action) => ({
      ...state,
      isSubmitting: false,
      validationErrors: action.errors
    })),
    //get current user
    on(authActions.getCurrentUser, state => ({
      ...state,
    })),
    on(authActions.getCurrentUserSuccess, (state, action) => ({
      ...state,
      currentUser: action.currentUser
    })),
    on(authActions.getCurrentUserFailure, (state, action) => ({
      ...state,
      validationError: action.error
    })),

    on(routerNavigatedAction, (state) => ({
      ...state,
      validationErrors: null
    }))
  )
})

export const {
  name: authFeatureKey,
  reducer: authReducer,
  selectIsSubmitting,
  selectIsLoading,
  selectCurrentUser,
  selectValidationErrors
} = authFeature
