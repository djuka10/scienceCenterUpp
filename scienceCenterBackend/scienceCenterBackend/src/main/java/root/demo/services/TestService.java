package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

@Service
public class TestService implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
    PasswordEncoder encoder;
	
	//inicijalno ovaj servis sluzi za proveru da li korisnik vec postoji u sistemu
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		  String var = "Pera";
	      var = var.toUpperCase();
	      execution.setVariable("input", var);
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      System.out.println(registration);
	      
	      for (FormSubmissionDto formField : registration) {
				if(formField.getFieldId().equals("username")) {
					MyUser myuser = repo.findByusername(formField.getFieldValue());
					if(myuser != null) {
						execution.setVariable("userexist", false);
						return;
					} else {
						execution.setVariable("userexist", true);
						break;
					}
				}
	      }
	      
	      
	    /*  User user = identityService.newUser("");
	      for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				user.setId(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("password")) {
				user.setPassword(formField.getFieldValue());
			}
	      }
	      identityService.saveUser(user);*/
	      
	      MyUser myuser = new MyUser();
	      for (FormSubmissionDto formField : registration) {
				if(formField.getFieldId().equals("username")) {
					myuser.setUsername(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("password")) {
					myuser.setPassword(encoder.encode(formField.getFieldValue()));
					//myuser.setPassword(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("mail")) {
					myuser.setMail(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("ime")) {
					myuser.setFirstName(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("prezime")) {
					myuser.setLastName(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("grad")) {
					myuser.setCity(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("drzava")) {
					myuser.setCountry(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("titula")) {
					myuser.setTitle(formField.getFieldValue());
					continue;
				}
				if(formField.getFieldId().equals("recenzent")) {
					if(formField.getFieldValue().equals("false")) {
						myuser.setReviewer(false);
						continue;
					} else
						myuser.setReviewer(true);
					continue;
				}
				
					
		      }
	      myuser.setActivate(false);
	      execution.setVariable("mail", myuser.getMail());
	      

	      repo.save(myuser);
	      
		
		
	}
}
