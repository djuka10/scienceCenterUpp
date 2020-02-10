import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  fetchUsers() {
    return this.httpClient.get("http://localhost:8088/user/fetch") as Observable<any>;
  }

  //unos osnovnih podataka
  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8088/welcome/post/mainData/".concat(taskId), user) as Observable<any>;
  }
  //unos naucnih oblasti
  scienceArea(science,taskId) {
    return this.httpClient.post("http://localhost:8088/welcome/post/sciencearea/".concat(taskId), science) as Observable<any>;
  }

  

}
