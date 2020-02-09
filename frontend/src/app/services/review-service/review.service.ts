import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private REVIEW_API = "http://localhost:8080/review/";

  constructor(private httpClient: HttpClient) { }

  startAddingReviewers(taskId: string) : Observable<any> {
    return this.httpClient.get(this.REVIEW_API + 'addReviewer/'.concat(taskId));
  }

  postAddingReviewers(taskId: string, obj: any): Observable<any> {
    return this.httpClient.post(this.REVIEW_API + 'addReviewer/'.concat(taskId), obj);
  }

  startReviewArticle(taskId: string): Observable<any>{
    return this.httpClient.get(this.REVIEW_API + 'start/'.concat(taskId)); 
  }

  postReview(review, taskId: string) : Observable<any>{
    return this.httpClient.post(this.REVIEW_API + "reviewPost/".concat(taskId), review);
  }

  startReviewArticleByEditor(taskId: string): Observable<any>{
    return this.httpClient.get(this.REVIEW_API + 'editorReview/'.concat(taskId)); 
  }

  postReviewByEditor(review, taskId: string) : Observable<any>{
    return this.httpClient.post(this.REVIEW_API + "reviewByEditorPost/".concat(taskId), review);
  }

  startAddingReviewerWhenError(taskId: string) : Observable<any> {
    return this.httpClient.get(this.REVIEW_API + 'addReviewerWhenError/'.concat(taskId));
  }

  postAddingReviewerWhenError(taskId: string, obj: any): Observable<any> {
    return this.httpClient.post(this.REVIEW_API + 'addReviewerWhenError/'.concat(taskId), obj);
  }

  postAddingReviewerAdditional(taskId: string, obj: any): Observable<any> {
    return this.httpClient.post(this.REVIEW_API + 'addAdditionalReviewer/'.concat(taskId), obj);
  }

}
