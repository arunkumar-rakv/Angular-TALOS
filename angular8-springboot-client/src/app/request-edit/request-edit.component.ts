import { Component, OnInit } from '@angular/core';
import { Request } from '../request';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestService } from '../request.service';
@Component({
  selector: 'app-request-edit',
  templateUrl: './request-edit.component.html',
  styleUrls: ['./request-edit.component.css']
})
export class RequestEditComponent implements OnInit {

  id: number;
  request: Request;

  constructor(private route: ActivatedRoute,private router: Router,
    private requestService: RequestService) { }
    
  ngOnInit() {
    this.request = new Request();

    this.id = this.route.snapshot.params['id'];
    
    this.requestService.getRequest(this.id)
      .subscribe(data => {
        console.log(data)
        this.request = data;
      }, error => console.log(error));
  }

  updateEmployee() {
    this.requestService.updateRequest(this.id, this.request)
      .subscribe(data => console.log(data), error => console.log(error));
  }

  onSubmit() {
    this.updateEmployee();    
    this.router.navigate(['/submitted']);
  }

}
