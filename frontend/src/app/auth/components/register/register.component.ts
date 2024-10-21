import { Component } from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Store} from '@ngrx/store';
import {authActions} from '../../store/actions';
import {RegisterRequestInterface} from '../../types/registerRequest.interface';
import {ChipsModule} from 'primeng/chips';
import {FloatLabelModule} from 'primeng/floatlabel';
import {ButtonModule} from 'primeng/button';
import {selectIsSubmitting, selectValidationErrors} from '../../store/reducers';
import {AuthStateInterface} from '../../types/authState.interface';
import {AsyncPipe, NgIf} from '@angular/common';
import {AuthService} from '../../services/auth.service';
import {combineLatest, Observable} from 'rxjs';
import {
  McBackendErrorMessagesComponent
} from '../../../shared/components/mc-backend-error-messages/mc-backend-error-messages.component';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    ChipsModule,
    FloatLabelModule,
    ButtonModule,
    AsyncPipe,
    NgIf,
    McBackendErrorMessagesComponent,
    RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  data$: Observable<any> = combineLatest({
    isSubmitting: this.store.select(selectIsSubmitting),
    backendErrors: this.store.select(selectValidationErrors)
  })

  constructor(
    private fb: FormBuilder,
    private store: Store,
  ) {
  }

  form = this.fb.nonNullable.group({
    username: ["", Validators.required],
    password: ["", Validators.required],
    email: ["", Validators.required],
  })

  onSubmit(){
    const request: RegisterRequestInterface = {
      user: this.form.getRawValue()
    };
    this.store.dispatch(authActions.register({request}));
  }
}
