package root.demo.model.user.tx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import root.demo.model.repo.Article;
import root.demo.model.repo.BuyingType;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MagazineEdition;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserTxItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long userTxItemId;
	
	@Column
	private Float price;
	
	@Column
	private Boolean sucess;
	
	@ManyToOne
	@JoinColumn(name = "tx_id")
	private UserTx userTx;
	//private Magazine seller; //ili neki seller id
	
	@Column
	@Enumerated(value = EnumType.STRING)
	private BuyingType buyingType;
	
	@ManyToOne
	@JoinColumn(name = "magazine_id")
	private Magazine purhasedMagazine;
	
	@ManyToOne
	@JoinColumn(name = "article_id")
	private Article purchasedArticle;
	
	@ManyToOne
	@JoinColumn(name = "edition_id")
	private MagazineEdition purhasedMagazineEdition;
	
	@Column
	private Long itemId;

	
	public UserTxItem(Float price, UserTx userTx, BuyingType buyingType, Long itemId) {
		super();
		this.price = price;
		this.userTx = userTx;
		this.buyingType = buyingType;
		this.itemId = itemId;
	}
}
