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
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

@Service
public class MagazineDataService implements JavaDelegate{

	@Autowired
	MagazineRepository repo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("magazine");
	      System.out.println(magazine);
	      
	      for (FormSubmissionDto formField : magazine) {
				if(formField.getFieldId().equals("ISSN")) {
					Magazine mag = repo.findByusername(formField.getFieldValue());
					if(mag != null) {
						execution.setVariable("magexist", false);
						return;
					} else {
						execution.setVariable("magexist", true);
						break;
					}
				}
	      }
	      
	      Magazine m = new Magazine();
	      
	      String mail = "";
	      
	      
	      for (FormSubmissionDto formField : magazine) {
	    	  if(formField.getFieldId().equals("IssnBr")) {
					m.setusername(formField.getFieldValue());
					continue;
	    	  }
	    	  if(formField.getFieldId().equals("Naziv")) {
					m.setName(formField.getFieldValue());
					continue;
	    	  }
	    	  if(formField.getFieldId().equals("nacinNaplate")) {
					m.setKindOfPay(formField.getFieldValue());
					continue;
	    	  }
	    	  if(formField.getFieldId().equals("mail")) {
	    		  mail = formField.getFieldValue();
	    	  }
	      }
	      m.setActive(false);
	      
	      execution.setVariable("issn", m.getusername()); //moracemo znati kasnije kojem casopisu dodeljujemo urednike i recenzente
	      execution.setVariable("mail", mail);
	      repo.save(m);
	      //Nekako treba pamtiti ko je glavni urednik casopisa
	      //m.setChiefEditor(chiefEditor);
		
	}

}
