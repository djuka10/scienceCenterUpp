import { Router } from '@angular/router';
import { TokenStorageService } from './services/auth/token-storage.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  private user = JSON.parse(localStorage.getItem('user'));
  private role = localStorage.getItem('role');

  userLogged: Boolean = false;
  userLogged2: Boolean;
  userAdmin: Boolean = false;
  authorities: String[];

  constructor(private tokenStorage: TokenStorageService,
    private router: Router) {
    this.authorities = tokenStorage.getAuthorities();
    if(this.authorities.includes("ROLE_EDITOR")) {
      this.userLogged = true;
    } else {
      //alert("FALSE");
      this.userLogged = false;
    }

    if(this.authorities.includes("ROLE_ADMIN")) {
      this.userAdmin = true;
    } else {
      this.userAdmin = false;
    }

    if(this.tokenStorage.getUser() != null) {
      alert("TRU")
      this.userLogged2 = true;
    } else {
      alert("FAL")
      this.userLogged2 = false;
    }


  }
  

  loggedIn(){
    if(this.user){
      return true; 
    }else{
      return false;
    }
  }

  notLoggedIn(){
    if(!this.user){
      return true; 
    }else{
      return false;
    }
  }

  isAdmin(){
    if(this.role == "ADMIN"){
      return true; 
    }else{
      return false;
    }
  }

  logout() {
    this.tokenStorage.signOut();
    this.router.navigate(['/']);
    window.location.reload();
  }
}
