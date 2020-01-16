import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) { }

  //unos osnovnih podataka o magazinu
  addMagazine(magazine,taskId) {
      return this.httpClient.post("http://localhost:8080/welcome/post/mainMagazineData/".concat(taskId), magazine) as Observable<any>;
  }

  addEditorReviewer(magazine,taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/post/addEditorReviewer/".concat(taskId), magazine) as Observable<any>;
  }

  adminConfirm(magazine,taskId) {
    return this.httpClient.post("http://localhost:8080/welcome/post/confirmMagazine/".concat(taskId), magazine) as Observable<any>;
  }


}
