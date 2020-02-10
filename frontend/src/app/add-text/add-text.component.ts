import { TokenStorageService } from './../services/auth/token-storage.service';
import { Token } from './../guard/Token';
import { Term } from './../model/term';
import { NewArticle } from './../model/new-article';
import { AddTextService } from './../services/add-text/add-text.service';
import { MagazineService } from './../services/magazine/magazine.service';

import { Globals } from './../guard/globals';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from './../services/repository/repository.service';
import { UserService } from './../services/users/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from '../guard/user';

@Component({
  selector: 'app-add-text',
  templateUrl: './add-text.component.html',
  styleUrls: ['./add-text.component.css']
})
export class AddTextComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  private taskId: string;
  private processInstanceId: string;

  globals: Globals;

  lista: Array<any>;

  private articleFormDto = null;


  private articleData: NewArticle = new NewArticle();
  private customTerm: Term = new Term();
  private customUser: User = new User();

  private activeForm: string = 'custom';

  constructor(private userService : UserService, 
    private repositoryService : RepositoryService, 
    private router: Router,
    private route: ActivatedRoute,
    globals: Globals,
    private magazineService: MagazineService,
    private addTextService: AddTextService,
    private tokenStorage: TokenStorageService) {

    }
    

  ngOnInit() {
    alert("Usao u proces obrade teksta");
    let x = this.addTextService.startProcess(this.route.snapshot.params.id,this.tokenStorage.getUser());

    x.subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.taskId = res.taskId;
        this.processInstanceId = res.processInstanceId;
      /*  console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });/*
/*
        this.addTextService.getTasks(this.processInstance).subscribe( data => {


          this.lista = data;
          for(let l of this.lista) {
            this.globals.globalTaskId = l.taskId;
          }

          alert("Ress: "+ this.globals.globalTaskId);
          this.addTextService.getUserTask(this.globals.globalTaskId).subscribe( data2 => {

            console.log(data2);
            //this.categories = res;
            this.formFieldsDto = data2;
            this.formFields = data2.formFields;
            this.processInstance = data2.processInstanceId;
            this.formFields.forEach( (field) =>{
              
              if( field.type.name=='enum'){
                this.enumValues = Object.keys(field.type.values);
              }
          })

        })


      },
      err => {
        console.log("Error occured");
      }
    );
*/
    })
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
    // let  file: File;
    // const imageWrapper: DisplayImageWrapper = new DisplayImageWrapper();
    // for (const f of event.target.files) {
    //   let  file: File;

    //   // provera tipa na ulazu
    //   const type: string = f.type;
    //   const contains: boolean = type.includes('image');
    //   if (!contains) {
    //     this.toastrService.warning('Dozvoljen je unos samo slike');
    //     return;
    //   }
    //   const imageWrapper: DisplayImageWrapper = new DisplayImageWrapper();

    //   file = f;
    //   const reader = new FileReader();

    //   // ucitavanje fajla preko readera, pregled fajla -> filter formata
    //   reader.onload = () => {

    //     imageWrapper.putanja = file.name;
    //     imageWrapper.value = reader.result;
    //     this.selectedImages.push(imageWrapper);
    //   };
    //   // ovo ispod trigerije ovo iznad
    //   reader.readAsDataURL(file);   // rezultat na kraju je 64bitni enkodirana slika
    // }

    let file: File = event.target.files[0];
    const reader = new FileReader();

      // ucitavanje fajla preko readera, pregled fajla -> filter formata
      reader.onload = () => {

        console.log(file.name);
        console.log(reader);
        console.log(reader.result);
        this.articleData.file = reader.result;
        
      };
      // ovo ispod trigerije ovo iznad
      // https://developer.mozilla.org/en-US/docs/Web/API/FileReader
      // reader.readAsArrayBuffer(file);
      // reader.readAsBinaryString(file);
      // reader.readAsText(file);
      reader.readAsDataURL(file);   // rezultat na kraju je 64bitni enkodirana string
      
    console.log(event);

    
  }

  valid(value): boolean {

    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
       if(value[property] == "") {
          return false;
       }
     }
    return true;
    
  }

  onSubmit(value, form){
    console.log(this.articleData);
    console.log(this.taskId);

    var p = this.valid(value);
     alert("P : " + p);
     if(p == false) {
      alert("Unesite sva polja !!!"); 
     } else {
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
  
      let x = this.addTextService.postNewArticle(o, this.taskId);
  
      x.subscribe(res => {
        console.log(res);
  
       // this.toastrService.success('New article published! You will be informed for the further instructions.');
        this.router.navigate(['']);
      }, err => {
  
      });
     }


    
  }

  

}
