import { NewCartItemRequest } from './../../model/shoppingcart-new-item-request';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor(private http: HttpClient) { }

  getAllEditions(id: number): Observable<any> {
    return this.http.get('https://localhost:8088/test/getEditions/'+ id);
  }

  getEditionById(id: number): Observable<any> {
    return this.http.get('https://localhost:8088/test/getOneEdition/'+ id);
  }

  getAllArticles(id: number): Observable<any> {
    return this.http.get('https://localhost:8088/test/getArticles/'+ id);
  }

  createCart(user) : Observable<any> {
    return this.http.get('https://localhost:8088/test/newCart/'+user);
  }

  getCart(id: number) : Observable<any> {
    return this.http.get('https://localhost:8088/test/getCart/' + id);
  }

  removeCart(id: string) : Observable<any> {
    return this.http.get('https://localhost:8088/test/removeItemFromCart/' + id);
  }

  getUserTx() : Observable<any> {
    return this.http.get('https://localhost:8088/test/getUserTxs');
  }

  addToCart(request: NewCartItemRequest) : Observable<any> {
    return this.http.post('https://localhost:8088/test/addItemToCart', request);
  }
}
