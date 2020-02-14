package root.demo.model.repo;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


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
@SuperBuilder(toBuilder = true)
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long articleId;
	
	@Column
	private String articleTitle;
	
	@Column
	private String articleAbstract;
	
	@Column
	private Date publishingDate;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private MyUser author;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
        })
	@JoinTable(name="coauthor_article", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "articleId"), inverseJoinColumns = @JoinColumn(name="coauthor_id", referencedColumnName = "userId"))
	private Set<MyUser> coAuthors;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
        })
	@JoinTable(name="article_key_terms", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "articleId"), inverseJoinColumns = @JoinColumn(name="term_id", referencedColumnName = "termId"))
	private Set<Term> keyTerms;
	
	@ManyToOne
	@JoinColumn(name = "science_area_id")
	private ScienceArea scienceArea;
	
	@Column
	private byte[] file;
	
	@Column
	private String fileFormat;
	
//	@Column
//	private String file;
	
	@Column
	@Enumerated(value = EnumType.STRING)
	private ArticleStatus status;
	
	@Column
	private String doi;
	
	@ManyToOne
	@JoinColumn(name = "magazine_edition_id")
	private MagazineEdition magazineEdition;
	
	@Column
	private Float articlePrice;
	
	public Article(String articleTitle, String articleAbstract, Date publishingDate, byte[] file, String fileFormat,
			String doi, MagazineEdition magazineEdition, Float articlePrice) {
		super();
		this.articleTitle = articleTitle;
		this.articleAbstract = articleAbstract;
		this.publishingDate = publishingDate;
		this.file = file;
		this.fileFormat = fileFormat;
		this.doi = doi;
		this.magazineEdition = magazineEdition;
		this.articlePrice = articlePrice;
	}

}
