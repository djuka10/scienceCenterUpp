import { ActivatedRoute } from '@angular/router';
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
  userEditor: Boolean = false;
  userAdmin: Boolean = false;
  authorities: String[];

  logged: Boolean = false;

  constructor(private tokenStorage: TokenStorageService,
    private route: ActivatedRoute) {
    this.authorities = tokenStorage.getAuthorities();
    if(this.authorities.includes("ROLE_EDITOR") ||
    this.authorities.includes("ROLE_REVIEWER")) {
      this.userEditor = true;
      this.logged = true;
    } else {
      //alert("FALSE");
      this.userEditor = false;
    }

    if(this.authorities.includes("ROLE_ADMIN")) {
      this.userAdmin = true;
      this.logged = true;
    } else {
      this.userAdmin = false;
    }

    if(this.authorities.includes("ROLE_USER")) {
      this.userLogged = true;
      this.logged = true;
    } else {
      this.userLogged = false;
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
    window.location.reload();
  }
}
