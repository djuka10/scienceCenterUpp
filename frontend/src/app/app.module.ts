import { Globals } from './guard/globals';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';

import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';

import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { ScienceareaComponent } from './sciencearea/sciencearea.component';
import { ActivateuserComponent } from './activateuser/activateuser.component';
import { SubmitReviewerComponent } from './submit-reviewer/submit-reviewer.component';
import { AddMagazineComponent } from './add-magazine/add-magazine.component';
import { LoginComponent } from './login/login.component';
import { AddEditorReviewerComponent } from './add-editor-reviewer/add-editor-reviewer.component';
import { AdminComponent } from './admin/admin.component';

const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  {
    path: "registrate",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "registrate/sciencearea/:id",
    component: ScienceareaComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "verification/:id",
    component: ActivateuserComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "submitReviewer/:id",  //ovde ce biti pristup samo adminu!
    component: SubmitReviewerComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "add/magazine/:id",
    component: AddMagazineComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "add/magazine/sciencearea/:id",
    component: ScienceareaComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "add/magazine/addEditorReviewer/:id",
    component: AddEditorReviewerComponent,
    canActivate: [Notauthorized]
  }/*,
  {
    path: "admin/confirmMagazine/:id",
    component: AdminComponent,
    canActivate: [Notauthorized]
  }*/,
  {
    path: "admin/confirmMagazine",
    component: AdminComponent,
    canActivate: [Notauthorized]
  }
  



]



@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    ScienceareaComponent,
    ActivateuserComponent,
    SubmitReviewerComponent,
    AddMagazineComponent,
    LoginComponent,
    AddEditorReviewerComponent,
    AdminComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule
  ],
  
  providers:  [
    Admin,
    Authorized,
    Notauthorized,
    Globals
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
