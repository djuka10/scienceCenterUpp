package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.repo.DoiGenerator;



public interface DoiGeneratorRepository extends JpaRepository<DoiGenerator, Integer> {

}
