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
import root.demo.model.ScienceAreaDto;
import root.demo.model.TermDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.ArticleRepository;


@Service
public class NewArticleAnalizeArticleDataInitializationi implements TaskListener {

	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		DelegateExecution execution = delegateTask.getExecution();
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");

		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		
		ScienceArea sc = article.getScienceArea();
		ScienceAreaDto scienceAreaDto = new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode());
		
		MyUser author = article.getAuthor();
		UserDto authorDto = new UserDto(author.getId(), 
				 author.getFirstName(), author.getLastName(), 
				 author.getMail(), author.getCity(), 
				 author.getCountry(), author.getUsername(), author.getTitle());
		
		Set<MyUser> coAuthors = article.getCoAuthors();
		List<UserDto> coAuthorsDto = new ArrayList<UserDto>();
		coAuthors.stream().forEach(c -> {
			coAuthorsDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle()));
		});
		
		Set<Term> terms = article.getKeyTerms();		
		List<TermDto> termsDto = new ArrayList<TermDto>();
		terms.stream().forEach(t -> {
			termsDto.add( new TermDto(t.getTermId(), t.getTermName()));
		});
		
		ArticleDto articleDto = new ArticleDto(delegateTask.getId(), 
				delegateTask.getProcessInstanceId(), 
				article.getArticleId(), 
				article.getArticleTitle(), 
				article.getArticleAbstract(), 
				scienceAreaDto, 
				article.getPublishingDate(), 
				authorDto, 
				coAuthorsDto, 
				termsDto, 
				article.getArticlePrice(), null,"");
		
		execution.setVariable("perodeformero", articleDto);
		execution.setVariable("articleRequestDto", articleDto);
	}

}
