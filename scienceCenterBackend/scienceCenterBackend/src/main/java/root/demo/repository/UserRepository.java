package root.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import root.demo.model.repo.*;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long>{
	MyUser findByusername(String username);
	MyUser findBymail(String mail);
	
	Optional<MyUser> findByMail(String email);
}
