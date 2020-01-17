import { TokenStorageService } from './../services/auth/token-storage.service';
import { MagazineService } from './../services/magazine/magazine.service';
import { UserService } from './../services/users/user.service';
import { RepositoryService } from './../services/repository/repository.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Globals } from './../guard/globals';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-magazine',
  templateUrl: './add-magazine.component.html',
  styleUrls: ['./add-magazine.component.css']
})
export class AddMagazineComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  globals: Globals;
  niz : Array<any>;
  userAuthority;
  authUsername;

  private globalTaskId: string;

  constructor(private userService : UserService, 
    private repositoryService : RepositoryService, 
    private magazineService: MagazineService,
    private router: Router,
    globals: Globals,
    private tokenStorage: TokenStorageService,
    private route: ActivatedRoute) { 
      this.globals = globals;

      this.niz = this.tokenStorage.getAuthorities();

      this.authUsername = this.tokenStorage.getUsername();

      for(let n of this.niz) {
        this.userAuthority = n;
      }
      //alert("Auth: " + this.userAuthority);

      this.globalTaskId = this.route.snapshot.params.id;
      let x;
      if(this.globalTaskId == "1") {
        alert("Start process");
        x = repositoryService.startProcess("Proces_Dodavanja_Casopisa");
      } else {
        alert("Admin rekao NE, i ponovo se pokrece proces");
        x = this.repositoryService.getUserTask(this.globalTaskId);
      }

      
    
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


  field1;

  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);

      

        o.push({fieldId : property, fieldValue : value[property]});

      

    }
  o.push({fieldId: "nacinNaplate", fieldValue: this.field1});
    o.push({fieldId: "mail", fieldValue: this.authUsername});

    console.log(o);
    let x = this.magazineService.addMagazine(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        console.log(res);
        
        alert("Prosao unos podataka o casopisu");

        //this.router.navigate(['/registrate2']);
        this.repositoryService.getTasks(this.processInstance).subscribe( data => {

            this.lista = data;
          for(let l of this.lista) {
            this.globals.globalTaskId = l.taskId;
          }

          alert("Ress: "+ this.globals.globalTaskId);
          this.router.navigate(['/add/magazine/sciencearea/' + this.globals.globalTaskId]);

        

        })
        


      },
      err => {
        console.log("Error occured");
      }
    );
  }

  lista: Array<any>;



}
