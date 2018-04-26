import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { AppService } from '../shared/app/app.service';
import { NgForm } from '@angular/forms';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-app-edit',
  templateUrl: './app-edit.component.html',
  styleUrls: ['./app-edit.component.css']
})
export class AppEditComponent implements OnInit, OnDestroy {

  app: any = {};
  sub: Subscription;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private appService: AppService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.appService.get(id).subscribe((app: any) => {
          if (app) {
            this.app = app;
            this.contentTypes.setValue(app.contentTypes);
          } else {
            console.log(`Application with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/app-list']);
  }

  save(form: any) {
    form.contentTypes = this.contentTypes.value;
    if(this.app.appUserId) {
      form.appUserId = this.app.appUserId;
      form.appUserName = this.app.appUserName;
    }
    this.appService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  remove(id) {
    this.appService.remove(id).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  appTypes = [{ value: 'IOS' }, { value: 'ANDROID' }, { value: 'WEBSITE' }];

  contentTypes : FormControl = new FormControl();
  contentTypeList = [ 'VIDEO', 'IMAGE', 'HTML'];
}
