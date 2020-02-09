package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import root.demo.model.ArticleProcessDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.ArticleStatus;
import root.demo.repository.ArticleRepository;

@Service
public class SavingArticleWithMajorChangesResult implements JavaDelegate{
	@Autowired
	private ArticleRepository articleRepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		//String articleStatusString = (String) execution.getVariableLocal("article_result");
		ArticleStatus articleStatus = ArticleStatus.MAJOR_CHANGES;//ArticleStatus.valueOf(articleStatusString);
		
		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		article.setStatus(articleStatus);
		articleRepo.save(article);
	}
	
	
}
