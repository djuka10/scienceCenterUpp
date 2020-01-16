import { SignUpInfo } from './../../forms/registerForm';
import { JwtResponse } from './../../guard/jwt-response';
import { AuthLoginInfo } from './../../forms/loginForm';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class AuthService {
 // private loginUrl = API + 'auth/signin';
//  private signupUrl = API + 'auth/signup';

  constructor(private http: HttpClient) {
  }

  login(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>("http://localhost:8080/user/login", credentials, httpOptions);
  }

 /* signUp(info: SignUpInfo): Observable<any> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }*/
}
