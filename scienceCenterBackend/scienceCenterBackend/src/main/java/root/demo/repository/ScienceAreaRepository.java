package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import root.demo.model.repo.*;

public interface ScienceAreaRepository extends JpaRepository<ScienceArea, Long> {
	
}
