package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import root.demo.model.repo.*;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {
	Magazine findByusername(String username);
}
