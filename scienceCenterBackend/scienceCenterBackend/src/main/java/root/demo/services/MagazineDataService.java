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
	      String mail = "";
	      Boolean update = false;
	      
	      
	      for (FormSubmissionDto formField : magazine) {
				if(formField.getFieldId().equals("IssnBr")) {
					Magazine mag = repo.findByusername(formField.getFieldValue());
					if(mag != null) {
						updateMagazine(mag,magazine);
						//idemo u izmenu
						
						update = true;
						execution.setVariable("issn", mag.getUsername()); //moracemo znati kasnije kojem casopisu dodeljujemo urednike i recenzente
						
						execution.setVariable("magexist", false);
						break;
					} else {
						execution.setVariable("magexist", true);
						break;
					}
				}
	      }
	      
	      
	      if(update) {
	    	  for (FormSubmissionDto formField : magazine) {
	    		  if(formField.getFieldId().equals("mail")) {
					  mail = formField.getFieldValue();
				  }
		    	  execution.setVariable("mail", mail);
	    	  }
	    	  
	    	  
	    	  
	      } else {
	    	  Magazine m = new Magazine();

		      for (FormSubmissionDto formField : magazine) {
		    	  if(formField.getFieldId().equals("IssnBr")) {
						m.setUsername(formField.getFieldValue());
						continue;
		    	  }
		    	  if(formField.getFieldId().equals("Naziv")) {
						m.setName(formField.getFieldValue());
						continue;
		    	  }
		    	  if(formField.getFieldId().equals("nacinNaplate")) {
		    		  	
						//m.setWayOfPayment((WayOfPayment) formField.getFieldValue());
						continue;
		    	  }
		    	  if(formField.getFieldId().equals("mail")) {
		    		  mail = formField.getFieldValue();
		    	  }
		      }
		      m.setActive(false);
		      
		      execution.setVariable("issn", m.getUsername()); //moracemo znati kasnije kojem casopisu dodeljujemo urednike i recenzente
		      execution.setVariable("mail", mail);
		      repo.save(m);
		      //Nekako treba pamtiti ko je glavni urednik casopisa
		      //m.setChiefEditor(chiefEditor);
	      }
	      
	     
		
	}
	
	public void updateMagazine(Magazine mag, List<FormSubmissionDto> lista) {
		
		 for (FormSubmissionDto formField : lista) {
			 if(formField.getFieldId().equals("IssnBr")) {
					mag.setUsername(formField.getFieldValue());
					continue;
	    	  }
			 if(formField.getFieldId().equals("Naziv")) {
					mag.setName(formField.getFieldValue());
					continue;
			  }
			  if(formField.getFieldId().equals("nacinNaplate")) {
				  //mag.setKindOfPay(formField.getFieldValue());
					continue;
			  }
			  
		 }
		 mag.setActive(false);
		 repo.save(mag);
		
	}

}
