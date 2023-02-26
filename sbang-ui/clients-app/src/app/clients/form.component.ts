import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from './client.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Region } from './region';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {
  client: Client = new Client();
  title = 'Create client';
  titleEdit = 'Edit client';
  regions: Region[];
  errors: string[];

  constructor(private clientService: ClientService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.loadclient();
    this.clientService.getRegions().subscribe(regiones => this.regions = regiones );
  }

  create(): void {
    this.clientService.create(this.client).subscribe(
      client => {
        this.router.navigate(['/clients']);
        swal.fire('Nuevo client', `client ${client.name} creado con éxito`, 'success');
      },
      err => {
        this.errors = err.error.errors as string[];
        console.error('Código del error desde el backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  loadclient(): void {
    this.activatedRoute.params.subscribe( params => {
      const id = params.id;
      if (id) {
        this.clientService.getClient(id).subscribe(client => this.client = client);
      }
    });
  }

  update(): void {
    this.client.invoices = null;
    this.clientService.update(this.client).subscribe(
      jsonResponse => {
        this.router.navigate(['/clients']);
        swal.fire('updated client', `${jsonResponse.message}: ${jsonResponse.client.name}`, 'success');
      },
      err => {
        this.errors = err.error.errors as string[];
        console.error('Error code from the backend: ' + err.status);
        console.error(err.error.errors);
      }
    );
  }

  compareRegion(o1: Region, o2: Region): boolean {
    if (o1 === undefined && o2 === undefined ) {
      return true;
    }

    return o1 === null || o2 === null || o1 === undefined || o2 === undefined ? false : o1.id === o2.id;
  }

}
