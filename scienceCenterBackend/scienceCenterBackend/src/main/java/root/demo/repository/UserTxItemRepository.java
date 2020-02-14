package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.user.tx.UserTxItem;


public interface UserTxItemRepository extends JpaRepository<UserTxItem, Long> {

//	@Modifying
//	@Transactional
//	@Query("DELETE FROM UserTxItem f WHERE f.userTxItemId = ?1")
//	void deleteTxItem(Long id);
}
