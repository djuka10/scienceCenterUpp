package root.demo.model;

import root.demo.model.repo.BuyingType;

public class NewCartItemRequest {
	
	private long cartId;
	private long articleId;
	
	private BuyingType buyingType;
	
	public long getCartId() {
		return cartId;
	}
	public void setCartId(long cartId) {
		this.cartId = cartId;
	}
	public long getArticleId() {
		return articleId;
	}
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	public NewCartItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewCartItemRequest(long cartId, long articleId) {
		super();
		this.cartId = cartId;
		this.articleId = articleId;
	}
	
	public BuyingType getBuyingType() {
		return buyingType;
	}
	public void setBuyingType(BuyingType buyingType) {
		this.buyingType = buyingType;
	}
	
	

}
