import { Component } from '@angular/core';
import {AsyncPipe, NgIf} from "@angular/common";
import {InputTextModule} from "primeng/inputtext";
import {
    McBackendErrorMessagesComponent
} from "../../../shared/components/mc-backend-error-messages/mc-backend-error-messages.component";
import {PaginatorModule} from "primeng/paginator";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {combineLatest, Observable} from 'rxjs';
import {selectIsSubmitting, selectValidationErrors} from '../../store/reducers';
import {Store} from '@ngrx/store';
import {RegisterRequestInterface} from '../../types/registerRequest.interface';
import {authActions} from '../../store/actions';
import {LoginRequestInterface} from '../../types/loginRequest.interface';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    AsyncPipe,
    InputTextModule,
    McBackendErrorMessagesComponent,
    NgIf,
    PaginatorModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

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
    email: ["", Validators.required],
    password: ["", Validators.required],
  })

  onSubmit(){
    const request: LoginRequestInterface = {
      user: this.form.getRawValue()
    };
    this.store.dispatch(authActions.login({request}));
  }
}
