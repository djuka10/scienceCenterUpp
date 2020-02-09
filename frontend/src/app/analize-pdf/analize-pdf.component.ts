import { EncodeDecode } from './../guard/base64';
import { RepositoryService } from './../services/repository/repository.service';
import { DomSanitizer } from '@angular/platform-browser';
import { AddTextService } from './../services/add-text/add-text.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from './../model/article';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-analize-pdf',
  templateUrl: './analize-pdf.component.html',
  styleUrls: ['./analize-pdf.component.css']
})
export class AnalizePdfComponent implements OnInit {

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

      this.articleService.analizeArticle(taskId).subscribe(data => {
        console.log(data);

        this.displayArticle = data;

        this.article = this.displayArticle;
        console.log("NEsto drugooo, ti si za mene stvar " + this.article.file.toString())
            let y = this.repoService.getUserTask(this.taskIdTopic)
            
            y.subscribe(res2 => {
              console.log(res2);
              //this.categories = res;
              this.formFieldsDto = res2;
              this.formFields = res2.formFields;
              this.processInstance = res2.processInstanceId;

              //za fajl
              //this.article = res;
              console.log(this.article.file.toString());
              this.taskIdText = res2.taskId;
              const data = 'some text';
              // const blob = new Blob([this.article.file.toString()], { type: 'application/octet-stream' });
              
           /*   let t1 = EncodeDecode.b64DecodeUnicode(this.article.file.toString());
              const blob1 = new Blob([t1], { type: 'application/octet-stream' });
              this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob1));*/
        
              //let t2 = EncodeDecode.b64Decode(this.article.file);
              //let t2 = this.article.file;
              // let t2 = atob(this.article.file.toString());
               //let t2 = EncodeDecode.b64EncodeUnicode(this.article.file.toString());
              // const blob2 = new Blob([t2], { type: 'application/pdf' });
              // this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob2));
        
              // this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blob));
              // this.fileUrl = window.URL.createObjectURL(blob);
        
              let file: Blob = new Blob([this.article.file.toString()], {type: 'application/pdf'});
             // this.fileUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(file));
              let fileURL = URL.createObjectURL(file);
              this.fileUrl = fileURL;
/*
              window.open(fileURL); 
              var a         = document.createElement('a');
              a.href        = fileURL; 
              a.target      = '_blank';
              a.download    = 'bill.pdf';
              document.body.appendChild(a);
              a.click();*/

              const linkSource = 'data:application/pdf;base64,' + this.article.file;
              const downloadLink = document.createElement("a");
              const fileName = "sample.pdf";
              //testirati
             // downloadLink.href = linkSource;
             // downloadLink.download = fileName;
             // downloadLink.click();

            })
     

      })
      
    });

  }

  download() {

    const linkSource = 'data:application/pdf;base64,' + this.article.file;
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
      if(value[property] != true) {
        alert("USAO 1");
        o.push({fieldId : property, fieldValue : false});
      } else {
        alert("USAO 2");
        o.push({fieldId : property, fieldValue : true});
      }
       
      //o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    let x = this.articleService.analizeBasicResult(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        
        alert("Prosao analizu");
        this.router.navigate(['']);
      })
  }

}
