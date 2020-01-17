import { TokenStorageService } from './../services/auth/token-storage.service';
import { Globals } from './../guard/globals';
import { Router, ActivatedRoute } from '@angular/router';
import { RepositoryService } from './../services/repository/repository.service';
import { UserService } from './../services/users/user.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-activateuser',
  templateUrl: './activateuser.component.html',
  styleUrls: ['./activateuser.component.css']
})
export class ActivateuserComponent implements OnInit {

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
  private proccesInstanceTemp = "";

  private firstAttemp: boolean;

  lista: Array<any>;
  checkbox: Boolean;

  constructor(private userService : UserService, 
    private repositoryService : RepositoryService, 
    private router: Router,
    private route: ActivatedRoute,
    private globals: Globals,
    private tokenStorage: TokenStorageService) { 

      this.proccesInstanceTemp = this.route.snapshot.params.id;

      let x = this.repositoryService.getTasks(this.proccesInstanceTemp).subscribe(data => {
        this.lista = data;
        for(let l of this.lista) {
          this.globals.globalTaskId = l.taskId;
        }
       //testirati sutra
        //sutra....
        let o = new Array();  

        this.userService.registerUser(o,this.globals.globalTaskId).subscribe(data => {
          //submitovao
          alert("Aktivirao korisnika");

          let x = this.repositoryService.getTasks(this.proccesInstanceTemp).subscribe(data => {
            this.lista = data;
            for(let l of this.lista) {
              this.globals.globalTaskId = l.taskId;
            }
            alert("TaskId: " + this.globals.globalTaskId);
            this.tokenStorage.saveTaskId(this.globals.globalTaskId);
           // this.router.navigate(['/submitReviewer/' + this.globals.globalTaskId]);

          })

          
         

        })


      })
  }

  ngOnInit() {
  }

}
