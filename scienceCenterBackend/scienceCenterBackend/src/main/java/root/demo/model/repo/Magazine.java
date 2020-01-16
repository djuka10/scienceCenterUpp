package root.demo.model.repo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
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
	private String kindOfPay;
	
	@Column
	private Boolean active;
	
	@ManyToMany
	private Set<ScienceArea> scienceAreas;

	@OneToOne
	private MyUser chiefEditor;
	
	@OneToMany
	private Set<EditorByScienceArea> editorsByScienceArea;//urednici naucnih oblasti
	@ManyToMany
	private Set<MyUser> reviewers;//recenzenti
	
	
	
	public Magazine() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Magazine(Long magazineId, String username, String name, String kindOfPay, Boolean active) {
		super();
		this.magazineId = magazineId;
		this.username = username;
		this.name = name;
		this.kindOfPay = kindOfPay;
		this.active = active;
	}



	public Magazine(Long magazineId, String username, String kindOfPay, Boolean active) {
		super();
		this.magazineId = magazineId;
		this.username = username;
		this.kindOfPay = kindOfPay;
		this.active = active;
	}



	public Magazine(Long magazineId, String username, String kindOfPay, Boolean active, Set<ScienceArea> scienceAreas,
			MyUser chiefEditor, Set<EditorByScienceArea> editorsByScienceArea, Set<MyUser> reviewers) {
		super();
		this.magazineId = magazineId;
		this.username = username;
		this.kindOfPay = kindOfPay;
		this.active = active;
		this.scienceAreas = scienceAreas;
		this.chiefEditor = chiefEditor;
		this.editorsByScienceArea = editorsByScienceArea;
		this.reviewers = reviewers;
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Long getMagazineId() {
		return magazineId;
	}
	public void setMagazineId(Long magazineId) {
		this.magazineId = magazineId;
	}
	public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public String getKindOfPay() {
		return kindOfPay;
	}
	public void setKindOfPay(String kindOfPay) {
		this.kindOfPay = kindOfPay;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Set<ScienceArea> getScienceAreas() {
		return scienceAreas;
	}
	public void setScienceAreas(Set<ScienceArea> scienceAreas) {
		this.scienceAreas = scienceAreas;
	}
	public MyUser getChiefEditor() {
		return chiefEditor;
	}
	public void setChiefEditor(MyUser chiefEditor) {
		this.chiefEditor = chiefEditor;
	}
	public Set<EditorByScienceArea> getEditorsByScienceArea() {
		return editorsByScienceArea;
	}
	public void setEditorsByScienceArea(Set<EditorByScienceArea> editorsByScienceArea) {
		this.editorsByScienceArea = editorsByScienceArea;
	}
	public Set<MyUser> getReviewers() {
		return reviewers;
	}
	public void setReviewers(Set<MyUser> reviewers) {
		this.reviewers = reviewers;
	}
	
	//private Set<MagazineEdition> magazineEditions;
	
	
	//private Float membershipPrice;
	
	//zbog KP
	//private Long sellerIdentifier;
	
	
	
	
}
