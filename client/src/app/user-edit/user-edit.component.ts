import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../shared/user/user.service';
import { NgForm } from '@angular/forms';
import { TokenStorage } from '../auth/token.storage';


@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit, OnDestroy {

  user: any = {};
  userRoles = [{ value: 'ADOPS' }, { value: 'PUBLISHER' }];
  sub: Subscription;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private tokenStorage: TokenStorage
  ) { }

  ngOnInit() {

    if (this.tokenStorage.getRole() === 'ADOPS') {
        this.userRoles = [{ value: 'PUBLISHER' }];
    }

    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.userService.getById(id).subscribe((user: any) => {
          if (user) {
            this.user = user;
          } else {
            console.log(`User with id '${id}' not found, returning to list`);
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
    this.router.navigate(['/user-list']);
  }

  save(form: NgForm) {
    this.userService.save(form).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }

  remove(id) {
    this.userService.remove(id).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }




}
