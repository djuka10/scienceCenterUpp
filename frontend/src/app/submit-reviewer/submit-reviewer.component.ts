import { Globals } from './../guard/globals';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from './../services/repository/repository.service';
import { UserService } from './../services/users/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-submit-reviewer',
  templateUrl: './submit-reviewer.component.html',
  styleUrls: ['./submit-reviewer.component.css']
})
export class SubmitReviewerComponent implements OnInit {


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

      alert("ID: " + this.route.snapshot.params.id );
      this.globalTaskId = this.route.snapshot.params.id;

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

    }

  ngOnInit() {
  }

  lista: Array<any>;

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

    let x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    x.subscribe (
      res => {
        console.log(res);
        
        alert("Admin potvrdio recenzenta");
      }
    )






  }

}
