import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user';

import { URL_AUTH } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _user: User;
  private _token: string;

  constructor(private http: HttpClient) { }

  public get user(): User {
    if (this._user != null) {
      return this._user;
    } else if (this._user == null && sessionStorage.getItem('user') != null) {
      this._user = JSON.parse(sessionStorage.getItem('user')) as User;
      return this._user;
    }
    return new User();
  }

  public get token(): string {
    if (this._token != null) {
      return this._token;
    } else if (this._token == null && sessionStorage.getItem('token') != null) {
      this._token = sessionStorage.getItem('token');
      return this._token;
    }
    return null;
  }

  login(user: User): Observable<any> {
    const urlEndpoint = URL_AUTH + '/oauth/token';
    const clientCredentials = btoa('clientId' + ':' + 'secret');
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + clientCredentials});
    let params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('username', user.username);
    params.set('password', user.password);
    return this.http.post<any>(urlEndpoint, params.toString(), {headers: httpHeaders});
  }

  saveUser(accessToken: string) {
    let payload = this.obtainPayload(accessToken);
    this._user = new User();
    this._user.username = payload.user_name;
    this._user.roles = payload.authorities;
    sessionStorage.setItem('user', JSON.stringify(this._user));
  }

  saveAccessToken(accessToken: string) {
    this._token = accessToken;
    sessionStorage.setItem('token', accessToken);
  }

  obtainPayload(accessToken: string): any {
    if (accessToken != null) {
      return JSON.parse(atob(accessToken.split('.')[1]));
    }
    return null;
  }

  isAuthenticated(): boolean {
    let payload = this.obtainPayload(this.token);
    if (payload != null && payload.user_name && payload.user_name.length > 0) {
      return true;
    }
    return false;
  }

  hasRole(role: string): boolean {
    console.log('roles:' + this.user.roles);
    return this.user.roles.includes(role);
  }

  logout(): void {
    this._token = null;
    this._user = null;
    //sessionStorage.clear(); removes everything
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
  }
}
