import { Product } from './product';

export class InvoiceItems {

    product: Product;
    quantity = 1;
    calculatedImport: number;

    public importCalculation(): number {
        return this.quantity * this.product.price;
    }
}
