package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.user.tx.UserTx;



public interface UserTxRepository extends JpaRepository<UserTx, Long> {

	UserTx findBykPClientIdentifier(long kpIdentifier);
	UserTx findByOrderId(long orderId);
}
