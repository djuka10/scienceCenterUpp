package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.cmd.GetDeploymentResourceNamesCmd;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.TaskDto;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.RoleName;
import root.demo.model.repo.ScienceArea;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.UserRepository;

@Controller
@RequestMapping("/welcome")
public class DummyController {
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	ScienceAreaRepository scienceRepo;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@GetMapping(path = "/get/{process}", produces = "application/json")
    public @ResponseBody FormFieldsDto getProcess(@PathVariable String process) {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		//ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_1");
		//System.out.println("param: " + process);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(process);

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }
	/*
	 * Metoda za dobavljanje drugog taska za unos naucnih oblasti
	 * 
	 * */
	@GetMapping(path = "/get/userTask/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getUserTask(@PathVariable String taskId) {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		//ProcessInstance pi = runtimeService.startProcessInstanceByKey("Process_1");
		//ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_registracije");

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		System.out.println("PIID: " + processInstanceId);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		if(task.getName().equals("Unos naučnih oblasti")) {
			  List<ScienceArea> no = scienceRepo.findAll();
			  
		
			  int i = 0;
			  /*for (ScienceArea scienceArea : no) {
				naucneOblasti.put(Integer.toString(i), scienceArea.getScienceAreaName());
				i++;
			  }*/
			  
			  for (FormField p : properties) {
				if(p.getId().equals("naucnaOblast")) {
					 EnumFormType eft = (EnumFormType) p.getType();
					 Map<String, String> map = eft.getValues();
					 for (ScienceArea scienceArea : no) {
						 map.put(Integer.toString(i), scienceArea.getScienceAreaName());
						 i++;
					 }
					 break;
				}
			  }
			  
			  return new FormFieldsDto(task.getId(), processInstanceId, properties);
		} else if(task.getName().equals("Dodavanje 2 urednika i 2 recenzenta")) {
			//ovde ce ici logika za ubacivanje enuma za urednike i recenzente
			 List<MyUser> no = repo.findAll();
			 int i = 0;
			 
			 
			  
			 
			 for (FormField p : properties) {
				 
			 }
			 
			 for (FormField p : properties) {
				 
				if(p.getId().equals("dodavanjeUrednika1")) {	 
					EnumFormType eft = (EnumFormType) p.getType(); 
					Map<String, String> map = eft.getValues();
					 for (MyUser user : no) {
						 if(user.getRole().getName().equals(RoleName.ROLE_EDITOR)) {
							 map.put(Integer.toString(i) + user.getMail(), user.getFirstName() + " " + user.getLastName());
							 i++;
						 }
					 }
					 //break;
				} else if(p.getId().equals("dodavanjeUrednika2")) {
					EnumFormType eft = (EnumFormType) p.getType(); 
					Map<String, String> map = eft.getValues();
					 for (MyUser user : no) {
						 if(user.getRole().getName().equals(RoleName.ROLE_EDITOR)) {
							 map.put(Integer.toString(i) + user.getMail(), user.getFirstName() + " " + user.getLastName());
							 i++;
						 }
					 }
				} else if(p.getId().equals("dodajRecenzenta1")) {
					EnumFormType eft = (EnumFormType) p.getType();
					 Map<String, String> map = eft.getValues();
					 for (MyUser user : no) {
						 if(user.getRole().getName().equals(RoleName.ROLE_REVIEWER)) {
							 map.put(Integer.toString(i) + user.getMail(), user.getFirstName() + " " + user.getLastName());
							 i++;
						 }
					 }
				} else {
					EnumFormType eft = (EnumFormType) p.getType();
					 Map<String, String> map = eft.getValues();
					 for (MyUser user : no) {
						 if(user.getRole().getName().equals(RoleName.ROLE_REVIEWER)) {
							 map.put(Integer.toString(i) + user.getMail(), user.getFirstName() + " " + user.getLastName());
							 i++;
						 }
					 }
				}
			}
			 
			 return new FormFieldsDto(task.getId(), processInstanceId, properties);
		} else {
			
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		}
		
		
		
        return new FormFieldsDto(task.getId(), processInstanceId, properties);
    }
	
	@GetMapping(path = "/get/tasks/{processInstanceId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> get(@PathVariable String processInstanceId) {
		
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
		
        return new ResponseEntity(dtos,  HttpStatus.OK);
    }
	
	/*
	 * Unos osnovnih podataka
	 * */
	@PostMapping(path = "/post/mainData/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postMainData(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", dto);
		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	/*
	 * Unos naucnih oblasti
	 * */
	@PostMapping(path = "/post/sciencearea/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postScienceArea(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "sciencearea", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	/*
	 * Unos osnovnih podataka za dodavanje novog magazina
	 * userMail => korisnik koji je pokrenuo proces, inace je on sigurno urednik
	 * Ovo nam treba kako bi kasnije njega postavili za glavnog urednika ako dodje do toga
	 * */
	@PostMapping(path = "/post/mainMagazineData/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Object> postMainMagazineData(@RequestBody List<FormSubmissionDto> dto, 
    		@PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "magazine", dto);
		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/post/addEditorReviewer/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postaddEditorReviewer(@RequestBody List<FormSubmissionDto> dto, 
    		@PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "editorReviewer", dto);
		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/post/confirmMagazine/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postconfirmMagazine(@RequestBody List<FormSubmissionDto> dto, 
    		@PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "confirmMagazine", dto);
		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
	
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		    // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);
		
//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
		
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, "registration", dto);
		formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/tasks/claim/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity claim(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(taskId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/tasks/complete/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<List<TaskDto>> complete(@PathVariable String taskId) {
		Task taskTemp = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.complete(taskId);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(taskTemp.getProcessInstanceId()).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			TaskDto t = new TaskDto(task.getId(), task.getName(), task.getAssignee());
			dtos.add(t);
		}
        return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
    }
	
	
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
}
