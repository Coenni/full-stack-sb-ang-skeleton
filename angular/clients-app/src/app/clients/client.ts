import { Region } from './region';
import { Invoice } from '../invoices/models/invoice';

export class Client {
    id: number;
    name: string;
    lastname: string;
    createdAt: string;
    email: string;
    photo: string;
    region: Region;
    invoices: Array<Invoice> = [];
}
