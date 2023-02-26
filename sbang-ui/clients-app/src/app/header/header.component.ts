import { Component, OnInit } from '@angular/core';
import { AuthService } from '../users/auth.service';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  title = 'Web App Angular & Spring Skeleton';

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  logout(): void {
    const username = this.authService.user.username;
    this.authService.logout();
    swal.fire('Logout', `${username} successfully closed session`, 'success');
    this.router.navigate(['/login']);
  }

}
