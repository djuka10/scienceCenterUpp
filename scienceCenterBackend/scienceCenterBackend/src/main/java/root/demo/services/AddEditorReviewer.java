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
public class AddEditorReviewer implements JavaDelegate {

	@Autowired
	MagazineRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		// dodavanje dva editora i dva reviewera
		
		List<FormSubmissionDto> editor_reviewer = (List<FormSubmissionDto>)execution.getVariable("editorReviewer");
		System.out.println(editor_reviewer);
		
		String issn = (String) execution.getVariable("issn");
		Magazine mag = repo.findByusername(issn);
		for (FormSubmissionDto formField : editor_reviewer) {
			if(formField.getFieldValue() == null) {
				continue;
			}
			if(formField.getFieldId().equals("dodavanjeUrednika1")) {
				String mail = formField.getFieldValue().substring(1,formField.getFieldValue().length());
				System.out.println("Mail: " + mail);
				MyUser user = userRepo.findBymail(mail);
				//mag.getEditorsByScienceArea().add(new EditorByScienceArea(1l,user));
				//zavrsiti kasnije ovo
			} else if(formField.getFieldId().equals("dodavanjeUrednika2")) { 
				
			} else if(formField.getFieldId().equals("dodajRecenzenta1")) { 
				
			} else {
				String mail = formField.getFieldValue().substring(1,formField.getFieldValue().length());
				System.out.println("Mail: " + mail);
				MyUser user = userRepo.findBymail(mail);
				mag.getReviewers().add(user);
			}
      }
		
		repo.save(mag);
	}

}
