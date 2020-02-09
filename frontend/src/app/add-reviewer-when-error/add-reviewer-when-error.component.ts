import { ReviewService } from './../services/review-service/review.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AddReviewerDto } from './../model/add-reviewer-dto';
import { EditorReviewer } from './../model/editor-reviewer-dto';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-reviewer-when-error',
  templateUrl: './add-reviewer-when-error.component.html',
  styleUrls: ['./add-reviewer-when-error.component.css']
})
export class AddReviewerWhenErrorComponent implements OnInit {

  private taskId: string;
  private processInstanceId: string;

  

  private scFilter: boolean = false;
  private geoFilter: boolean = false;

  private possibleReviewers: EditorReviewer[];
  private filteredReviewers: EditorReviewer[] = [];
  private selectedReviewers: EditorReviewer[] = [];

  private reviewerDto: AddReviewerDto;

  constructor(private activatedRoute: ActivatedRoute, 
    private reviewService: ReviewService, 
    private router: Router) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(data => {
      const taskId = data.get("taskId");
      this.taskId = taskId;
      
      let x = this.reviewService.startAddingReviewerWhenError(taskId);

      x.subscribe(
        res => {
          console.log(res);

          this.reviewerDto = res;
         
          this.possibleReviewers = res.editorsReviewersDto;
          this.filteredReviewers = res.editorsReviewersDto;
        },
        err => {
          console.log("Error occured");
        }
      );
    });
  }

  add(rev: EditorReviewer){
    this.selectedReviewers.push(rev);
  }

  delete(rev: EditorReviewer){
    let index = this.selectedReviewers.indexOf(rev);
    this.selectedReviewers.splice(index, 1);
  }

  addReviewer(){
    if(this.selectedReviewers.length > 1){
      alert('Please select only one');
      return;
    }

    this.reviewerDto.editorsReviewersDto = this.selectedReviewers;

    // let x = this.reviewService.postAddingReviewerWhenError(this.taskId, this.reviewerDto);
    let x;

    if(this.reviewerDto.insideMf){
       x = this.reviewService.postAddingReviewerWhenError(this.taskId, this.reviewerDto);
    }else{
       x = this.reviewService.postAddingReviewerAdditional(this.taskId, this.reviewerDto);
    }

    x.subscribe(res => {
      console.log('resi');
      alert('Operations of adding reviewer is completed.');
      this.router.navigate(['']);
    }, err => {

    });
  }

  filter(){
    if(this.scFilter){
      this.filteredReviewers = this.filteredReviewers.filter(r => r.scienceArea.scienceAreaId === this.reviewerDto.articleDto.scienceArea.scienceAreaId);
    }

    if(!this.scFilter && !this.geoFilter){
      this.filteredReviewers = this.possibleReviewers;
    }
  }

  

}
