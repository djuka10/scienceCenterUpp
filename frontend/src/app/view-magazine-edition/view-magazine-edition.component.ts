import { NewCartItemRequest, BuyingType } from './../model/shoppingcart-new-item-request';
import { Cart } from './../model/shoppingcart';
import { TokenStorageService } from './../services/auth/token-storage.service';
import { TestService } from './../services/test/test.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-view-magazine-edition',
  templateUrl: './view-magazine-edition.component.html',
  styleUrls: ['./view-magazine-edition.component.css']
})
export class ViewMagazineEditionComponent implements OnInit {

  private edition;
  private articles;

  constructor(private activatedRoute: ActivatedRoute, private testService: TestService, 
    private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(data => {
      const magId = data.get('id');

      this.testService.getEditionById(+magId).subscribe(data => {
        this.edition = data;
      });

      this.testService.getAllArticles(+magId).subscribe(data => {
        this.articles = data;
      });
    });
  }

  addToCart(article){
    const l = this.tokenStorageService.hasCart();
    if(!l){
      this.testService.createCart(this.tokenStorageService.getUser()).subscribe(newCart => {
        this.tokenStorageService.setCart(newCart);
        const cart: Cart = this.tokenStorageService.getCart();
        let request = new NewCartItemRequest(cart.cartId, article.articleId);
        request.buyingType = BuyingType.ARTICLE;
        this.testService.addToCart(request).subscribe(data => {
    
        });
      });
    } else{
      const cart: Cart = this.tokenStorageService.getCart();
      let request = new NewCartItemRequest(cart.cartId, article.articleId);
      request.buyingType = BuyingType.ARTICLE;
      this.testService.addToCart(request).subscribe(data => {
  
      });
    }
    
  }

}
