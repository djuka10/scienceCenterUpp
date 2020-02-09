package root.demo.camunda.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.EditorReviewerByScienceAreaDto;
import root.demo.model.MagazineDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.UserDto;
import root.demo.model.repo.EditorReviewerByScienceArea;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.ScienceArea;
import root.demo.repository.EditorReviewerByScienceAreaRepository;
import root.demo.repository.MagazineRepository;

@Service
public class RetrieveReviewers implements JavaDelegate {

	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private EditorReviewerByScienceAreaRepository editorRevByRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		Magazine magazine = magRepo.getOne(articleProcessDto.getMagazineId());
		
		List<EditorReviewerByScienceArea> reviewers = editorRevByRepo.findByMagazineAndEditor(magazine, false);;
		List<EditorReviewerByScienceAreaDto> reviewersDto = new ArrayList<EditorReviewerByScienceAreaDto>();
		
		reviewers.stream().forEach(r -> {
			MyUser u = r.getEditorReviewer();
			UserDto rDto = new UserDto(u.getId(), u.getFirstName(), u.getLastName(), u.getMail(), u.getCity(), u.getCountry(), u.getUsername(), u.getTitle());
			
			ScienceArea articleScienceArea = r.getScienceArea();
			ScienceAreaDto articleScienceAreaDto = new ScienceAreaDto(
					articleScienceArea.getScienceAreaId(), 
					articleScienceArea.getScienceAreaName(),
					articleScienceArea.getScienceAreaCode());
			
			Set<ScienceArea> scienceAreas = magazine.getScienceAreas();
			List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
			scienceAreas.stream().forEach(sc -> {
				scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode()));
			});
			
			MagazineDto mDto = new MagazineDto(magazine.getMagazineId(), magazine.getUsername(), magazine.getName(), scienceAreasDto);
			
			EditorReviewerByScienceAreaDto e = new EditorReviewerByScienceAreaDto(r.getEditorByScArId(), r.isEditor(), rDto, articleScienceAreaDto, mDto);
			reviewersDto.add(e);
		});
		
		execution.setVariable("reviewersByScArea", reviewersDto);
	}

}
