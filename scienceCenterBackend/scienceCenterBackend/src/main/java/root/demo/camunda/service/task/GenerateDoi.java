package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.DoiGenerator;
import root.demo.repository.ArticleRepository;
import root.demo.repository.DoiGeneratorRepository;


@Service
public class GenerateDoi implements JavaDelegate {
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private DoiGeneratorRepository doiGeneratorRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		
		
		DoiGenerator d = new DoiGenerator();
		d.setArticle(article);
		DoiGenerator dSaved = doiGeneratorRepository.save(d);
		
		String doi = generateDoi(dSaved);
		article.setDoi(doi);
		articleRepo.save(article);
		
	}
	
	private String generateDoi(DoiGenerator d) {
		Integer b = d.getGeneratedId();
		String bString = b.toString();
		StringBuilder builder = new StringBuilder("10.1").append(bString).insert(7, '/');
		
		return builder.toString();
	}

}
