import { ComponentFixture, TestBed } from '@angular/core/testing';

import { McBackendErrorMessagesComponent } from './mc-backend-error-messages.component';

describe('McBackendErrorMessagesComponent', () => {
  let component: McBackendErrorMessagesComponent;
  let fixture: ComponentFixture<McBackendErrorMessagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [McBackendErrorMessagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(McBackendErrorMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
