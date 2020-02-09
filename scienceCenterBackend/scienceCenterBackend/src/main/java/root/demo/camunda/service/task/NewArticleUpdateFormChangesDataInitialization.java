package root.demo.camunda.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.NewArticleRequestDto;
import root.demo.model.NewArticleResponseDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.TermDto;
import root.demo.model.UpdateArticleChangesDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.ArticleStatus;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.OpinionAboutArticle;
import root.demo.model.repo.ReviewingType;
import root.demo.model.repo.Role;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.RoleRepository;
import root.demo.repository.TermRepository;
import root.demo.repository.UserRepository;
import root.demo.util.Base64Utility;

@Service
public class NewArticleUpdateFormChangesDataInitialization implements TaskListener {

	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private TermRepository termRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		DelegateExecution execution = delegateTask.getExecution();
		String proccessId = delegateTask.getProcessInstanceId();
		String taskId = delegateTask.getId();
				
		Long magazineId = (Long) execution.getVariable("select_magazine_id");
		Magazine magazine = magRepo.getOne(magazineId);
		List<ScienceArea> scienceAreas = new ArrayList<ScienceArea>(magazine.getScienceAreas());
		
		Role r = roleRepo.getOne(5l);
		
		List<MyUser> coAuthors = userRepo.findByRoleOrderByLastName(r);
		Long userId = (Long) execution.getVariable("user");
		MyUser authorInitiator = userRepo.getOne(userId);
		coAuthors.remove(authorInitiator);
		
		List<Term> terms = termRepo.findAll();

		List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
		List<TermDto> termsDto = new ArrayList<TermDto>();
		List<UserDto> coAuthorsDto = new ArrayList<UserDto>();
		
		scienceAreas.stream().forEach(sc -> scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode())));
		terms.stream().forEach(t -> termsDto.add(new TermDto(t.getTermId(), t.getTermName())));
		coAuthors.stream().forEach(c -> coAuthorsDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle())));
	
		NewArticleRequestDto requestDto = new NewArticleRequestDto(taskId, proccessId, new ArrayList<FormField>(), scienceAreasDto, termsDto, coAuthorsDto);

		
		ArticleProcessDto articleDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		Article oldSavedArticle = articleRepo.getOne(articleDto.getArticleId());
		
		String decodedFile = Base64Utility.encode(oldSavedArticle.getFile());
		Set<Term> termsArticle = oldSavedArticle.getKeyTerms();
		Set<MyUser> coAuthorsArticle  = oldSavedArticle.getCoAuthors();
		
		List<TermDto> termsArticleDto = new ArrayList<TermDto>();
		List<UserDto> coAuthorsArticleDto = new ArrayList<UserDto>();
		
		termsArticle.stream().forEach(t -> termsArticleDto.add(new TermDto(t.getTermId(), t.getTermName())));
		coAuthorsArticle.stream().forEach(c -> coAuthorsArticleDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle())));
		
		NewArticleResponseDto responseDto = new NewArticleResponseDto(
				oldSavedArticle.getArticleTitle(), oldSavedArticle.getArticleAbstract(), 
				Long.toString(oldSavedArticle.getScienceArea().getScienceAreaId()), oldSavedArticle.getArticlePrice(), 
				termsArticleDto, coAuthorsArticleDto, 
				oldSavedArticle.getFileFormat(), decodedFile);
		
		Long authorId = (Long) execution.getVariable("user");
		String id = authorId.toString();
		
		OpinionAboutArticle opinion = new OpinionAboutArticle(articleDto.getArticleId(), id, ReviewingType.AUTHOR_RESPONSE, ArticleStatus.ACCEPTED, "", "","", articleDto.getIteration());

		
		//scienceAreas.stream().forEach(sc -> scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode())));
		
		
		UpdateArticleChangesDto updateArticleChangesDto = new UpdateArticleChangesDto(taskId, proccessId, new ArrayList<FormField>(),scienceAreasDto,termsDto,coAuthorsDto,requestDto, responseDto, articleDto.getOpinions(), articleDto.getEditorOpinions(), opinion);
		
		execution.setVariable("updateArticleChangesDto", updateArticleChangesDto);
	}

}
