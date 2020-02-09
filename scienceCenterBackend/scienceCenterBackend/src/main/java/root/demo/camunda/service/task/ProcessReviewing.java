package root.demo.camunda.service.task;

import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.ArticleProcessDto;
import root.demo.model.ReviewArticleMfDto;
import root.demo.model.ReviewingDto;
import root.demo.model.repo.OpinionAboutArticle;



@Service
public class ProcessReviewing implements JavaDelegate {

	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		
		String processInstanceId = execution.getProcessInstanceId();
		String subProcessInstanceId = execution.getParentId();
		
		Map<String, Object> m1 = execution.getVariables();
		Map<String, Object> m2 = execution.getVariablesLocal();
		ReviewArticleMfDto ra= (ReviewArticleMfDto) execution.getVariable("subProcessData");
		ReviewArticleMfDto r = (ReviewArticleMfDto) execution.getVariableLocal("subProcessData");
		
		ReviewingDto reviewingDto = ra.getReviewerOpinion();
		OpinionAboutArticle opinion = reviewingDto.getOpinion();
		
		OpinionAboutArticle op = new OpinionAboutArticle();
		op.setArticleId(articleProcessDto.getArticleId());
		op.setComment(ra.getOpinion());
		op.setCommentOnlyForEditor(ra.getOpinionForEditors());
		op.setPersonOpinionId(ra.getTaskInitiator());
		if(ra.getPreporuka().equals("v2")) {
			op.setResult("Prihvata se uz manje ispravke");
		} else if(ra.getPreporuka().equals("v3")) {
			op.setResult("Uslovno se prihvata uz vece ispravke");
		} else if(ra.getPreporuka().equals("v4")) {
			op.setResult("Odbija se");
		} else {
			op.setResult("Prihvata se");
		}
		
		articleProcessDto.getOpinions().add(op);
		execution.setVariable("articleProcessDto", articleProcessDto);

	}

}
