import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AppService {
  public API = '//localhost:8080';
  public APP_API = this.API + '/apps';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.APP_API);
  }

  get(id: string) {
    return this.http.get(this.APP_API + '/' + id);
  }

  save(app: any): Observable<any> {
    let result: Observable<Object>;
    if (app['id']) {
      result = this.http.put(this.APP_API, app);
    } else {
      result = this.http.post(this.APP_API, app);
    }
    return result;
  }

  remove(id: string) {
    return this.http.delete(this.APP_API + '/' + id);
  }
}
