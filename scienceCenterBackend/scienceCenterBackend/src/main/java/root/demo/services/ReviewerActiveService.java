package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.repository.RoleRepository;
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

@Service
public class ReviewerActiveService implements JavaDelegate{

	
	@Autowired
	UserRepository repo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		MyUser user = repo.findBymail( (String) execution.getVariable("mail"));
		
		user.setReviewer(true);
		
		List<Role> lista = roleRepo.findAll();
		Role r = new Role();
		
		for (Role role : lista) {
			if(role.getName().equals(RoleName.ROLE_REVIEWER)) {
				r = role;
				break;
			}
		}
		
		
		//moglo bi se dodati i u tabelu roles da je reviewer
		//Role reviewerRole = new Role(RoleName.ROLE_REVIEWER);
		user.getRoles().add(r);
		
		repo.save(user);
		
		
	}

}
