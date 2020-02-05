package root.demo.camunda.service.task;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormFieldsDto;
import root.demo.model.NewArticleRequestDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.TermDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.Role;
import root.demo.model.repo.RoleName;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.MagazineRepository;
import root.demo.repository.RoleRepository;
import root.demo.repository.TermRepository;
import root.demo.repository.UserRepository;

@Service
public class NewArticleFormDataInitialization implements TaskListener {

	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TermRepository termRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	

	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		DelegateExecution execution = delegateTask.getExecution();
		String proccessId = delegateTask.getProcessInstanceId();
		String taskId = delegateTask.getId();
		
		// List<ScienceArea> scienceAreas = unityOfWork.getScienceAreaRepository().findAll();
		
		Long magazineId = (Long) execution.getVariable("select_magazine_id");
		Magazine magazine = magRepo.getOne(magazineId);
		List<ScienceArea> scienceAreas = new ArrayList<ScienceArea>(magazine.getScienceAreas());
		
		Role r = roleRepo.getOne(5l);
		List<MyUser> coAuthors = userRepo.findByRoleOrderByLastName(r);
		//naredne tri linije nista ne uradi jer nisam setiovao varijablu
		//user
		Long userId = (Long) execution.getVariable("user");
		MyUser authorInitiator = userRepo.getOne(userId);
		coAuthors.remove(authorInitiator);
		
		List<Term> terms = termRepo.findAll();

		
		List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
		List<TermDto> termsDto = new ArrayList<TermDto>();
		List<UserDto> coAuthorsDto = new ArrayList<UserDto>();
		
		scienceAreas.stream().forEach(sc -> scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode())));
		terms.stream().forEach(t -> termsDto.add(new TermDto(t.getTermId(), t.getTermName())));
		coAuthors.stream().forEach(c -> coAuthorsDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle())));//vocation --> titula
	

		// TODO ne radi formService, nekako pokusati u controlleru da ubacimo ove formField-ove kako
		//bismo poslali sve djuture na front ==> Sutra, sad u KIKINDU!
		
				
		
		NewArticleRequestDto dto = new NewArticleRequestDto(taskId, proccessId, new ArrayList<FormField>(), scienceAreasDto, termsDto, coAuthorsDto);
		//NewArticleRequestDto requestDto = new NewArticleRequestDto(taskId, proccessId, scienceAreasDto, termsDto, coAuthorsDto);
		
		execution.setVariable("newArticleRequestDto", dto);
	}

}
