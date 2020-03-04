import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { CreateEmployeeComponent } from './create-employee/create-employee.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { EmployeeDetailsComponent } from './employee-details/employee-details.component';
import { CreateRequestComponent } from './create-request/create-request.component';
import { RequestSubmitComponent } from './request-submit/request-submit.component';
import { RequestHomeComponent } from './request-home/request-home.component';
import { RequestViewComponent } from './request-view/request-view.component';

const routes: Routes = [
  { path: '', redirectTo: 'request-home', pathMatch: 'full' },
  { path: 'employees', component: EmployeeListComponent },
  { path: 'add', component: CreateEmployeeComponent },
  { path: 'update/:id', component: UpdateEmployeeComponent },
  { path: 'details/:id', component: EmployeeDetailsComponent },
  { path: 'request-home', component: RequestHomeComponent },
  { path: 'appwiz', component: CreateRequestComponent },
  { path: 'appwiz/:id', component: CreateRequestComponent },
  { path: 'submitted', component: RequestSubmitComponent },
  { path: 'request-view/:id', component: RequestViewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
