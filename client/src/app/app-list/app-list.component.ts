import { Component, OnInit } from '@angular/core';
import { AppService } from '../shared/app/app.service';
import { Response } from '@angular/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './app-list.component.html',
  styleUrls: ['./app-list.component.css']
})

export class AppListComponent implements OnInit {
  apps: Array<any>;
  displayedColumns = ['appName', 'appType', 'contentTypes', 'user'];

  constructor(private appService: AppService) { }

  applyFilter(filterValue: any) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.apps.filter = filterValue;
  }

  ngOnInit() {
    this.appService.getAll().subscribe((apps: any[]) => {
      this.apps = apps;
    });
  }
}
