import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { RequestService } from '../request.service';
import { Request } from '../request';

@Component({
  selector: 'app-request-home',
  templateUrl: './request-home.component.html',
  styleUrls: ['./request-home.component.css']
})
export class RequestHomeComponent implements OnInit {

  requestList: Request[];
  displayedColumns: string[] = ['id', 'appId', 'region', 'environment', 
    'appName', 'asgDesired', 'asgMax', 'asgMin', 'actions'];
  dataSource = new MatTableDataSource<Request>();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor( private requestService: RequestService, private router: Router) { }

  ngOnInit() {
    this.getRequestList();
  }

  getRequestList() {
    this.requestService.getRequestList().subscribe(requests => {
      console.log('requests', requests);
      this.requestList = requests as Request[];
      this.dataSource = new MatTableDataSource<Request>(this.requestList);
      this.dataSource.paginator = this.paginator;
    });
  }

  editRequest(requestId) {
    console.log("requestId", requestId);
    this.router.navigate(['appwiz', requestId]);
  }

  deleteRequest(requestId: number) {
    console.log("requestId", requestId);
    this.requestService.deleteRequest(requestId).subscribe(data => {
      console.log(data);
      this.getRequestList();
    },
    error => console.log(error)
    );
  }

}
