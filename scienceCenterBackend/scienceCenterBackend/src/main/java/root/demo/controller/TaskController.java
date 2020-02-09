package root.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import root.demo.model.TaskDto;
import root.demo.model.TaskDto2;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.Role;
import root.demo.model.repo.RoleName;
import root.demo.repository.UserRepository;
import root.demo.services.jwt.UserDetailsServiceImpl;
import root.demo.util.TaskUrlEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private TaskUrlEndpoint taskUrlEndpoint;
	
	@Autowired
	private UserDetailsServiceImpl userDetailService;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping(path = "/assignedToUser/{userId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto2>> get(@PathVariable Long userId) {
		
		
		MyUser logged = userRepo.getOne(userId);
//		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		System.out.println(obj.toString());
		
		
//		MyUser loggedUser = userDetailService.getLoggedUser();
//		if(loggedUser == null ) {
//			
//			return null;
//		}
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(logged.getUsername()).active().list();
		
		if(logged.getRole().getName() == RoleName.ROLE_ADMIN) {
			tasks = taskService.createTaskQuery().active().list();
		}
//		List<Task> tasks = taskService.createTaskQuery().active().list();

		
		List<TaskDto2> dtos = new ArrayList<TaskDto2>();
		for (Task task : tasks) {
			String taskDefinitionKey = task.getTaskDefinitionKey();
			// String url = taskUrlEndpoint.getValue(task.getTaskDefinitionKey());
			String[] params = taskUrlEndpoint.getParams(taskDefinitionKey);
			String url = params[0];
			String param = "";
			if(params.length > 1) {
				param = task.getId();
			}
			if(url == null) {};
			Date d = task.getCreateTime();
			
			TaskDto2 t = new TaskDto2(task.getId(), task.getName(), task.getAssignee(), task.getDescription(), taskDefinitionKey, task.getProcessInstanceId(), url, param, d.toString());
			dtos.add(t);			
		}
		
        return new ResponseEntity<List<TaskDto2>> (dtos,  HttpStatus.OK);
    }
	
	@GetMapping(path = "/removeTask/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable String taskId) throws URISyntaxException {
		

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		RestTemplate restTemplate = new RestTemplate();
		
		String uriBasic = "http://localhost:8080/rest/process-instance/{id}";
		URI uri = new URI(uriBasic.replace("{id}", processInstanceId));
		restTemplate.delete(uri);
		
		
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
