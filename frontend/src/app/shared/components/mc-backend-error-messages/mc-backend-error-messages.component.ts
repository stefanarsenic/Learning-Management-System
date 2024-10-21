import {Component, Input, OnInit} from '@angular/core';
import {BackendErrorsInterface} from '../../types/backendErrors.interface';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'mc-backend-error-messages',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './mc-backend-error-messages.component.html',
  styleUrl: './mc-backend-error-messages.component.css'
})
export class McBackendErrorMessagesComponent implements OnInit{
  @Input() backendErrors: BackendErrorsInterface = {}

  errorMessages: string[] = []

  ngOnInit(): void {
    this.errorMessages = Object.keys(this.backendErrors).map((name: string) => {
      const messages = this.backendErrors[name].join(' ');
      return `${name} ${messages}`;
    });
  }
}
