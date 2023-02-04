import { Component, OnInit } from '@angular/core';
import { Invoice } from './models/invoice';
import { ClientService } from '../clients/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, flatMap} from 'rxjs/operators';
import { InvoiceService } from './services/invoice.service';
import { Product } from './models/product';
import { InvoiceItems } from './models/invoice-items';
import swal from 'sweetalert2';

@Component({
  selector: 'app-invocies',
  templateUrl: './invoices.component.html'
})
export class InvoicesComponent implements OnInit {
  title = 'New Invoice';
  invoice: Invoice = new Invoice();
  autocompleteControl = new FormControl();
  productsFilters: Observable<Product[]>;

  constructor(private clientService: ClientService,
              private activatedRoute: ActivatedRoute,
              private facturaService: InvoiceService,
              private router: Router) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      const clientId = +params.get('clientId');
      this.clientService.getClient(clientId).subscribe(client => this.invoice.client = client);
    });
    this.productsFilters = this.autocompleteControl.valueChanges
    .pipe(
      map(value => typeof value === 'string' ? value : value.name),
      flatMap(value => value ? this._filter(value) : [])
    );
  }

  private _filter(value: string): Observable<Product[]> {
    const filterValue = value.toLowerCase();

    return this.facturaService.productFilter(filterValue);
  }

  showProductName(product?: Product): string | undefined {
    return product ? product.name : undefined;
  }

  productSelected(event: any): void {
    const product = event.option.value as Product;
    console.log(product);

    if (this.itemExists(product.id)) {
      this.incrementQuantity(product.id);
    } else {
      const invoiceItems = new InvoiceItems();
      invoiceItems.product = product;
      this.invoice.invoiceItems.push(invoiceItems);
    }
    this.autocompleteControl.setValue('');
    event.option.focus();
    event.option.deselect();
  }

  updateQuantity(id: number, event: any): void {
    const quantity: number = event.target.value as number;
    if (quantity === 0 || quantity < 1) {
      return this.deleteItem(id);
    }
    this.invoice.invoiceItems = this.invoice.invoiceItems.map((item: InvoiceItems) => {
      if (id === item.product.id) {
        item.quantity = quantity;
      }
      return item;
    });
  }

  itemExists(id: number): boolean {
    let exist = false;
    this.invoice.invoiceItems.forEach((item: InvoiceItems) => {
      if (id === item.product.id) {
        exist = true;
      }
    });
    return exist;
  }

  incrementQuantity(id: number) {
    this.invoice.invoiceItems = this.invoice.invoiceItems.map((item: InvoiceItems) => {
      if (id === item.product.id) {
        ++item.quantity;
      }
      return item;
    });
  }

  deleteItem(id: number): void {
    this.invoice.invoiceItems = this.invoice.invoiceItems.filter((item: InvoiceItems) => item.product.id !== id);
  }

  createInvoice(facturaForm): void {
    console.log(this.invoice);
    if (this.invoice.invoiceItems.length === 0) {
      this.autocompleteControl.setErrors({ invalid: true});
    }
    if (facturaForm.form.valid && this.invoice.invoiceItems.length > 0) {
      this.facturaService.createInvoice(this.invoice).subscribe(factura => {
        swal.fire(this.title, `Invoice: ${factura.description} creada con éxito!`, 'success');
        this.router.navigate(['/clients']);
      });
    }
  }

}
