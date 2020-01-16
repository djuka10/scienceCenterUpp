package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;
import root.demo.model.repo.*;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class ActivateMagazineService implements JavaDelegate {

	
	@Autowired
	MagazineRepository repo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		//MAJMUNCINO ZAVRSI OVO
		List<FormSubmissionDto> confirmMagazine = (List<FormSubmissionDto>)execution.getVariable("confirmMagazine");
		System.out.println(confirmMagazine);
		
		String issn = (String) execution.getVariable("issn");
		Magazine mag = repo.findByusername(issn);
		
		mag.setActive(true);
		
		repo.save(mag);
		
		//kraj drugog procesa
		
	}

}
