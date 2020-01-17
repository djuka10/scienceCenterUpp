import { TokenStorageService } from './../services/auth/token-storage.service';
import { Globals } from './../guard/globals';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from './../services/repository/repository.service';
import { RepositoryComponent } from './../repository/repository.component';
import { MagazineService } from './../services/magazine/magazine.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-editor-reviewer',
  templateUrl: './add-editor-reviewer.component.html',
  styleUrls: ['./add-editor-reviewer.component.css']
})
export class AddEditorReviewerComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  private checkboxFlag : Boolean;
  private enumOption;

  private globalTaskId: string;

  private firstAttemp: boolean;

  private enumValues2 = [];
  private enumValues3 = [];
  private enumValues4 = [];

  field1;
  field2;
  field3;
  field4;


  constructor(private magazineService : MagazineService, 
    private repositoryService : RepositoryService, 
    private router: Router,
    private globals: Globals,
    private route: ActivatedRoute,
    private tokenService: TokenStorageService) { 
      this.globalTaskId = this.route.snapshot.params.id;


      let x = this.repositoryService.getUserTask(this.globalTaskId);

      x.subscribe(
        res => {
          console.log(res);
          //this.categories = res;
          this.formFieldsDto = res;
          this.formFields = res.formFields;
          this.processInstance = res.processInstanceId;
          let i = 0;
          this.formFields.forEach( (field) =>{
            if( field.type.name=='enum'){
              if(i == 0) {
                this.enumValues = Object.keys(field.type.values);
                i++;
              } else if(i == 1) {
                this.enumValues2 = Object.keys(field.type.values);
                i++;
              } else if(i == 2) {
                this.enumValues3 = Object.keys(field.type.values);
                i++;
              } else {
                this.enumValues4 = Object.keys(field.type.values);
                i++;
              }
              
            }
          });
        },
        err => {
          console.log("Error occured");
        }
  );

    }

  ngOnInit() {
  }
  lista: Array<any>;
  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      
      o.push({fieldId : property, fieldValue : value[property]});
    }

    console.log(o);

    let p = new Array();
    p.push({fieldId: "dodavanjeUrednika1", fieldValue: this.field1})
    p.push({fieldId: "dodavanjeUrednika2", fieldValue: this.field2})
    p.push({fieldId: "dodajRecenzenta1", fieldValue: this.field3})
    p.push({fieldId: "dodajRecenzenta2", fieldValue: this.field4})


    console.log(p);
    for(let e in this.enumValues) {

    }


    let x = this.magazineService.addEditorReviewer(p, this.formFieldsDto.taskId);


    x.subscribe(
      res => {
        console.log(res);

        alert("Prosao unos urednika i recenzenta");
//ovde baguje nesto sve mu jebem, nmg vise

      this.repositoryService.getTasks(this.processInstance).subscribe( data => { 
          this.lista = data;
          for(let l of this.lista) {
            this.tokenService.saveTaskId(l.taskId);
            this.globals.globalTaskId = l.taskId;
          }

         // this.router.navigate(['/']);
        //  window.location.reload();
        //  this.router.navigate(['/admin/confirmMagazine/' + this.globals.globalTaskId]);
      })
        
/*
        this.globals.globalProcessInstance = this.processInstance;
        this.repositoryService.getTasks(this.processInstance).subscribe( data => {
          
        this.router.navigate(['/admin/confirmMagazine/' + this.globals.globalTaskId]);*/
          //bice redirektovanje na admin page za potvrdu magazina!!
          //to je to za danas, valjda cu sutra zavrsiti 
        
       /* })*/

      }
    )

  }

}
