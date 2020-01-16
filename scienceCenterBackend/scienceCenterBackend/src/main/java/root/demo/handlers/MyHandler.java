package root.demo.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables.SerializationDataFormats;
import org.camunda.bpm.engine.variable.type.SerializableValueType;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.repo.ScienceArea;
import root.demo.repository.ScienceAreaRepository;

@Service
public class MyHandler implements TaskListener{

	@Autowired
	ScienceAreaRepository repo;
	
	
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
		 System.out.println("Kreiran prvi task");
		  //List<User> users = identityService.createUserQuery().userIdIn("pera", "mika", "zika").list();
		
		  List<ScienceArea> no = repo.findAll();
		  
		  Map<String, String> naucneOblasti = new HashMap<String, String>();
	
		  int i = 0;
		  for (ScienceArea scienceArea : no) {
			naucneOblasti.put(Integer.toString(i), scienceArea.getScienceAreaName());
			i++;
		  }
		  
		  
		  DelegateExecution execution = delegateTask.getExecution();
		  
		  
		  //execution.setVariables("AVAILABLE_PRODUCT_TYPES",naucneOblasti);
		  
		/*  execution.setVariable("AVAILABLE_PRODUCT_TYPES",  
		    objectValue(naucneOblasti)
		      .serializationDataFormat(SerializationDataFormats.JSON)
		      .create());*/
		  
		  
		
	}

	/*private void ObjectValue(Map<String, String> naucneOblasti) {
		// TODO Auto-generated method stub
		
	}*/

}
