import { PayService } from './../services/pay/pay.service';
import { TestService } from './../services/test/test.service';
import { Router } from '@angular/router';
import { TokenStorageService } from './../services/auth/token-storage.service';
import { Cart } from './../model/shoppingcart';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  private cartSession: Cart;
  private cartDisplay;

  constructor(private tokenStorageService: TokenStorageService, 
    private router: Router,
     private testService: TestService, 
     private payService: PayService) { }

  ngOnInit() {
    let hasCart = this.tokenStorageService.hasCart();
    if(!hasCart){
      this.router.navigate(['error']);
    }else{
       this.cartSession = this.tokenStorageService.getCart();

      this.testService.getCart(+this.cartSession.cartId).subscribe(data => {
        this.cartDisplay = data;
      });
    }
  }

  
  removeFromCart(item) {
    this.testService.removeCart(item.userTxItemId).subscribe(data => {
      this.testService.getCart(+this.cartSession.cartId).subscribe(data => {
        this.cartDisplay = data;
      });
    });
  }

  checkout() {
      let shoppingCartTemp: Cart = new Cart();
      let cart: Cart = this.tokenStorageService.getCart();
     
      this.payService.executePayment(cart).subscribe(data => {
        this.tokenStorageService.removeCart();
        this.router.navigate(['/externalRedirect', { externalUrl: data.kpUrl }], {
          skipLocationChange: true,
            });
      
      });

     
      
      
   
  }


}
