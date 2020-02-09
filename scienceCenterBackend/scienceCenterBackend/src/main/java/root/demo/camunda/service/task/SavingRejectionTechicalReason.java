package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.ArticleStatus;
import root.demo.repository.ArticleRepository;


@Service
public class SavingRejectionTechicalReason implements JavaDelegate {

	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		
		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		article.setStatus(ArticleStatus.REJECTED_TECHNICAL_REASON);
		articleRepo.save(article);
	}

}
