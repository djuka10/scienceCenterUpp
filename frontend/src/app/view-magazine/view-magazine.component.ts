import { Magazine } from './../model/magazine';


import { TokenStorageService } from './../services/auth/token-storage.service';
import { MagazineService } from './../services/magazine/magazine.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-view-magazine',
  templateUrl: './view-magazine.component.html',
  styleUrls: ['./view-magazine.component.css']
})
export class ViewMagazineComponent implements OnInit {

  private magazine: Magazine;
  private isLogged: boolean;


  constructor(private activatedRoute: ActivatedRoute, private magazineService: MagazineService, private router: Router,
    private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(data => {
      const magazineId = data.get("id");
      
      let x = this.magazineService.getMagazine(magazineId);
      this.isLogged = this.tokenStorageService.isLogged();

      x.subscribe(
        res => {
          console.log(res);
          this.magazine = res;

        
        },
        err => {
          console.log("Error occured");
        }
      );
    });
  }

  newText(){
    this.router.navigate(['add-text', this.magazine.magazineId])
  }

  viewEditions(){

  }

  subscribe(){

  }

}
