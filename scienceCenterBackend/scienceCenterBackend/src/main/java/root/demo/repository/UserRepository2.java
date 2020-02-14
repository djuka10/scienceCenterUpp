package root.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import root.demo.model.repo.UserA;



public interface UserRepository2 extends JpaRepository<UserA, Long> {

} 
