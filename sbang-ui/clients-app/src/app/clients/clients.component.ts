import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from './client.service';
import swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { ModalService } from './detail/modal.service';
import { AuthService } from '../users/auth.service';

import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html'
})
export class ClientsComponent implements OnInit {

  clients: Client[];
  paginator: any;
  selectedClient: Client;
  urlBackend: string = URL_BACKEND;

  constructor(public clientService: ClientService,
              public activatedRoute: ActivatedRoute,
              public modalService: ModalService,
              public authService: AuthService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe( params => {
      let page: number = +params.get('page');
      if (!page) {
        page = 0;
      }
      this.clientService.getClients(page).pipe(
        tap(response => {
          console.log('tap 3 - client Component');
          (response.content as Client[]).forEach(client => {
            console.log(client.name);
          });
        })
      ).subscribe(response => {
        this.clients = response.content as Client[];
        this.paginator = response;
        });
    });
    this.modalService.notifyUpload.subscribe( clientUpdated => {
      this.clients = this.clients.map(client => {
        if (client.id === clientUpdated.id) {
          client.photo = clientUpdated.photo;
        }
        return client;
      });
    }
    );
  }

  delete(client: Client): void {
    swal.fire({
      title: 'Are you sure?',
      text: `Client ${client.name} ${client.lastname} will be permanently eliminated`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#28a745',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete',
      cancelButtonText: 'No, cancel'
    }).then((result) => {
      if (result.value) {
        this.clientService.delete(client.id).subscribe(
          response => {
            this.clients = this.clients.filter(cli => cli !== client);
            swal.fire('client deleted', `client ${client.name} successfully deleted`, 'success');
          }
        );
      }
    });
  }

  openModal(client: Client) {
    this.selectedClient = client;
    this.modalService.openModal();
  }

  signInMessage() {
    swal.fire('restricted area', 'Please login to view details', 'warning');
  }

}
