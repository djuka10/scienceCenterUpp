package root.demo.model.repo;

import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScienceArea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long scienceAreaId;
	@Column
	private String scienceAreaName;
	
	@Column(unique = true)
	@Length(min = 3, max = 4)
	private String scienceAreaCode;
	
	
//	public ScienceArea() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//
//
//	public ScienceArea(Long id, String scienceAreaName) {
//		super();
//		this.id = id;
//		this.scienceAreaName = scienceAreaName;
//	}
//
//
//
//	public Long getId() {
//		return id;
//	}
//
//
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//
//
//	public String getScienceAreaName() {
//		return scienceAreaName;
//	}
//
//
//
//	public void setScienceAreaName(String scienceAreaName) {
//		this.scienceAreaName = scienceAreaName;
//	}
	
	
	
	
}
