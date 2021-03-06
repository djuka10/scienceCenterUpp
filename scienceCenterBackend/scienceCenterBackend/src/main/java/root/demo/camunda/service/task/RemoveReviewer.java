package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.ReviewArticleMfDto;
import root.demo.model.ReviewingDto;



@Service
public class RemoveReviewer implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");


		ReviewArticleMfDto ra = (ReviewArticleMfDto) execution.getVariable("subProcessData");
		ReviewArticleMfDto r = (ReviewArticleMfDto) execution.getVariableLocal("subProcessData");
		
		String irresponsibleReviewerId = r.getTaskInitiator();
		
		articleProcessDto.getReviewers().remove(irresponsibleReviewerId);

		
		
		r.setTaskInitiator("");
		r.setPreviousTaskInitiator(irresponsibleReviewerId);
		r.setReviewerOpinion(new ReviewingDto());

		
		//ovo ovde ne sljaka
//		VariableInstance user = runtimeService.createVariableInstanceQuery()
//                .processInstanceIdIn(processInstanceId)
//                .variableName("subProcessData")
//                .list()
//                .stream()
//                .filter(v -> ((ReviewArticleMfDto) v.getValue()).getSubProcessExecutionId().equals(subProcessInstanceId))
//                .findFirst().orElse(null);
//		
//		if(user == null) {}
//		ReviewingDto reviewingDto = ((ReviewArticleMfDto)user.getValue()).getReviewerOpinion();
//		OpinionAboutArticle opinion = reviewingDto.getOpinion();
		
		execution.setVariable("articleProcessDto", articleProcessDto);
		execution.setVariable("subProcessData", r);
	}

}
