import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AddTextService {

  private ADDTEXT_API = "http://localhost:8080/addText";

  constructor(private httpClient: HttpClient) { }


  startProcess(process: string, userId: string){
    return this.httpClient.get(this.ADDTEXT_API.concat('/start/').concat(process).concat('/') + userId) as Observable<any>
  }

  getUserTask(processInstance : string) {
    return this.httpClient.get(this.ADDTEXT_API.concat("/get/userTask/").concat(processInstance)) as Observable<any>
  }

  getTasks(processInstance : string){

    return this.httpClient.get(this.ADDTEXT_API.concat('/get/tasks/').concat(processInstance)) as Observable<any>
  }

  postNewArticle(article, taskId) : Observable<any>{
    return this.httpClient.post(this.ADDTEXT_API + "/postArticle/".concat(taskId), article);
  }

  analizeArticle(taskId: string){
    return this.httpClient.get(this.ADDTEXT_API + '/analizeBasic/'.concat(taskId));
  }
  
  preanalizeArticle(taskId: string){
    return this.httpClient.get(this.ADDTEXT_API + '/preanalizeBasic/'.concat(taskId));
  }

  analizeBasicResult(article, taskId: string): Observable<any> {
    return this.httpClient.post(this.ADDTEXT_API + '/post/basicAnalize/'.concat(taskId),article);
  }

  postTimeForReviewing(article,taskId: string): Observable<any> {
    return this.httpClient.post(this.ADDTEXT_API + '/post/timeForReviewing/'.concat(taskId),article);
  }

  getPDF(invoiceId : number): Observable<Blob>
     {
         
         var authorization = 'Bearer '+sessionStorage.getItem("access_token");

         const headers = new HttpHeaders({ 'Content-Type': 'application/json',
         "Authorization": authorization, responseType : 'blob'});

         return this.httpClient.get<Blob>(this.ADDTEXT_API + '/pdf/'+invoiceId, { headers : headers,responseType : 
         'blob' as 'json'});
     }
     
  startUpdateArticle(taskId: string): Observable<any>{
    return this.httpClient.get(this.ADDTEXT_API + '/updateArticleStart/'.concat(taskId)); 
  }

  updateArticle(article, taskId) : Observable<any>{
    return this.httpClient.put(this.ADDTEXT_API + "/updateArticle/".concat(taskId), article);
  }

  startUpdateArticle2(taskId: string): Observable<any>{
    return this.httpClient.get(this.ADDTEXT_API + 'updateArticleStart2/'.concat(taskId)); 
  }

  startReviewArticle() {

  }

  startUpdateChanges(taskId: string): Observable<any>{
    return this.httpClient.get(this.ADDTEXT_API + '/updateArticleChangesStart/'.concat(taskId)); 
  }
}
