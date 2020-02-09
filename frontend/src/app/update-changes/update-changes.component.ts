import { UpdateArticleChanges } from './../model/update-article-changes-dto';
import { AddTextService } from './../services/add-text/add-text.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Term } from './../model/term';
import { NewArticle } from './../model/new-article';
import { Component, OnInit } from '@angular/core';
import { User } from '../guard/user';

@Component({
  selector: 'app-update-changes',
  templateUrl: './update-changes.component.html',
  styleUrls: ['./update-changes.component.css']
})
export class UpdateChangesComponent implements OnInit {

  private activateTab: string;

  private update: UpdateArticleChanges = null;
  private articleFormDto = null;
  private taskId: string;
  private processInstanceId: string;

  private articleData: NewArticle = new NewArticle();
  private customTerm: Term = new Term();
  private customUser: User = new User();

  private activeForm: string = 'custom';

  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private activatedRoute: ActivatedRoute, 
    private articleService: AddTextService, 
    private router: Router) { }


    ngOnInit() {
      // this.activeForm == 'custom';
      this.activatedRoute.paramMap.subscribe(data => {
        const taskId = data.get("taskId");
        
        let x = this.articleService.startUpdateChanges(taskId);
  
        x.subscribe(
          res => {
            this.update = res;
            console.log(res);
            this.formFieldsDto = res;
            this.formFields = res.formFields;
            this.taskId = res.taskId;
            this.processInstanceId = res.processInstanceId;
      
            this.articleData = res.newAarticleResponseDto;
  
            this.activateTab = 'updateForm';

            
  
          },
          err => {
            console.log("Error occured");
          }
        );
      });
    }

    add(term: Term){
      this.articleData.articleTerm.push(term);
    }
  
    delete(term: Term){
      let index = this.articleData.articleTerm.indexOf(term);
      this.articleData.articleTerm.splice(index, 1);
    }
  
    addCustom(term: Term){
      const t: Term = new Term();
      // t.termId = -1;
      t.termName = term.termName
      this.articleData.articleTerm.push(t);
      term.termName = '';
    }
  
    addCustomCouathor(value, form){
      
      this.articleData.articleCoAuthors.push(this.customUser);
      this.customUser = new User();
      form.submitted = false;
    }
  
    deleteCoauthor(user: User){
      let index = this.articleData.articleCoAuthors.indexOf(user);
      this.articleData.articleCoAuthors.splice(index, 1);
    }
  
    addExisting(user: User){
      this.articleData.articleCoAuthors.push(user);
    }
  
    onFileUpload(event) {
      
      let file: File = event.target.files[0];
      const reader = new FileReader();
  
        // ucitavanje fajla preko readera, pregled fajla -> filter formata
        reader.onload = () => {
  
          console.log(file.name);
          console.log(reader);
          console.log(reader.result);
          this.articleData.file = reader.result;
          
        };
        
        reader.readAsDataURL(file);   // rezultat na kraju je 64bitni enkodirana string
        
      console.log(event);
  
      
    }

    onSubmit(value, form){
      console.log(this.articleData);
      console.log(this.taskId);
  
      let o = new Array();
      for (var property in value) {
        console.log(property);
        console.log(value[property]);
        //if file, staviti ono iz 
        if(property == "file_choose") {
          o.push({fieldId : property, fieldValue : this.articleData.file});
          alert("FILE: " + this.articleData.file);
        } else
          o.push({fieldId : property, fieldValue : value[property]});
      }
  
      o.push({fieldId : "koautor", fieldValue : this.articleData.articleCoAuthors})
      o.push({fieldId : "kljucniPojmovi", fieldValue : this.articleData.articleTerm})
  
  
      let x = this.articleService.updateArticle(o, this.taskId);
  
      x.subscribe(res => {
        console.log(res);
        alert('New article published! You will be informed for the further instructions.');
        this.router.navigate(['']);
      }, err => {
  
      });
    }

}
