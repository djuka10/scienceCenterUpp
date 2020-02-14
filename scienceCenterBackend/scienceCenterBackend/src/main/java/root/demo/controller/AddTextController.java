package root.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



import root.demo.model.ArticleDto;
import root.demo.model.ArticleProcessDto;
import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.FormSubmissionDto2;
import root.demo.model.NewArticleRequestDto;
import root.demo.model.NewArticleResponseDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.TaskDto;
import root.demo.model.TermDto;
import root.demo.model.UpdateArticleChangesDto;
import root.demo.model.UpdateArticleDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.UserRepository;
import root.demo.util.Base64Utility;

@RestController
@RequestMapping("/addText")
@CrossOrigin
public class AddTextController {

	
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
	
	@Autowired
	private MagazineRepository magazineRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@GetMapping(path = "/start/{magazineId}/{userId}", produces = "application/json")
    public @ResponseBody ResponseEntity<NewArticleRequestDto> get(@PathVariable Long magazineId,
    		@PathVariable Long userId) {
		//FormFieldsDto
		// TODO debagovati ovde
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_obrade_teksta");
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		String proccessInstanceId = task.getProcessInstanceId();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("select_magazine_id", magazineId);
		
		Magazine m = magazineRepo.getOne(magazineId);
		System.out.println("M: " + m.getName());
		
		runtimeService.setVariable(proccessInstanceId, "user", userId);
		//runtimeService.setVariable(proccessInstanceId, "select_magazine_id", magazineId);
		
		formService.submitTaskForm(task.getId(), map);
		
	/*	*/
		
		
		NewArticleRequestDto requestDto = (NewArticleRequestDto) runtimeService.getVariable(proccessInstanceId, "newArticleRequestDto");
		runtimeService.removeVariable(proccessInstanceId, "newArticleRequestDto");
		
		//Task task2 = taskService.createTaskQuery().processInstanceId(proccessInstanceId).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(requestDto.getTaskId());
		
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			//int i = 0;
//			System.out.println(fp.getId() + fp.getType());
//			if(fp.getId().equals("naucnaOblast")) {
//				EnumFormType eft = (EnumFormType) fp.getType();
//				Map<String, String> mapa = eft.getValues();
//				for(ScienceAreaDto sa : requestDto.getArticleScienceAreas()) {
//					mapa.put(Integer.toString(i), Long.toBinaryString(sa.getScienceAreaId()));
//				}
//			}
			requestDto.getFormFields().add(fp);
			
		}
		
		
		System.out.println("STOP");
		
		return new ResponseEntity<NewArticleRequestDto>(requestDto,HttpStatus.OK);
       // return new FormFieldsDto(task2.getId(), pi.getId(),properties);
    }	
	
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
	
	@PostMapping(path = "/postArticle/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto2> dto, @PathVariable String taskId) throws IOException {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
//		Decoder decoder = Base64.getDecoder();
//        byte[] decodedByte = decoder.decode(dto.getFile().split(",")[1]);
//        FileOutputStream fos = new FileOutputStream("MyAudio.webm");
//        fos.write(decodedByte);
//        fos.close();
		
		String processInstanceId = task.getProcessInstanceId();
		// formService.submitTaskForm(taskId, properties);
		runtimeService.setVariable(processInstanceId, "newArticleDto", dto);
		String file = "fajl";
		String f = (String) map.get("file_choose");
		map.put("file_choose", file);
		formService.submitTaskForm(taskId, new HashMap<String,Object>());
//		taskService.complete(taskId);

		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/preanalizeBasic/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> getPreAnalizeBasic(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		//ovde mozemo iz baze izvuci by the way
		
		ArticleDto pero = (ArticleDto) runtimeService.getVariable(proccessInstanceId, "perodeformero");
		//Article a = articleRepo.
		
		ArticleDto requestDto = (ArticleDto) runtimeService.getVariable(proccessInstanceId, "articleRequestDto");
		// runtimeService.removeVariable(proccessInstanceId, "articleRequestDto");
		
		
		//kopirano iz NewArticleAnalizeDataInit jer nesto nije htelo
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) runtimeService.getVariable(proccessInstanceId,"articleProcessDto");
		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		
		ScienceArea sc = article.getScienceArea();
		ScienceAreaDto scienceAreaDto = new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode());
		
		MyUser author = article.getAuthor();
		UserDto authorDto = new UserDto(author.getId(), 
				 author.getFirstName(), author.getLastName(), 
				 author.getMail(), author.getCity(), 
				 author.getCountry(), author.getUsername(), author.getTitle());
		
		Set<MyUser> coAuthors = article.getCoAuthors();
		List<UserDto> coAuthorsDto = new ArrayList<UserDto>();
		coAuthors.stream().forEach(c -> {
			coAuthorsDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle()));
		});
		
		Set<Term> terms = article.getKeyTerms();		
		List<TermDto> termsDto = new ArrayList<TermDto>();
		terms.stream().forEach(t -> {
			termsDto.add( new TermDto(t.getTermId(), t.getTermName()));
		});
		
		ArticleDto articleDto = new ArticleDto(task.getId(), 
				task.getProcessInstanceId(), 
				article.getArticleId(), 
				article.getArticleTitle(), 
				article.getArticleAbstract(), 
				scienceAreaDto, 
				article.getPublishingDate(), 
				authorDto, 
				coAuthorsDto, 
				termsDto, 
				article.getArticlePrice(), null, "");
		
        return new ResponseEntity<ArticleDto>(articleDto, HttpStatus.OK);
    }
	
	@GetMapping(path = "/analizeBasic/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> getAnalizeBasic(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		//ovde mozemo iz baze izvuci by the way
		
		//Article a = articleRepo.
		
		//ArticleDto requestDto = (ArticleDto) runtimeService.getVariable(proccessInstanceId, "articleRequestDto");
		// runtimeService.removeVariable(proccessInstanceId, "articleRequestDto");
		
		ProcessInstance processInstance1 =  runtimeService.createProcessInstanceQuery().processInstanceId(proccessInstanceId).singleResult();
		Task taskAnalizeText = null;
		try {
			taskAnalizeText = taskService.createTaskQuery().processInstanceId(proccessInstanceId).list().get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			
			System.out.println("bacaj ga nekompatabilnost");
    		//return ResponseEntity.status(error.getStatus()).body(new Response(HttpStatus.));
    		return new ResponseEntity<>(HttpStatus.OK);

		}

		
		ArticleProcessDto requestDto = (ArticleProcessDto) runtimeService.getVariable(proccessInstanceId, "articleProcessDto");
		Long longId = requestDto.getArticleId();
		Article article = articleRepo.getOne(longId);	
		
		String document = Base64Utility.encode(article.getFile());
		
		
		ArticleDto articleDto = new ArticleDto();
		articleDto.setTaskId(taskAnalizeText.getId());
		articleDto.setProcessInstanceId(proccessInstanceId);
		articleDto.setFile(document);
		
        return new ResponseEntity<ArticleDto>(articleDto, HttpStatus.OK);
    }
	
	@GetMapping(path = "/pdf/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> getPdf(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		//ovde mozemo iz baze izvuci by the way
		
		//Article a = articleRepo.
		
		//ArticleDto requestDto = (ArticleDto) runtimeService.getVariable(proccessInstanceId, "articleRequestDto");
		// runtimeService.removeVariable(proccessInstanceId, "articleRequestDto");
		
		ProcessInstance processInstance1 =  runtimeService.createProcessInstanceQuery().processInstanceId(proccessInstanceId).singleResult();
		Task taskAnalizeText = null;
		try {
			taskAnalizeText = taskService.createTaskQuery().processInstanceId(proccessInstanceId).list().get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			
			//System.out.println("bacaj ga nekompatabilnost");
    		//return ResponseEntity.status(error.getStatus()).body(new Response(HttpStatus.));
    		return new ResponseEntity<>(HttpStatus.OK);

		}

		
		ArticleProcessDto requestDto = (ArticleProcessDto) runtimeService.getVariable(proccessInstanceId, "articleProcessDto");
		Long longId = requestDto.getArticleId();
		Article article = articleRepo.getOne(longId);	
		
		String document = Base64Utility.encode(article.getFile());
		
		
		ArticleDto articleDto = new ArticleDto();
		articleDto.setTaskId(taskAnalizeText.getId());
		articleDto.setProcessInstanceId(proccessInstanceId);
		articleDto.setFile(document);
		
		
		
        return new ResponseEntity<ArticleDto>(articleDto, HttpStatus.OK);
    }
	
	@PostMapping(path = "/post/basicAnalize/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postMainData(@RequestBody List<FormSubmissionDto2> dto, @PathVariable String taskId) {
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
		runtimeService.setVariable(processInstanceId, "daLiSePrihvata", dto);
		//runtimeService.setVariable(processInstanceId, "analize_article_comment", map.get("komentar"));
		
		
		
		
		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(path = "/post/timeForReviewing/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity postTime(@RequestBody List<FormSubmissionDto2> dto, @PathVariable String taskId) {
		HashMap<String, Object> map = this.mapListToDto(dto);
				
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();

		formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/updateArticleStart2/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity updateArticleStart2(@PathVariable String taskId) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path = "/updateArticleStart/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<UpdateArticleDto> updateArticleStart(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
					
		UpdateArticleDto requestDto = (UpdateArticleDto) runtimeService.getVariable(proccessInstanceId, "updateArticleRequestDto");
		//runtimeService.removeVariable(proccessInstanceId, "updateArticleRequestDto");
		
		TaskFormData tfd = formService.getTaskFormData(requestDto.getTaskId());
		
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
			requestDto.getFormFields().add(fp);
		}
		
		
		System.out.println("STOP");
		
        return new ResponseEntity<UpdateArticleDto>(requestDto, HttpStatus.OK);
    }	
	
	@PutMapping(path = "/updateArticle/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity updateArticle(@RequestBody List<FormSubmissionDto2> dto, @PathVariable String taskId) throws IOException {
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
//		Decoder decoder = Base64.getDecoder();
//        byte[] decodedByte = decoder.decode(dto.getFile().split(",")[1]);
//        FileOutputStream fos = new FileOutputStream("MyAudio.webm");
//        fos.write(decodedByte);
//        fos.close();
		
		String processInstanceId = task.getProcessInstanceId();
		// formService.submitTaskForm(taskId, properties);
		runtimeService.setVariable(processInstanceId, "newArticleDto", dto);
		formService.submitTaskForm(taskId, new HashMap<String, Object>());
//		taskService.complete(taskId);

		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path = "/updateArticleChangesStart/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<UpdateArticleChangesDto> updateArticleChangesStart(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
					
		UpdateArticleChangesDto requestDto = (UpdateArticleChangesDto) runtimeService.getVariable(proccessInstanceId, "updateArticleChangesDto");
		runtimeService.removeVariable(proccessInstanceId, "updateArticleChangesDto");
		
		TaskFormData tfd = formService.getTaskFormData(requestDto.getTaskId());
		
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
			requestDto.getFormFields().add(fp);
		}
		
		
		System.out.println("STOP");
		
        return new ResponseEntity<UpdateArticleChangesDto>(requestDto, HttpStatus.OK);
    }	
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto2> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto2 temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
	
	
}
