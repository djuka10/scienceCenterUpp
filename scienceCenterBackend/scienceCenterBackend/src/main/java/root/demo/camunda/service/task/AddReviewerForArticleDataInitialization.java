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
public class AddReviewerForArticleDataInitialization implements TaskListener {

	@Autowired
	private ArticleRepository artRepo;
	@Autowired
	private MagazineRepository magRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		DelegateExecution execution = delegateTask.getExecution();
		String proccessId = delegateTask.getProcessInstanceId();
		String taskId = delegateTask.getId();
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		Article article = artRepo.getOne(articleProcessDto.getArticleId());
		Magazine magazine = magRepo.getOne(articleProcessDto.getMagazineId());
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
				scienceAreaDto, article.getPublishingDate(), authorDto, coAuthorsDto, null, null, null);
		
		Set<ScienceArea> scienceAreas = magazine.getScienceAreas();
		List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
		scienceAreas.stream().forEach(sc -> {
			scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode()));
		});
		
		MagazineDto magazineDto = new MagazineDto(magazine.getMagazineId(), magazine.getUsername(), magazine.getName(), scienceAreasDto);
		
		List<EditorReviewerByScienceAreaDto> reviewersDto = (ArrayList<EditorReviewerByScienceAreaDto>) execution.getVariable("reviewersByScArea");
		
		
		AddReviewersDto addReviewersDto = new AddReviewersDto(taskId,proccessId,new ArrayList<FormField>(), articleDto, magazineDto, reviewersDto, "", false);
		//AddReviewersDto addReviewersDto = new AddReviewersDto(taskId,proccessId,null, magazineDto, reviewersDto, "", false);
		execution.setVariable("addReviewersDto", addReviewersDto);
	}

}
