import { Component, OnInit } from '@angular/core';
import { InvoiceService } from './services/invoice.service';
import { Invoice } from './models/invoice';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-invoice-detail',
  templateUrl: './invoice-detail.component.html'
})
export class InvoiceDetailComponent implements OnInit {
  invoice: Invoice;
  title = 'Invoice';

  constructor(private invoiceService: InvoiceService, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      const id = +params.get('id');
      this.invoiceService.getInvoice(id).subscribe(invoice => this.invoice = invoice);
    });
  }

}
