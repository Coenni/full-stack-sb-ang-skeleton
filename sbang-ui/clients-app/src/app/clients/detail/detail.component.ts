import { Component, OnInit, Input } from '@angular/core';
import { Client } from '../client';
import { ClientService } from '../client.service';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { AuthService } from 'src/app/users/auth.service';
import { InvoiceService } from 'src/app/invoices/services/invoice.service';
import { Invoice } from 'src/app/invoices/models/invoice';

import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-client-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {
  @Input() client: Client;
  title = 'Customer details';
  selectedPhoto: File;
  progress = 0;
  urlBackend: string = URL_BACKEND;

  constructor(private clientService: ClientService,
              public modalService: ModalService,
              public authService: AuthService,
              private invoiceService: InvoiceService) {
  }

  ngOnInit() {}

  selectPhoto(event: any) {
    this.selectedPhoto = event.target.files[0];
    this.progress = 0;
    console.log(this.selectedPhoto);
    if (this.selectedPhoto.type.indexOf('image') < 0) {
      swal.fire('Error seleccionar imagen: ', 'El archivo debe ser del tipo imagen', 'error');
      this.selectedPhoto = null;
    }
  }

  uploadPhoto() {
    if (!this.selectedPhoto) {
      swal.fire('Error Upload: ', 'Debe seleccionar una foto', 'error');
    } else {
      this.clientService.uploadPhoto(this.selectedPhoto, this.client.id).subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round((event.loaded / event.total) * 100);
          } else if (event.type === HttpEventType.Response) {
            const response: any = event.body;
            this.client = response.client as Client;
            this.modalService.notifyUpload.emit(this.client);
            swal.fire('La foto se ha subido correctamente', response.message, 'success');
          }
        }
      );
    }
  }

  closeModal() {
    this.modalService.closeModal();
    this.selectedPhoto = null;
    this.progress = 0;
  }

  deleteInvoice(invoice: Invoice): void {
    swal.fire({
      title: 'Are you sure?',
      text: `Invoice: ${invoice.description} will be permanently deleted`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#28a745',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete',
      cancelButtonText: 'No, cancel'
    }).then((result) => {
      if (result.value) {
        this.invoiceService.deleteInvoice(invoice.id).subscribe(
          response => {
            this.client.invoices = this.client.invoices.filter(clientFact => clientFact !== invoice);
            swal.fire('Invoice deleted', `Invoice: ${invoice.description} successfully deleted`, 'success');
          }
        );
      }
    });
  }

}
