package root.demo.model.repo;

import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import root.demo.model.user.tx.Membership;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Magazine {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long magazineId;
	
	@Column(name = "username")
	private String username; //ISSN BROJ, MORAO SAM OVO ZBOG BAZE
	
	@Column 
	private String name;
	
	@Column
	@ManyToMany(fetch = FetchType.EAGER,cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH
        })
	@JoinTable(name="magazine_scArea", joinColumns = @JoinColumn(name = "magazine_id", referencedColumnName = "magazineId"), inverseJoinColumns = @JoinColumn(name="sc_areaId", referencedColumnName = "scienceAreaId"))
	private Set<ScienceArea> scienceAreas;
	
	@Column
	@Enumerated(value = EnumType.STRING)
	private WayOfPayment wayOfPayment;
	
	/*
	 * "rje za upozorenje@SuperBuilder will ignore the initializing expression entirely. 
	 * If you want the initializing expression to serve as default, add @Builder.Default. 
	 * If it is not supposed to be settable during building, make the field final."
	 * */
	@OneToMany(mappedBy = "magazine")
	@Builder.Default 
	private Set<Membership> memberships = new HashSet<Membership>();
	
	@ManyToOne
	@JoinColumn(name = "chief_editor_id")
	private MyUser chiefEditor;
	
	@OneToMany(mappedBy = "magazine", fetch = FetchType.EAGER)
	@Builder.Default 
	private Set<EditorReviewerByScienceArea> editorsReviewersByScienceArea = new HashSet<EditorReviewerByScienceArea>();

//	@OneToMany(mappedBy = "magazine", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
//	@OneToMany(mappedBy = "magazine")
//	@OneToMany(mappedBy = "magazine", fetch = FetchType.EAGER,
//		    cascade = {
//		            CascadeType.MERGE,
//		            CascadeType.PERSIST
//		        })
	//pravilo erore: JPA: detached entity passed to persist: nested exception is org.hibernate.PersistentObjectException
	@OneToMany(mappedBy = "magazine", fetch = FetchType.EAGER)
	private Set<MagazineEdition> magazineEditions;
	
	@Column
	private boolean active;
	
	@Column
	private Float membershipPrice;
	
	@ManyToMany
	private Set<MyUser> reviewers;//recenzenti
	
	@ManyToMany
	private Set<MyUser> editors; //urednici
	
	@Column
	private Long sellerIdentifier;
	
	@Column
	private Double price;
	
	public Magazine(String iSSN, String name, WayOfPayment wayOfPayment, boolean active,
			Long sellerIdentifier, Double price) {
		super();
		this.magazineId = magazineId;
		username = iSSN;
		this.name = name;
		this.wayOfPayment = wayOfPayment;
		this.active = active;
		this.sellerIdentifier = sellerIdentifier;
		this.price = price;
	}
	
	public Magazine(Long magazineId, String iSSN, String name, WayOfPayment wayOfPayment, boolean active,
			Long sellerIdentifier, Double price) {
		super();
		this.magazineId = magazineId;
		username = iSSN;
		this.name = name;
		this.wayOfPayment = wayOfPayment;
		this.active = active;
		this.sellerIdentifier = sellerIdentifier;
		this.price = price;
	}
	
//	@Column
//	private String kindOfPay;
//	
//	@Column
//	private Boolean active;
//	
//	@ManyToMany
//	private Set<ScienceArea> scienceAreas;
//
//	@OneToOne
//	private MyUser chiefEditor;
//	
//	@OneToMany
//	private Set<EditorByScienceArea> editorsByScienceArea;//urednici naucnih oblasti
//	@ManyToMany
//	private Set<MyUser> reviewers;//recenzenti
//	
//	@ManyToMany
//	private Set<MyUser> editors; //urednici
	
	
	
//	public Magazine() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	
//	
//	
//	public Magazine(Long magazineId, String username, String name, String kindOfPay, Boolean active) {
//		super();
//		this.magazineId = magazineId;
//		this.username = username;
//		this.name = name;
//		this.kindOfPay = kindOfPay;
//		this.active = active;
//	}
//
//
//
//	public Magazine(Long magazineId, String username, String kindOfPay, Boolean active) {
//		super();
//		this.magazineId = magazineId;
//		this.username = username;
//		this.kindOfPay = kindOfPay;
//		this.active = active;
//	}
//
//
//
//	public Magazine(Long magazineId, String username, String kindOfPay, Boolean active, Set<ScienceArea> scienceAreas,
//			MyUser chiefEditor, Set<EditorByScienceArea> editorsByScienceArea, Set<MyUser> reviewers) {
//		super();
//		this.magazineId = magazineId;
//		this.username = username;
//		this.kindOfPay = kindOfPay;
//		this.active = active;
//		this.scienceAreas = scienceAreas;
//		this.chiefEditor = chiefEditor;
//		this.editorsByScienceArea = editorsByScienceArea;
//		this.reviewers = reviewers;
//	}
//	
//	public String getUsername() {
//		return username;
//	}
//
//
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//
//
//	public Set<MyUser> getEditors() {
//		return editors;
//	}
//
//
//
//	public void setEditors(Set<MyUser> editors) {
//		this.editors = editors;
//	}
//
//
//
//	public String getName() {
//		return name;
//	}
//
//
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//
//	public Long getMagazineId() {
//		return magazineId;
//	}
//	public void setMagazineId(Long magazineId) {
//		this.magazineId = magazineId;
//	}
//	public String getusername() {
//		return username;
//	}
//	public void setusername(String username) {
//		this.username = username;
//	}
//	public String getKindOfPay() {
//		return kindOfPay;
//	}
//	public void setKindOfPay(String kindOfPay) {
//		this.kindOfPay = kindOfPay;
//	}
//	public Boolean getActive() {
//		return active;
//	}
//	public void setActive(Boolean active) {
//		this.active = active;
//	}
//	public Set<ScienceArea> getScienceAreas() {
//		return scienceAreas;
//	}
//	public void setScienceAreas(Set<ScienceArea> scienceAreas) {
//		this.scienceAreas = scienceAreas;
//	}
//	public MyUser getChiefEditor() {
//		return chiefEditor;
//	}
//	public void setChiefEditor(MyUser chiefEditor) {
//		this.chiefEditor = chiefEditor;
//	}
//	public Set<EditorByScienceArea> getEditorsByScienceArea() {
//		return editorsByScienceArea;
//	}
//	public void setEditorsByScienceArea(Set<EditorByScienceArea> editorsByScienceArea) {
//		this.editorsByScienceArea = editorsByScienceArea;
//	}
//	public Set<MyUser> getReviewers() {
//		return reviewers;
//	}
//	public void setReviewers(Set<MyUser> reviewers) {
//		this.reviewers = reviewers;
//	}
//	
//	//private Set<MagazineEdition> magazineEditions;
//	
//	
//	//private Float membershipPrice;
//	
//	//zbog KP
//	//private Long sellerIdentifier;
	
	
	
	
}
