import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClientsComponent } from './clients/clients.component';
import { FormComponent } from './clients/form.component';
import { LoginComponent } from './users/login.component';
import { AuthGuard } from './users/guards/auth.guard';
import { RoleGuard } from './users/guards/role.guard';
import { InvoiceDetailComponent } from './invoices/invoice-detail.component';
import { InvoicesComponent } from './invoices/invoices.component';

const routes: Routes = [
    { path: '', redirectTo: '/clients', pathMatch: 'full' },
    { path: 'clients', component: ClientsComponent },
    { path: 'clients/page/:page', component: ClientsComponent },
    { path: 'clients/form', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'} },
    { path: 'clients/form/:id', component: FormComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'} },
    { path: 'login', component: LoginComponent },
    { path: 'invoices/:id', component: InvoiceDetailComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ROLE_USER'} },
    { path: 'invoices/form/:clientId', component: InvoicesComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ROLE_ADMIN'} }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
