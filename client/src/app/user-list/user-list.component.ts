import { Component, OnInit } from '@angular/core';
import { UserService } from '../shared/user/user.service';
import { Response } from '@angular/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})

export class UserListComponent implements OnInit {
  users: Array<any>;
  displayedColumns = ['name', 'email', 'password', 'role', 'apps'];

  constructor(private userService: UserService) { }

  applyFilter(filterValue: any) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.users.filter = filterValue;
  }

  ngOnInit() {
    this.userService.getAll().subscribe((users: any[]) => {
      this.users = users;
    });
  }

  onRowClicked(row) {
    console.log('Row clicked: ', row);
    // [routerLink]="['/user-edit', element.id
  }
}
