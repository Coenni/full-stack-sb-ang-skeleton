import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { AuthService } from '../auth.service';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import {AlertService} from "@app/_services";
import {Alert, AlertType} from "@app/_models";

/** Pass untouched request through to the next request handler. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router, private alertService: AlertService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError(e => {
        if (e.status == 401) {
          if (this.authService.isAuthenticated()) {
            this.authService.logout();
          }
          this.router.navigate(['/login']);
        } else if (e.status == 403) {
          this.alertService.alert(
            new Alert({
              type: AlertType.Error,
              message: `Your user (${this.authService.user.username}) is not allowed to access to that content.`,

            })
            // 'Access denied',
            // `Your user (${this.authService.user.username}) is not allowed to access to that content.`,
            // 'warning'
          );
          this.router.navigate(['/clients']);
        }
        return throwError(e);
      })
    );
  }
}
