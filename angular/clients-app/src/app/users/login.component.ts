import { Component, OnInit } from '@angular/core';
import { User } from './user';
import swal from 'sweetalert2';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  title = 'Login';
  user: User;

  constructor(private authService: AuthService, private router: Router) {
    this.user = new User();
   }

  ngOnInit() {
    if (this.authService.isAuthenticated()) {
      swal.fire('Login', `You are signed in as: ${this.authService.user.username}`, 'info');
      this.router.navigate(['/clients']);
    }
  }

  login(): void {
    console.log(this.user);
    if (this.user.username == null || this.user.password == null) {
      swal.fire('Error login', 'Username and password cannot be empty', 'error');
      return;
    }
    this.authService.login(this.user).subscribe(response => {
      console.log(response);

      this.authService.saveUser(response.access_token);
      this.authService.saveAccessToken(response.access_token);
      const user = this.authService.user;
      this.router.navigate(['/clients']);
      swal.fire('Login', `Welcome ${user.username}`, 'success');
    }, err => {
      if (err.status == 400) {
        swal.fire('Error login', 'Username or password invalid!', 'error');
      }
    });
  }

}
