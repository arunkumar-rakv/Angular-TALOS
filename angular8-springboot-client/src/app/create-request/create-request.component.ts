import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Request } from '../request';
import { RequestService } from '../request.service';

@Component({
  selector: 'app-create-request',
  templateUrl: './create-request.component.html',
  styleUrls: ['./create-request.component.css']
})
export class CreateRequestComponent implements OnInit {

  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  request: Request = new Request();
  id: number = 0;

  constructor(private _formBuilder: FormBuilder, private requestService: RequestService,
    private router: Router, private route: ActivatedRoute,) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    console.log('lofjsi', this.id);
    if(this.id) {
      this.requestService.getRequest(this.id)
      .subscribe(data => {
        console.log(data)
        this.request = data;
        this.firstFormGroup.setValue({
          appId: this.request.appId,
          region: this.request.region,
          environment: this.request.environment,
          appName: this.request.appName
        });
        this.secondFormGroup.setValue({
          asgDesired: this.request.asgDesired,
          asgMax: this.request.asgMax,
          asgMin: this.request.asgMin
        });
      }, error => console.log(error));
    }

    this.firstFormGroup = this._formBuilder.group({
      appId: [this.request.appId, Validators.required],
      region: [this.request.region, Validators.required],
      environment: [this.request.environment, Validators.required],
      appName: [this.request.appName, Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      asgDesired: [this.request.asgDesired, Validators.required],
      asgMax: [this.request.asgMax, Validators.required],
      asgMin: [this.request.asgMin, Validators.required]
    });
  }

  get firstCtrl(): any {
    return this.firstFormGroup.get('firstCtrl');
  }

  onSubmit() {
    this.request = {...this.request, ...this.firstFormGroup.value, ...this.secondFormGroup.value};
    console.log('this.request', this.request);
    if(this.id) {
      this.requestService.updateRequest(this.id, this.request)
      .subscribe(data => console.log(data), error => console.log(error));
    } else {
      this.requestService.createRequest(this.request)
        .subscribe(data => console.log(data), error => console.log(error));
    }
    this.router.navigate(['/submitted']);
  }

}
