package root.demo.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.repo.Magazine;
import root.demo.model.user.tx.Membership;


public interface MembershipRepository extends JpaRepository<Membership, Long> {

	Membership findByMagazineAndEndAtBefore(Magazine magazine, Date now);
}
