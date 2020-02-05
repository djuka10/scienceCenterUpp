package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineRepository;

@Service
public class FindChiefEditor implements JavaDelegate {
	
	@Autowired
	private MagazineRepository magazineRepo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		
		Magazine magazine = magazineRepo.getOne(articleProcessDto.getMagazineId());
		MyUser chiefEditor = magazine.getChiefEditor();
		
		articleProcessDto.setWhoIsChiefEditor(chiefEditor.getUsername());
		
		execution.setVariable("articleProcessDto", articleProcessDto);
	}

}
