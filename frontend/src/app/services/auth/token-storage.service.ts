import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';
const USER_KEY = 'AuthUser;';
const RESERVED = 'Reserved;';
const PROCESS_INSTANCE_ID = 'ProcessInstanceId';
const TASK_ID = 'TaskId';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private roles: Array<string> = [];
  constructor() { }

  public signOut() {
    let temp = this.getTaskId();
    window.sessionStorage.clear();
    this.saveTaskIdWithSignOut(temp);

  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUsername(username: string) {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }

  public getUsername(): string {
    return sessionStorage.getItem(USERNAME_KEY);
  }

  public saveAuthorities(authorities: string[]) {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    this.roles = [];

    if (sessionStorage.getItem(TOKEN_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        this.roles.push(authority.authority);
      });
    }

    return this.roles;
  }

  public saveUser(user: number) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() : string {
    return sessionStorage.getItem(USER_KEY);
  }

  public saveReserved(num: number) {
    window.sessionStorage.removeItem(RESERVED);
    window.sessionStorage.setItem(RESERVED, JSON.stringify(num));
  }

  public getReserved() : string {
    return sessionStorage.getItem(RESERVED);
  }

  public saveProcessInstanceId(process_instance_id : string) {
    window.sessionStorage.removeItem(PROCESS_INSTANCE_ID);
    window.sessionStorage.setItem(PROCESS_INSTANCE_ID, JSON.stringify(process_instance_id));
  }

  public getProccessInstanceId() : string {
    return sessionStorage.getItem(PROCESS_INSTANCE_ID);
  }

  public saveTaskId(task_id : string) {
    window.sessionStorage.removeItem(TASK_ID);
    window.sessionStorage.setItem(TASK_ID, JSON.stringify(task_id));
  }

  public saveTaskIdWithSignOut(task_id : string) {
    window.sessionStorage.removeItem(TASK_ID);
    window.sessionStorage.setItem(TASK_ID, task_id);
  }

  public getTaskId() : string {
    return sessionStorage.getItem(TASK_ID);
  }

}
