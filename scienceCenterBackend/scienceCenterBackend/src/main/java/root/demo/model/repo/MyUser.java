package root.demo.model.repo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.CodePointLength;


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

@Table(name = "my_user")
public class MyUser {
	
	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String mail;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String city;
	@Column
	private String country;
	@Column
	private String title;
	@Column
	private Boolean reviewer;
	@Column
	private Boolean activate; //da li je korisnik aktiviran
	@Builder.Default
	@ManyToMany
	/*@JoinTable(
		name = "user_scienceArea", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "scienceArea_id"))*/
	private Set<ScienceArea> scienceArea = new HashSet<>();
	
	
	@ManyToOne
    private Role role;
	
	@Builder.Default
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
	@OnDelete(action = OnDeleteAction.CASCADE) 
    private Set<Role> roles = new HashSet<>();
	
	@OneToMany
	private Set<Membership> memberships;
	

//	public Role getRole() {
//		return role;
//	}
//
//
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
//
//	
//
//
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
//
//
//
//	public Set<ScienceArea> getScienceArea() {
//		return scienceArea;
//	}
//
//
//
//	public void setScienceArea(Set<ScienceArea> scienceArea) {
//		this.scienceArea = scienceArea;
//	}
//
//
//
//	public MyUser() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	
//	
//	
//	
//	public MyUser(Long id, String username, String password, String mail, String firstName, String lastName,
//			String city, String country, String title, Boolean reviewer, Boolean activate, Role role) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.password = password;
//		this.mail = mail;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.city = city;
//		this.country = country;
//		this.title = title;
//		this.reviewer = reviewer;
//		this.activate = activate;
//		this.role = role;
//	}
//
//
//
//	public MyUser(Long id, String username, String password, String mail, String firstName, String lastName, String city,
//			String country, String title, Boolean reviewer, Boolean activate) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.password = password;
//		this.mail = mail;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.city = city;
//		this.country = country;
//		this.title = title;
//		this.reviewer = reviewer;
//		this.activate = activate;
//	}
//
//
//
//	public MyUser(Long id, String username, String password) {
//		super();
//		this.id = id;
//		this.username = username;
//		this.password = password;
//	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//
//	public String getMail() {
//		return mail;
//	}
//
//
//
//	public void setMail(String mail) {
//		this.mail = mail;
//	}
//
//
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//
//
//	public String getLastName() {
//		return lastName;
//	}
//
//
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//
//
//	public String getCity() {
//		return city;
//	}
//
//
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//
//
//	public String getCountry() {
//		return country;
//	}
//
//
//
//	public void setCountry(String country) {
//		this.country = country;
//	}
//
//
//
//	public String getTitle() {
//		return title;
//	}
//
//
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//
//
//	public Boolean getReviewer() {
//		return reviewer;
//	}
//
//
//
//	public void setReviewer(Boolean reviewer) {
//		this.reviewer = reviewer;
//	}
//
//
//
//	public Boolean getActivate() {
//		return activate;
//	}
//
//
//
//	public void setActivate(Boolean activate) {
//		this.activate = activate;
//	}



	/*public Set<ScienceArea> getScienceAreaId() {
		return scienceArea;
	}



	public void setScienceAreaId(Set<ScienceArea> scienceAreaId) {
		this.scienceArea = scienceAreaId;
	}*/
	
	
	
	
}
