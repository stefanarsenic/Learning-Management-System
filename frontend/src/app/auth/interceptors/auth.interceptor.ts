import { HttpInterceptorFn } from '@angular/common/http';
import { environment } from '../../../environments/environment.development';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const allowedRoutes = [`${environment.apiUrl}/api/auth/login`, `${environment.apiUrl}/api/auth/register`]

  const token = `Bearer ${localStorage.getItem('token') ?? ''}`;

  if(!allowedRoutes.includes(req.url)){
    const newReq = req.clone({
      setHeaders: {
        'Authorization': token
      }
    });

    return next(newReq);
  }

  return next(req);
};
