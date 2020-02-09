package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import root.demo.model.ArticleProcessDto;
import root.demo.model.ReviewArticleMfDto;



public class ProcessAddReviewerWhenError implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");


		ReviewArticleMfDto ra = (ReviewArticleMfDto) execution.getVariable("subProcessData");
		ReviewArticleMfDto r = (ReviewArticleMfDto) execution.getVariableLocal("subProcessData");
		
		String irresponsibleReviewerId = r.getTaskInitiator();
		
		articleProcessDto.getReviewers().add(r.getTaskInitiator());
		
		execution.setVariable("articleProcessDto", articleProcessDto);
		execution.setVariable("subProcessData", r);
	}

}
