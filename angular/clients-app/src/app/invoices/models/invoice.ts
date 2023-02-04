import { InvoiceItems } from './invoice-items';
import { Client } from 'src/app/clients/client';

export class Invoice {

    id: number;
    description: string;
    note: string;
    invoiceItems: Array<InvoiceItems> = [];
    client: Client;
    total: number;
    createdAt: string;

    calculateTotal(): number {
        this.total = 0;
        this.invoiceItems.forEach((item: InvoiceItems) => {
            this.total += item.importCalculation();
        });
        return this.total;
    }
}
