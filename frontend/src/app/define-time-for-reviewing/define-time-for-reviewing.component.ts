import { RepositoryService } from './../services/repository/repository.service';
import { AddTextService } from './../services/add-text/add-text.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-define-time-for-reviewing',
  templateUrl: './define-time-for-reviewing.component.html',
  styleUrls: ['./define-time-for-reviewing.component.css']
})
export class DefineTimeForReviewingComponent implements OnInit {

  private taskIdTopic: string;
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";

  constructor(private activatedRoute: ActivatedRoute, 
    private articleService: AddTextService, 
    private router: Router,
    private repoService: RepositoryService) { }

  ngOnInit() {

    this.activatedRoute.paramMap.subscribe(data => {
      const taskId = data.get("taskId");
      this.taskIdTopic = taskId;


      let y = this.repoService.getUserTask(this.taskIdTopic)
            
      y.subscribe(res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;

      })

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

    this.articleService.postTimeForReviewing(o,this.taskIdTopic).subscribe( data => {
      alert("SVE OK");
    })


  }

}
