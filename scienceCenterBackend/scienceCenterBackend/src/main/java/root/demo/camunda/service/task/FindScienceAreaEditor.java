package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.EditorReviewerByScienceArea;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.ScienceArea;
import root.demo.repository.ArticleRepository;
import root.demo.repository.EditorReviewerByScienceAreaRepository;
import root.demo.repository.MagazineRepository;



@Service
public class FindScienceAreaEditor implements JavaDelegate {

	@Autowired
	private ArticleRepository articleRepo;
	

	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private EditorReviewerByScienceAreaRepository editorRevByRepo;
	
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		//testirati
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		Magazine magazine = magRepo.getOne(articleProcessDto.getMagazineId());
		
		ScienceArea scienceArea = article.getScienceArea();
		EditorReviewerByScienceArea scienceAreaEditor = editorRevByRepo.findByMagazineAndScienceAreaAndEditor(magazine, scienceArea, true);
	
		if(scienceAreaEditor == null) {
			articleProcessDto.setWhoIsScienceEditor(articleProcessDto.getWhoIsChiefEditor());
		}else {
			MyUser scAreaEditor = scienceAreaEditor.getEditorReviewer();
			articleProcessDto.setWhoIsScienceEditor(scAreaEditor.getUsername());
		}
		
		execution.setVariable("articleProcessDto", articleProcessDto);
	}

}
