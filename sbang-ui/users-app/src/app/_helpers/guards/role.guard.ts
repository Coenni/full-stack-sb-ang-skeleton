import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../auth.service';
import {AlertService} from "@app/_services";
import {Alert, AlertType} from "@app/_models";

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router, private alertService: AlertService) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return false;
    }
    let role = next.data.role as string;
    console.log(role);
    if (this.authService.hasRole(role)) {
      return true;
    }
    // swal.fire('Access denied', `Your user (${this.authService.user.username}) is not allowed to access to that content.`, 'warning');
    this.alertService.alert(new Alert({
      message: `Your user (${this.authService.user.username}) is not allowed to access to that content.`,
      type: AlertType.Error
      })
    );
    this.router.navigate(['/login']);
    return false;
  }
}
