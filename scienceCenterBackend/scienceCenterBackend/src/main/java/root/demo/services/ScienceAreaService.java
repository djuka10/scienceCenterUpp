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
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

@Service
public class ScienceAreaService implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	ScienceAreaRepository repo;
	
	@Autowired
	UserRepository userRepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		 List<FormSubmissionDto> sciencearea = (List<FormSubmissionDto>)execution.getVariable("sciencearea");
		 System.out.println(sciencearea);
		 
		 MyUser user = userRepo.findBymail((String) execution.getVariable("mail")); 
		 
		 List<ScienceArea> lista = repo.findAll();
		 
		 //pretpostavka da nece izabrati dva puta jednu te istu naucnu oblast ako se na to odluci
		 for (FormSubmissionDto formField : sciencearea) {
			 if(formField.getFieldId().equals("naucnaOblast")) {
				 for (ScienceArea scienceArea2 : lista) {
					if(scienceArea2.getScienceAreaName().equals(formField.getFieldValue())) {
						 user.getScienceArea().add(scienceArea2);
						 break;
					}
				}
				 break;
			 }
		 }
		 
		 userRepo.save(user);
		 
		
		 
		 
		 
		 
		 
		 
	}

}
