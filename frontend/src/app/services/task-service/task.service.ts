import { Observable } from 'rxjs';
import { TokenStorageService } from './../auth/token-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private TASKAPI = "http://localhost:8080/task/";


  constructor(private httpClient: HttpClient, private tokenStorageService: TokenStorageService) { }

  private genHeader(): HttpHeaders {
    return new HttpHeaders().set('Authorization', this.tokenStorageService.getToken());
  }

  getAllUserTask(id: string): Observable<any> {
    return this.httpClient.get(this.TASKAPI + 'assignedToUser/'+ id, {headers: this.genHeader()});
  }

  getTask(id: number): Observable<any> {
    return this.httpClient.get(this.TASKAPI + "8");
  }

  removeTask(taskId: string): Observable<any> {
    return this.httpClient.get(this.TASKAPI + 'removeTask/'.concat(taskId));
  }
}
