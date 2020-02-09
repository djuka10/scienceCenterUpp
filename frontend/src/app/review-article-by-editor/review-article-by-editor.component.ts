import { Opinion } from './../model/opinion';
import { Article } from './../model/article';
import { AddTextService } from './../services/add-text/add-text.service';
import { ReviewService } from './../services/review-service/review.service';
import { DomSanitizer } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from './../services/repository/repository.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-review-article-by-editor',
  templateUrl: './review-article-by-editor.component.html',
  styleUrls: ['./review-article-by-editor.component.css']
})
export class ReviewArticleByEditorComponent implements OnInit {


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
  private enumValues = [];

  private listOpionions = [];

  constructor(private activatedRoute: ActivatedRoute, 
    private articleService: AddTextService, 
    private reviewService: ReviewService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private repoService: RepositoryService) { }

  ngOnInit() {

    this.activatedRoute.paramMap.subscribe(data => {
      const taskId = data.get("taskId");
      this.taskIdTopic = taskId;
      alert("Usao u start");

      //ovde vracamo informacije o tekstu(artiklu)
      let x = this.reviewService.startReviewArticleByEditor(taskId);

      x.subscribe(data => {
        console.log('resi');

        this.displayArticle = data.article;
        this.listOpionions = data.opinions;

        console.log(this.displayArticle);

        let y = this.repoService.getUserTask(this.taskIdTopic)


        y.subscribe(res2 => {
          console.log(res2);
          //this.categories = res;
          this.formFieldsDto = res2;
          this.formFields = res2.formFields;
          this.processInstance = res2.processInstanceId;
          this.formFields.forEach( (field) =>{
          
            if( field.type.name=='enum'){
              this.enumValues = Object.keys(field.type.values);
            }
          });
        })

      })
    })


  }

  download() {

    const linkSource = 'data:application/pdf;base64,' + this.displayArticle.file;
    const downloadLink = document.createElement("a");
    const fileName = "sample.pdf";
    //testirati
    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();

  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
       
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    let x = this.reviewService.postReviewByEditor(o,  this.taskIdTopic);

    x.subscribe( res => {
      console.log('resi');
      alert('Uspesno obavljeno recenziranje od strane urednika');
      this.router.navigate(['']);
    }, err => {

    })
  }

}
