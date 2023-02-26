import { Client } from './client';

export const CLIENTS: Client[] = [
    {id: 1, name: 'John', lastname: 'Doe', email: 'johndoe@johndoe.es', createdAt: '16-12-2019', photo: 'photo.png', region: {id: 3, name: 'Europe'}, invoices: []},
    {id: 2, name: 'Mary', lastname: 'Doe', email: 'mary@johndoe.es', createdAt: '16-12-2019', photo: 'photo.png', region: {id: 3, name: 'Europe'}, invoices: []},
    {id: 3, name: 'Edward', lastname: 'Doe', email: 'edward@johndoe.es', createdAt: '16-12-2019', photo: 'photo.png', region: {id: 3, name: 'Europe'}, invoices: []},
    {id: 4, name: 'George', lastname: 'Doe', email: 'george@johndoe.es', createdAt: '16-12-2019', photo: 'photo.png', region: {id: 3, name: 'Europe'}, invoices: []},
    {id: 5, name: 'Alex', lastname: 'Doe', email: 'alex@johndoe.es', createdAt: '16-12-2019', photo: 'photo.png', region: {id: 3, name: 'Europe'}, invoices: []}/*,
    {id: 6, name: 'Mad', lastname: 'Doe', email: 'mad@johndoe.es', createdAt: '16-12-2019'},
    {id: 7, name: 'Matt', lastname: 'Doe', email: 'matt@johndoe.es', createdAt: '16-12-2019'},
    {id: 8, name: 'Tristana', lastname: 'Doe', email: 'trist@johndoe.es', createdAt: '16-12-2019'},
    {id: 9, name: 'Harry', lastname: 'Doe', email: 'harr@johndoe.es', createdAt: '16-12-2019'},
    {id: 10, name: 'William', lastname: 'Doe', email: 'wil@johndoe.es', createdAt: '16-12-2019'}*/
  ];
