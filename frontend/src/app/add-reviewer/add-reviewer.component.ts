import { EditorReviewer } from './../model/editor-reviewer-dto';
import { ReviewService } from './../services/review-service/review.service';
import { TokenStorageService } from './../services/auth/token-storage.service';
import { AddTextService } from './../services/add-text/add-text.service';
import { MagazineService } from './../services/magazine/magazine.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from './../services/repository/repository.service';
import { UserService } from './../services/users/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-reviewer',
  templateUrl: './add-reviewer.component.html',
  styleUrls: ['./add-reviewer.component.css']
})
export class AddReviewerComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  private taskId: string;
  private processInstanceId: string;

  private selectedReviewers: EditorReviewer[] = [];



  constructor(private userService : UserService, 
    private repositoryService : RepositoryService, 
    private router: Router,
    private route: ActivatedRoute,
    private magazineService: MagazineService,
    private addTextService: AddTextService,
    private tokenStorage: TokenStorageService,
    private reviewService: ReviewService) { }

  ngOnInit() {

    let x = this.reviewService.startAddingReviewers(this.route.snapshot.params.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.processInstanceId = res.processInstanceId;
      })
  }

 

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    

    let reviewrs: EditorReviewer[] = form.controls['recenzent'].value;
    
    if(reviewrs.length < 2){
      alert('Please select at least 2 reviewers');
      return;
    }

    console.log(reviewrs);

    let x = this.reviewService.postAddingReviewers(this.taskId,reviewrs);

    x.subscribe(res => {
      console.log('resi');

      alert('Operations of adding reviewer is completed.');
      this.router.navigate(['']);
    }, err => {

    });



  }

}
