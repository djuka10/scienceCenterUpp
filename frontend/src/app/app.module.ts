import { UpdateArticleChanges } from './model/update-article-changes-dto';
import { ViewMagazineComponent } from './view-magazine/view-magazine.component';
import { ViewMagazinesComponent } from './view-magazines/view-magazines.component';
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
import { AddTextComponent } from './add-text/add-text.component';
import { TasksComponent } from './tasks/tasks.component';
import { AnalizeTextComponent } from './analize-text/analize-text.component';
import { AnalizePdfComponent } from './analize-pdf/analize-pdf.component';
import { DefineTimeForReviewingComponent } from './define-time-for-reviewing/define-time-for-reviewing.component';
import { AddReviewerComponent } from './add-reviewer/add-reviewer.component';
import { UpdateArticleComponent } from './update-article/update-article.component';
import { ReviewArticleComponent } from './review-article/review-article.component';
import { ReviewArticleByEditorComponent } from './review-article-by-editor/review-article-by-editor.component';
import { UpdateChangesComponent } from './update-changes/update-changes.component';
import { AddReviewerWhenErrorComponent } from './add-reviewer-when-error/add-reviewer-when-error.component';

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
  },
  {
    path: "view-magazines",
    component: ViewMagazinesComponent,
    canActivate: [Notauthorized]
  },
  {
    path: 'view-magazine/:id', component: ViewMagazineComponent,
  },
  {
    path: 'add-text/:id', component: AddTextComponent,
  },
  {
    path: 'tasks', 
    component: TasksComponent, 
    canActivate: [Authorized]
  },
  {
    path: 'analize-text/:taskId', component: AnalizeTextComponent,
  },
  {
    path: 'analize-pdf/:taskId', component: AnalizePdfComponent,
  },
  {
    path: 'define-time-for-reviewing/:taskId', component: DefineTimeForReviewingComponent
  },
  {
    path: 'add-review/:taskId', component: AddReviewerComponent
  },
  {
    path: 'update-article/:taskId', component: UpdateArticleComponent,
  },
  {
    path: 'review-article/:taskId', component: ReviewArticleComponent,
  },
  {
    path: 'review-article-by-editor/:taskId', component: ReviewArticleByEditorComponent
  },
  {
    path: 'update-article-changes/:taskId', component: UpdateChangesComponent,
  },
  {
    path: 'add-review-when-error/:taskId', component: AddReviewerWhenErrorComponent,
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
    AdminComponent,
    ViewMagazinesComponent,
    ViewMagazineComponent,
    AddTextComponent,
    TasksComponent,
    AnalizeTextComponent,
    AnalizePdfComponent,
    DefineTimeForReviewingComponent,
    AddReviewerComponent,
    UpdateArticleComponent,
    ReviewArticleComponent,
    ReviewArticleByEditorComponent,
    UpdateChangesComponent,
    AddReviewerWhenErrorComponent
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
