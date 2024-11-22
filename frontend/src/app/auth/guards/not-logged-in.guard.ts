import { CanActivateFn } from '@angular/router';

export const notLoggedInGuard: CanActivateFn = (route, state) => {

  const token = localStorage.getItem('token') ? false : true;
  return token;
};
