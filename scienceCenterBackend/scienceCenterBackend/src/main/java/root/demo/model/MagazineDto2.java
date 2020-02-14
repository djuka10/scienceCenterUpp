package root.demo.model;

import root.demo.model.repo.Magazine;
import root.demo.model.repo.WayOfPayment;

public class MagazineDto2 {

private Long magazineId;
	
	private String issn;

	private String name;

/*	@ManyToMany
	private Set<ScienceArea> scienceAreas;*/

	private WayOfPayment wayOfPayment;
	
/*	@ManyToMany
	private User chiefEditor;
/*
	private Set<EditorByScienceArea> editorsByScienceArea;

	private Set<User> reviewers;
	
	private Set<MagazineEdition> magazineEditions;*/

	private boolean active;
	//zbog KP -- id prodavca u KP-u ???

	private Long sellerIdentifier;
	
	private Double price;
	
	public MagazineDto2(Long magazineId, String iSSN, String name, WayOfPayment wayOfPayment, boolean active,
			Long sellerIdentifier, Double price) {
		super();
		this.magazineId = magazineId;
		issn = iSSN;
		this.name = name;
		this.wayOfPayment = wayOfPayment;
		this.active = active;
		this.sellerIdentifier = sellerIdentifier;
		this.price = price;
	}
	
	public MagazineDto2(Magazine m) {
		this.magazineId = m.getMagazineId();
		issn = m.getUsername();
		this.name = m.getName();
		this.wayOfPayment = m.getWayOfPayment();
		this.active = true;
		this.sellerIdentifier = m.getSellerIdentifier();
		this.price = m.getPrice();
	}
	
	public Long getMagazineId() {
		return magazineId;
	}
	public void setMagazineId(Long magazineId) {
		this.magazineId = magazineId;
	}
	public String getISSN() {
		return issn;
	}
	public void setISSN(String iSSN) {
		issn = iSSN;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WayOfPayment getWayOfPayment() {
		return wayOfPayment;
	}
	public void setWayOfPayment(WayOfPayment wayOfPayment) {
		this.wayOfPayment = wayOfPayment;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Long getSellerIdentifier() {
		return sellerIdentifier;
	}
	public void setSellerIdentifier(Long sellerIdentifier) {
		this.sellerIdentifier = sellerIdentifier;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
