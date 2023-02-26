import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../models/invoice';
import { Product } from '../models/product';

import { URL_BACKEND } from 'src/app/config/config';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  private urlEndpoint = URL_BACKEND + '/api/invoices';

  constructor(private http: HttpClient) { }

  getInvoice(id: number): Observable<Invoice> {
    return this.http.get<Invoice>(`${this.urlEndpoint}/${id}`);
  }

  deleteInvoice(id: number): Observable<void> {
    return this.http.delete<void>(`${this.urlEndpoint}/${id}`);
  }

  productFilter(term: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.urlEndpoint}/product-filter/${term}`);
  }

  createInvoice(invoice: Invoice): Observable<Invoice> {
    return this.http.post<Invoice>(this.urlEndpoint, invoice);
  }
}
