import { AuthService } from './../services/auth/auth.service';
import { Router } from '@angular/router';
import { TokenStorageService } from './../services/auth/token-storage.service';
import { Token } from './../guard/Token';
import { AuthLoginInfo } from './../forms/loginForm';
import { Component, OnInit } from '@angular/core';
import { User } from '../guard/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  idCompany;
  user: User = new User();
  private loginInfo : AuthLoginInfo;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  token: Token = new Token();

  checkFirst: boolean = false;

  constructor(private authService: AuthService, 
    private router: Router, 
    private tokenStorage: TokenStorageService) { }

  ngOnInit() {
  }

  login() {
    this.loginInfo = new AuthLoginInfo(this.user.email,this.user.password);
    this.authService.login(this.loginInfo).subscribe( data => {

      if(data.accessToken === undefined) {
        alert("Undifined!")
      } else {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.tokenStorage.saveUser(data.user_id);
        this.tokenStorage.saveReserved(0);

        this.roles = this.tokenStorage.getAuthorities();
      }

      this.router.navigate(['']).then(() => {
        window.location.reload();
      });
      

    })
  }

}
