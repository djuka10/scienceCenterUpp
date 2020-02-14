package root.demo.camunda.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.AddReviewersDto;
import root.demo.model.ArticleDto;
import root.demo.model.ArticleProcessDto;
import root.demo.model.EditorReviewerByScienceAreaDto;
import root.demo.model.MagazineDto;
import root.demo.model.ReviewArticleMfDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.ScienceArea;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;



@Service
public class AddReviewerForArticleWhenErrorDataInitialization implements TaskListener {

	@Autowired
	private MagazineRepository magazineRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		DelegateExecution execution = delegateTask.getExecution();
		String proccessId = delegateTask.getProcessInstanceId();
		String taskId = delegateTask.getId();
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		Magazine magazine = magazineRepo.getOne(articleProcessDto.getMagazineId());
		MyUser author = userRepo.findByusername(articleProcessDto.getAuthor());
		Set<MyUser> coAuthors = article.getCoAuthors();
		List<UserDto> coAuthorsDto = new ArrayList<UserDto>();
		
		coAuthors.stream().forEach(c -> {
			coAuthorsDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle()));
		});
		
		ScienceArea scienceArea = article.getScienceArea();
		ScienceAreaDto scienceAreaDto = new ScienceAreaDto(scienceArea.getScienceAreaId(), scienceArea.getScienceAreaName(), scienceArea.getScienceAreaCode());
		
		UserDto authorDto = new UserDto(author.getId(), author.getFirstName(), author.getLastName(), author.getMail(), author.getCity(), author.getCountry(), author.getUsername(), author.getTitle());
		
		ArticleDto articleDto = new ArticleDto(delegateTask.getId(), execution.getProcessInstanceId(), article.getArticleId(), article.getArticleTitle(), article.getArticleAbstract(), 
				scienceAreaDto, article.getPublishingDate(), authorDto, coAuthorsDto, null, null, null,"");
		
		Set<ScienceArea> scienceAreas = magazine.getScienceAreas();
		List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
		scienceAreas.stream().forEach(sc -> {
			scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode()));
		});
		
		MagazineDto magazineDto = new MagazineDto(magazine.getMagazineId(), magazine.getUsername(), magazine.getName(), scienceAreasDto);
		
		List<EditorReviewerByScienceAreaDto> reviewersDto = (ArrayList<EditorReviewerByScienceAreaDto>) execution.getVariable("reviewersByScArea");
		
		reviewersDto = filterReviewers(execution, articleProcessDto, reviewersDto);
		
//		String subProcessId = execution.getParentId();
		String subProcessId = execution.getParentActivityInstanceId();

		AddReviewersDto addReviewersDto = new AddReviewersDto(taskId,proccessId, new ArrayList<FormField>(), articleDto, magazineDto, reviewersDto, subProcessId, true);
		execution.setVariable("addReviewersDto", addReviewersDto);
	}
	
	private List<EditorReviewerByScienceAreaDto> filterReviewers(DelegateExecution execution, ArticleProcessDto articleProcessDto, List<EditorReviewerByScienceAreaDto> initialList){
		List<EditorReviewerByScienceAreaDto> retVal = new ArrayList<EditorReviewerByScienceAreaDto>(initialList);
		
		ReviewArticleMfDto r = (ReviewArticleMfDto) execution.getVariable("subProcessData");
		String iresponsibleReviewerId = r.getPreviousTaskInitiator();
		
		List<EditorReviewerByScienceAreaDto> listToRemove = new ArrayList<EditorReviewerByScienceAreaDto>();
		
		EditorReviewerByScienceAreaDto iresponsbleRev =  retVal.stream().filter(rev -> rev.getEditorReviewer().getUserUsername().equals(iresponsibleReviewerId)).findFirst().orElse(null);
		if(iresponsbleRev != null) {
			listToRemove.add(iresponsbleRev);
		}
		
		List<String> existingReviewerIds = articleProcessDto.getReviewers();
		
		existingReviewerIds.forEach(id -> {
			EditorReviewerByScienceAreaDto existingRev =  retVal.stream().filter(rev -> rev.getEditorReviewer().getUserUsername().equals(id)).findFirst().orElse(null);
			if(existingRev != null) {
				listToRemove.add(existingRev);
			}
		});
		
		retVal.removeAll(listToRemove);
		
		
		return retVal;
	}

}
