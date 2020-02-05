package root.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.repo.Magazine;
import root.demo.model.repo.MagazineEdition;



public interface MagazineEditionRepository extends JpaRepository<MagazineEdition, Long> {

//	MagazineEdition findByMagazineOrderByPublishingDateDesc(Magazine magazine);
	 List<MagazineEdition> findByMagazineOrderByPublishingDateDesc(Magazine magazine);

}
