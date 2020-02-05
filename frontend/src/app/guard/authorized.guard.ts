import { TokenStorageService } from './../services/auth/token-storage.service';
import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Authorized implements CanActivate {

  constructor(private tokeStorage: TokenStorageService) {}

  canActivate() {
    if(this.tokeStorage.isLogged()){
      return true;
    }else{
      alert('You cannot access to requested path');
      return false;
    }
  }
}