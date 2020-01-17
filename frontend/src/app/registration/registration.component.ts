import { Globals } from './../guard/globals';
import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';
import {RepositoryService} from '../services/repository/repository.service';
import { Router, ActivatedRoute } from '@angular/router';
import { validateConfig } from '@angular/router/src/config';
import { empty } from 'rxjs';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  providers: [Globals]
})
export class RegistrationComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  globals: Globals;

  constructor(private userService : UserService, 
    private repositoryService : RepositoryService, 
    private router: Router,
    private route: ActivatedRoute,
    globals: Globals) {

    this.globals = globals;
    alert("URL: " + this.route.snapshot.url.join('/'));
    let x = repositoryService.startProcess("Proces_registracije");

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
   }

  ngOnInit() {
  }

  validdd: Boolean = false;


  valid(value): boolean {

    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
     if(property == "titula" || property == "recenzent") {
       continue;
     } else {
       if(value[property] == "") {
         //alert("Usao u null");
          return false;
       }
     }
    }

    return true;
    
  }

  onSubmit(value, form){

    var p = this.valid(value);
   // alert("P : " + p);
    if(p == false ) {
     alert("Unesite sva polja koja su obavezna"); 
    } else {

      let o = new Array();
      for (var property in value) {
        console.log(property);
        console.log(value[property]);
        o.push({fieldId : property, fieldValue : value[property]});
      }

      console.log(o);
      let x = this.userService.registerUser(o, this.formFieldsDto.taskId);

      x.subscribe(
        res => {
          console.log(res);
          
          alert("Prosao unos osnovnih");

          //this.router.navigate(['/registrate2']);
          this.repositoryService.getTasks(this.processInstance).subscribe( data => {

              this.lista = data;
            for(let l of this.lista) {
              this.globals.globalTaskId = l.taskId;
            }

            alert("Ress: "+ this.globals.globalTaskId);
            this.router.navigate(['/registrate/sciencearea/' + this.globals.globalTaskId]);

          /*  this.repositoryService.completeTask(this.formFieldsDto.taskId).subscribe( data2 => {
              alert("Prosao complete!");
              this.router.navigate(['/registrate/sciencearea/' + this.globals.globalTaskId]);
            })*/

          })
          


        },
        err => {
          console.log("Error occured");
        }
      );
    }

    
  }

  lista: Array<any>;

  getTasks(){
    let x = this.repositoryService.getTasks(this.processInstance);

    x.subscribe(
      res => {
        console.log(res);
        this.tasks = res;
        this.lista = res;
        for(let l of this.lista) {
          this.globals.globalTaskId = l.taskId;
        }

        alert("Ress: "+ this.globals.globalTaskId);
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

        this.router.navigate(['/registrate/sciencearea/' + this.globals.globalTaskId]);


      },
      err => {
        console.log("Error occured");
      }
    );
   }

   

}
