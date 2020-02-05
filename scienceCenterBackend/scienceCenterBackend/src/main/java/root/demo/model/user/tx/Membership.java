package root.demo.model.user.tx;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Membership {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long membershipId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private MyUser signedUpUser;
	
	@ManyToOne
	@JoinColumn(name = "magazine_id")
	private Magazine magazine;
	
	@Column
	private Date startAt;
	
	@Column
	private Date endAt;
	
	@Column
	private Float price;

}
