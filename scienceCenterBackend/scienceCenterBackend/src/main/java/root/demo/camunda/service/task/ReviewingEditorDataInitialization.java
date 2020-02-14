package root.demo.camunda.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleDto;
import root.demo.model.ArticleProcessDto;
import root.demo.model.MagazineDto;
import root.demo.model.ReviewingEditorDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.TermDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.ArticleStatus;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.OpinionAboutArticle;
import root.demo.model.repo.ReviewingType;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;
import root.demo.util.Base64Utility;



@Service
public class ReviewingEditorDataInitialization implements TaskListener {

	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
		DelegateExecution execution = delegateTask.getExecution();
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");

		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
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
		
		Set<Term> terms = article.getKeyTerms();		
		List<TermDto> termsDto = new ArrayList<TermDto>();
		terms.stream().forEach(t -> {
			termsDto.add( new TermDto(t.getTermId(), t.getTermName()));
		});
		
		String encoded = Base64Utility.encode(article.getFile());
		
		String document = encoded;

		ArticleDto articleDto = new ArticleDto(delegateTask.getId(), execution.getProcessInstanceId(), article.getArticleId(), article.getArticleTitle(), article.getArticleAbstract(), 
				scienceAreaDto, article.getPublishingDate(), authorDto, coAuthorsDto, termsDto, article.getArticlePrice(), document,"");
		
		Set<ScienceArea> scienceAreas = magazine.getScienceAreas();
		List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
		scienceAreas.stream().forEach(sc -> {
			scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode()));
		});
		
		MagazineDto magazineDto = new MagazineDto(magazine.getMagazineId(), magazine.getUsername(), magazine.getName(), scienceAreasDto);
		
		
		
		OpinionAboutArticle editorsOpinion = new OpinionAboutArticle(article.getArticleId(), articleProcessDto.getWhoIsScienceEditor(), ReviewingType.REVIEWING_ANALIZE_BY_EDITOR, ArticleStatus.ACCEPTED, "", "","", articleProcessDto.getIteration());
		
		ReviewingEditorDto reviewingDto = new ReviewingEditorDto(articleDto, magazineDto, articleProcessDto.getOpinions(), articleProcessDto.getAuthorsMessages(), editorsOpinion);
		execution.setVariable("editorsReviewing", reviewingDto);
	}

}
