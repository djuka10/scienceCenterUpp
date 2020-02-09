package root.demo.camunda.service.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import root.demo.repository.UserRepository;



@Service
public class ProcessAddingReviewer implements JavaDelegate {

	@Autowired
	private EditorReviewerByScienceAreaRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		List<EditorReviewerByScienceAreaDto> reviewersList = new ArrayList<EditorReviewerByScienceAreaDto>();
		
		List<String> reviewers = (List<String>) execution.getVariable("reviewers");
		List<String> reviewersUsername = new ArrayList<String>();
		
		for (String string : reviewers) {
			EditorReviewerByScienceArea erbsa = repo.getOne(Long.parseLong(string));
			MyUser user = userRepo.getOne(erbsa.getEditorReviewer().getId());
			
			reviewersUsername.add(user.getUsername());
			
			
		}
		
		
		
	/*	for (String rev : reviewers) {
			EditorReviewerByScienceArea erbsa = repo.getOne(Long.parseLong(rev));
			
			
			UserDto rDto = new UserDto(erbsa.getEditorReviewer().getId(), erbsa.getEditorReviewer().getFirstName(), erbsa.getEditorReviewer().getLastName(), erbsa.getEditorReviewer().getMail(), erbsa.getEditorReviewer().getCity(), erbsa.getEditorReviewer().getCountry(), erbsa.getEditorReviewer().getUsername(), erbsa.getEditorReviewer().getTitle());
			
			ScienceArea articleScienceArea = erbsa.getScienceArea();
			ScienceAreaDto articleScienceAreaDto = new ScienceAreaDto(
					articleScienceArea.getScienceAreaId(), 
					articleScienceArea.getScienceAreaName(),
					articleScienceArea.getScienceAreaCode());
			
			
			
			Magazine magazine = erbsa.getMagazine();
			
			Set<ScienceArea> scienceAreas = magazine.getScienceAreas();
			List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
			scienceAreas.stream().forEach(sc -> {
				scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode()));
			});
			
			MagazineDto mDto = new MagazineDto(magazine.getMagazineId(), magazine.getUsername(), magazine.getName(), scienceAreasDto);
			
			
			
			EditorReviewerByScienceAreaDto dto = new EditorReviewerByScienceAreaDto(erbsa.getEditorByScArId(), false, rDto, articleScienceAreaDto, mDto);
			reviewersList.add(dto);
			
		}*/
		
		
//		List<UserDto> onlyReviewers = reviewers.stream().map(EditorReviewerByScienceAreaDto::getEditorReviewer).collect(Collectors.toList());
//		List<String> reviewersIds = onlyReviewers.stream().map(UserDto::getCity).collect(Collectors.toList());
//		List<String> reviewersIds = reviewers.stream().map(c -> c.getEditorReviewer().getUserUsername()).collect(Collectors.toList());

		
		articleProcessDto.setReviewers(reviewersUsername);
		
		execution.setVariable("articleProcessDto", articleProcessDto);
		
		
		
		//List<String> reviewersList = articleProcessDto.getReviewers();

		
	}

}
