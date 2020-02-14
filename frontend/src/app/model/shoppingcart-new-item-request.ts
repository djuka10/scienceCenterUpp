export enum BuyingType {
	MEMBERSHIP, MAGAZINE_EDITION, ARTICLE
}
export class NewCartItemRequest{

    cartId: number;
    articleId: number;

    buyingType: BuyingType;

    constructor(cartId:number, articleId: number) {
        this.cartId = cartId;
        this.articleId = articleId;
    }
}