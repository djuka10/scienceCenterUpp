import { Globals } from './../guard/globals';
import { RepositoryService } from './../services/repository/repository.service';
import { UserService } from './../services/users/user.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sciencearea',
  templateUrl: './sciencearea.component.html',
  styleUrls: ['./sciencearea.component.css']
})
export class ScienceareaComponent implements OnInit {

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

  constructor(private userService : UserService, 
    private repositoryService : RepositoryService, 
    private router: Router,
    private globals: Globals,
    private route: ActivatedRoute) {
      this.firstAttemp = globals.firstAttemp;
      this.globalTaskId = this.route.snapshot.params.id;
      
      
      alert("KONSTRUKTOVAO GA");
      alert("? " + this.globalTaskId);
      //Otkomentarisati kasnije
      //window.location.reload();
     // alert("FirstAtt: " + this.globals.firstAttemp);

      //idemo u getUserTask samo ako prvi put pokrecemo proces
   //   if(this.globals.firstAttemp) {
          this.globals.firstAttemp = false;
          let x = this.repositoryService.getUserTask(this.globalTaskId);

            x.subscribe(
              res => {
                console.log(res);
                //this.categories = res;
                this.formFieldsDto = res;
                this.formFields = res.formFields;
                this.processInstance = res.processInstanceId;
                this.formFields.forEach( (field) =>{
                  
                  if( field.type.name=='enum'){
                    this.enumValues = Object.keys(field.type.values);
                  }
                });
              },
              err => {
                console.log("Error occured");
              }
        );
  //    } else {
   //     console.log("Nije prvi put :(");
   //   }
      


   }

  ngOnInit() {
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if(property == "daLiSteZavrsili") {
        if(value[property] == undefined) {
          o.push({fieldId : property, fieldValue : false});
        }else {
          o.push({fieldId : property, fieldValue : true});
        }
        
      } else {
          o.push({fieldId : property, fieldValue : value[property]});
      }
     
    }
   // o.push({fieldId : "naucnaOblast", fieldValue: this.enumOption});

    console.log(o);
    let x = this.userService.scienceArea(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert("Prosao unos no");

        //this.router.navigate(['/registrate/sciencearea/' + this.globals.globalTaskId]);

        //ovde mozda neki if getTask
        if(this.checkboxFlag) {
          
        } else {

        }

        //this.router.navigate(['/registrate2']);
        this.repositoryService.getTasks(this.processInstance).subscribe( data => {

            this.lista = data;
          for(let l of this.lista) {
            this.globals.globalTaskId = l.taskId;
          }

          alert("Ress: "+ this.globals.globalTaskId);

          for(let f of this.formFields) {
            if(f.type.name=='boolean') {
              alert("Flag: " + this.checkboxFlag);

            }
          }

          if(this.checkboxFlag == undefined) {
            this.checkboxFlag = false;
            alert("Vise nije undefined? : " + this.checkboxFlag);
          }

          let pom = this.route.snapshot.url.join('/');
          alert("POM: " + pom);
          console.log(pom);


          //ovde treba napraviti if logiku za razlikovanje registracije od dodavanja magazina
          if(!this.checkboxFlag) {
            alert("FALSE");
            if(pom.includes('magazine')) {
              this.router.navigate(['/add/magazine/sciencearea/' + this.globals.globalTaskId]);
              alert("Magazine");
              //window.location.reload();
            } else {
              this.router.navigate(['/registrate/sciencearea/' + this.globals.globalTaskId]);
              alert("Registrate");
              //window.location.reload();
            }
             
            //  window.location.reload();
          } else {
            alert("TRUE");
            if(pom.includes('magazine')) { 
              this.router.navigate(['/add/magazine/addEditorReviewer/' + this.globals.globalTaskId]);
              //alert("Magazine");
              //window.location.reload();
            } else {
              alert("Cekam potvrdu na mejlu!")
            }
              
            //this.router.navigate(['/verification/' + this.processInstance]);
          }

          


        })

        


      },
      err => {
        console.log("Error occured");
      }
    );
  }

  

  getTasks(){
    let x = this.repositoryService.getTasks(this.processInstance);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;

      },
      err => {
        console.log("Error occured");
      }
    );
   }

   claim(taskId){
    let x = this.repositoryService.claimTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
      },
      err => {
        console.log("Error occured");
      }
    );
   }

   lista: Array<any>;
   checkbox: Boolean;

   complete(taskId){
    let x = this.repositoryService.completeTask(taskId);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;

        
        this.lista = res;
        for(let l of this.lista) {
          this.globals.globalTaskId = l.taskId;
        }

        alert("Ress: "+ this.globals.globalTaskId);
        //if..
        for(let f of this.formFields) {
          alert("Uso u ff")
          if(f == "daLiSteZavrsili") {
            this.checkbox = f.id;
          }
        }
        alert("F: " + this.checkbox);

        this.router.navigate(['/registrate/sciencearea/' + this.globals.globalTaskId]);

      },
      err => {
        console.log("Error occured");
      }
    );
   }
}
