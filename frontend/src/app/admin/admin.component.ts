import { TokenStorageService } from './../services/auth/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MagazineService } from './../services/magazine/magazine.service';
import { RepositoryService } from './../services/repository/repository.service';
import { UserService } from './../services/users/user.service';
import { Globals } from './../guard/globals';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

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
    private magazineService: MagazineService,
    private router: Router,
    private globals: Globals,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute) { 

      this.globalTaskId = this.route.snapshot.params.id;

     

      let x = this.repositoryService.getUserTask( this.globals.globalTaskId);


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


    
   lista: Array<any>;
   checkbox: Boolean;

  ngOnInit() {
  }

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      
      if(property == "aktivirajCasopis") {
        if(value[property] == undefined) {
          o.push({fieldId : property, fieldValue : false});
        }else {
          o.push({fieldId : property, fieldValue : true});
        }
        
      } else {
          o.push({fieldId : property, fieldValue : value[property]});
      }
    }

    console.log(o);

    let x = this.magazineService.adminConfirm(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);

        alert("Proslo aktiviranje magazina od strane admina");

        this.repositoryService.getTasks(this.processInstance).subscribe( data => { 

          this.lista = data;
          for(let l of this.lista) {
            this.globals.globalTaskId = l.taskId;
          }

          for(let f of this.formFields) {
            if(f.type.name=='boolean') {
              alert("Flag: " + this.checkboxFlag);

            }
          }


          //testirati slucaj kad ponovo mora da unosi
          if(!this.checkboxFlag) { 
            this.router.navigate(['/add/magazine/'+ this.globals.globalTaskId]);
          } else {
            alert("Kraj procesa");
          }


        })


      }
    )



  }

}
