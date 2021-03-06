package root.demo.camunda.service.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import root.demo.model.ReviewArticleMfDto;
import root.demo.model.ReviewingDto;


public class ReviewArticleSubProcessInitialization implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		String taskInitiator = (String) execution.getVariable("rec");
//		String subProcessInstanceId = (String) execution.getId();
		String activityInstanceId = execution.getActivityInstanceId();
		
//		System.out.println(">>>>>>>>>>>>>>> MF init listenet >>>>>>>>>>>>>>>>>>>>>>>.");
//		
//		System.out.println("rev:" + taskInitiator);
//		System.out.println("getActivityInstanceId(): " + execution.getActivityInstanceId());
//		System.out.println("getParentActivityInstanceId(): " + execution.getParentActivityInstanceId());
//		System.out.println("getCurrentActivityId: " + 		execution.getCurrentActivityId());
//		System.out.println("getParentId(): " + execution.getParentId());
//		System.out.println("execution.getId(): " + execution.getId());
//
//		
//		System.out.println("<<<<<<<<<<<<<< MF init listenet <<<<<<<<<<<<<<.");

		
		
		
		
		ReviewingDto reviewingDto = new ReviewingDto();
		ReviewArticleMfDto reviewingMfDto = new ReviewArticleMfDto(taskInitiator, "", activityInstanceId,"","","", reviewingDto);

		VariableMap initLocalVariablesMap = Variables.createVariables()
                .putValue("subProcessData", reviewingMfDto);
		
		execution.setVariablesLocal(initLocalVariablesMap);

	}

}
