import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { URL_AUTH } from 'src/app/config/config';
import {User} from "@app/_models";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _user: User | undefined;
  private _token: string | undefined;

  constructor(private http: HttpClient) { }

  public get user(): User {
    if (this._user != null) {
      return this._user;
    } else if (this._user == null && sessionStorage.getItem('user') != null) {
      // @ts-ignore
      this._user = JSON.parse(sessionStorage.getItem('user')) as User;
      return this._user;
    }
    return new User();
  }

  public get token(): string | undefined {
    if (this._token != null) {
      return this._token;
    } else if (this._token == null && localStorage.getItem('token') != null) {
      this._token = localStorage.getItem('token') || undefined;
      return this._token;
    }
    return undefined;
  }

  login(user: User): Observable<any> {
    const urlEndpoint = URL_AUTH + '/oauth/token';
    const clientCredentials = btoa('clientId' + ':' + 'secret');
    const httpHeaders = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + clientCredentials});
    let params = new URLSearchParams();
    params.set('grant_type', 'password');
    params.set('username', user.username!!);
    params.set('password', user.password!!);
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
    let payload = this.obtainPayload(this.token as string);
    return !!(payload != null && payload.user_name && payload.user_name.length > 0);

  }

  hasRole(role: string): boolean {
    console.log('roles:' + this.user.roles);
    return this.user.roles.includes(role);
  }

  logout(): void {
    this._token = undefined;
    this._user = undefined;
    //sessionStorage.clear(); removes everything
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
  }
}
