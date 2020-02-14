package root.demo.model.repo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagazineEdition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long magazineEditionId;
	
	@Column
	private Date publishingDate;
	
	@Column
	private Float magazineEditionPrice;
	
//	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@ManyToOne(fetch = FetchType.EAGER,
//    cascade = {
//            CascadeType.MERGE,
//            CascadeType.PERSIST
//        })
	//pravilo erore: JPA: detached entity passed to persist: nested exception is org.hibernate.PersistentObjectException
	@ManyToOne
	@JoinColumn(name = "magazine_id")
	private Magazine magazine;
	
	@OneToMany(mappedBy = "magazineEdition", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Article> articles;
	
	public MagazineEdition(Date publishingDate, Float magazineEditionPrice, Magazine magazine) {
		super();
		this.publishingDate = publishingDate;
		this.magazineEditionPrice = magazineEditionPrice;
		this.magazine = magazine;
	}

	
}
