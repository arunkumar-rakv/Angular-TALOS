import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { Request } from '../request';
import { RequestService } from '../request.service';

@Component({
  selector: 'app-request-view',
  templateUrl: './request-view.component.html',
  styleUrls: ['./request-view.component.css']
})
export class RequestViewComponent implements OnInit {

  id: number;
  requestJSONUri;
  fileName: string;
  request: Request = new Request();

  constructor(private requestService: RequestService,
    private router: Router, private route: ActivatedRoute, private sanitizer: DomSanitizer) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.requestService.getRequest(this.id)
      .subscribe(data => {
        console.log(JSON.stringify(data));
        this.request = data;
        var requestJSON = JSON.stringify(this.request);
        this.requestJSONUri = this.sanitizer.bypassSecurityTrustUrl("data:application/json;charset=UTF-8," + encodeURIComponent(requestJSON));
        this.fileName = "application-request-" + this.request.id + ".json";
      }, error => console.log(error));
  }

}
