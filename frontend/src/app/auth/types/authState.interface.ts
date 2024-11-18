import {CurrentUserInterface} from '../../shared/types/currentUser.interface';
import {BackendErrorsInterface} from '../../shared/types/backendErrors.interface';
import { BackendErrorInterface } from '../../shared/types/backendError.interface';

export interface AuthStateInterface {
  isSubmitting: boolean,
  currentUser: CurrentUserInterface | null | undefined,
  isLoading: boolean,
  validationError: BackendErrorInterface | null,
  validationErrors: BackendErrorsInterface | null
}
