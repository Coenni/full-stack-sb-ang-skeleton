import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { formatDate, DatePipe } from '@angular/common';

import { Client } from './client';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Router } from '@angular/router';
import { Region } from './region';

import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  urlEndPoint = URL_BACKEND + '/api/clients';

  constructor(private http: HttpClient,
              private router: Router) { }

  getRegions(): Observable<Region[]> {
    return this.http.get<Region[]>(this.urlEndPoint + '/regions');
  }

  getClients(page: number): Observable<any> {
    /* clientS converted as stream by using of
    return of(clientS); */
    return this.http.get(this.urlEndPoint + '/page/' + page).pipe(
      tap( (response: any) => {
        console.log('tap 1');
        (response.content as Client[]).forEach(client => {
          console.log(client.name);
        });
      }),
      map( (response: any) => {
        (response.content as Client[]).map(client => {
          client.name = client.name.toUpperCase();
          // let datePipe = new DatePipe('es');
          // client.createdAt = datePipe.transform(client.createdAt, 'EEEE dd, MMMM yyyy');
          // client.createdAt = formatDate(client.createdAt, 'dd-MM-yyyy', 'en_US');
          return client;
        });
        return response;
      }),
      tap(response => {
        console.log('tap 2');
        (response.content as Client[]).forEach(client => {
          console.log(client.name);
        });
      })
    );
  }

  create(client: Client): Observable<Client> {
    return this.http.post(this.urlEndPoint, client).pipe(
      map( (jsonResponse: any) => jsonResponse.client as Client),
      catchError(e => {
        if (e.status === 400) {
          return throwError(e);
        }
        if (e.error.message) {
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }

  getClient(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        if (e.status !== 401 && e.error.message) {
          this.router.navigate(['./clients']);
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }

  update(client: Client): Observable<any> {
    return this.http.put<any>(`${this.urlEndPoint}/${client.id}`, client).pipe(
      catchError(e => {
        if ( e.status === 400) {
          return throwError(e);
        }
        if (e.error.message) {
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Client> {
    return this.http.delete<Client>(`${this.urlEndPoint}/${id}`).pipe(
      catchError(e => {
        if (e.error.message) {
          console.error(e.error.message);
        }
        return throwError(e);
      })
    );
  }

  uploadPhoto(photo: File, id): Observable<HttpEvent<{}>> {
    const formData = new FormData();
    formData.append('file', photo);
    formData.append('id', id);

    const req = new HttpRequest('POST', `${this.urlEndPoint}/upload`, formData, {
      reportProgress: true
    });

    return this.http.request(req);
  }
}
