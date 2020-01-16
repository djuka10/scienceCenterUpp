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
import root.demo.repository.MagazineRepository;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

@Service
public class AddChiefEditor implements JavaDelegate{

	
	@Autowired
	ScienceAreaRepository repo;
	
	@Autowired
	MagazineRepository magazineRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Magazine magazine = magazineRepo.findByusername((String) execution.getVariable("issn"));
		
		String mail = (String) execution.getVariable("mail");
		
		MyUser user = userRepo.findBymail(mail);
		
		magazine.setChiefEditor(user);
		
		magazineRepo.save(magazine);
		
	}

}
