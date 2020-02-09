import { RepositoryService } from './../services/repository/repository.service';
import { Article } from './../model/article';
import { AddTextService } from './../services/add-text/add-text.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-analize-text',
  templateUrl: './analize-text.component.html',
  styleUrls: ['./analize-text.component.css']
})
export class AnalizeTextComponent implements OnInit {

  private taskIdTopic: string;
  private taskIdText: string;
  private displayArticle;
  private isOk: boolean = false;
  private isTextOk: boolean = false;
  private comment: string = '';
  private article: Article;

  private fileUrl;

  private proba: string | ArrayBuffer;

  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";

  constructor(private activatedRoute: ActivatedRoute, 
    private articleService: AddTextService, 
    private sanitizer: DomSanitizer,
    private router: Router,
    private repoService: RepositoryService) { }

  ngOnInit() {
    // this.activeForm == 'custom';
    this.activatedRoute.paramMap.subscribe(data => {
      const taskId = data.get("taskId");
      this.taskIdTopic = taskId;

      //alert("TAsk id: " + this.taskIdTopic);

      this.articleService.preanalizeArticle(taskId).subscribe(data => {
        console.log(data);

        this.displayArticle = data;

            let y = this.repoService.getUserTask(this.taskIdTopic)
            
            y.subscribe(res2 => {
              console.log(res2);
              //this.categories = res;
              this.formFieldsDto = res2;
              this.formFields = res2.formFields;
              this.processInstance = res2.processInstanceId;
            })


        

      })
      
    });
  }

 
  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if(value[property] != true) {
        alert("USAO 1: " + false);
        o.push({fieldId : property, fieldValue : false});
      } else {
        alert("USAO 2:" + true);
        o.push({fieldId : property, fieldValue : true});
      }
    }

    console.log(o);

    let x = this.articleService.analizeBasicResult(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.router.navigate(['']);
      
      })
  }

}
