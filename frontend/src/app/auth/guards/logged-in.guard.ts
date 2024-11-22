import { CanActivateFn } from '@angular/router';

export const loggedInGuard: CanActivateFn = (route, state) => {

  const token = localStorage.getItem('token') ? true : false;
  return token;
};
