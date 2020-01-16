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
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

@Service
public class ActivateUserService implements JavaDelegate {

	@Autowired
	UserRepository repo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		MyUser user = repo.findBymail( (String) execution.getVariable("mail"));
		
		user.setActivate(true);
		
		repo.save(user);
		
		
	}

}
