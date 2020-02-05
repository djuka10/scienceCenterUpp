package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.repo.Term;

public interface TermRepository extends JpaRepository<Term, Long> {

}
