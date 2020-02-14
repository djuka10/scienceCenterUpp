package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import root.demo.model.AddReviewersDto;
import root.demo.model.ArticleDto;
import root.demo.model.ArticleProcessDto;
import root.demo.model.EditorReviewerByScienceAreaDto;
import root.demo.model.FormSubmissionDto2;
import root.demo.model.MagazineDto;
import root.demo.model.ReviewArticleMfDto;
import root.demo.model.ReviewingDto;
import root.demo.model.ReviewingEditorDto;
import root.demo.model.ScienceAreaDto;
import root.demo.model.TermDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.ArticleStatus;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.OpinionAboutArticle;
import root.demo.model.repo.ReviewingType;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;
import root.demo.util.Base64Utility;

@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private MagazineRepository magazineRepo;
	
	@Autowired
	private ArticleRepository articleRepo;

	
	@GetMapping(path = "/addReviewer/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<AddReviewersDto> get(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		AddReviewersDto requestDto = (AddReviewersDto) runtimeService.getVariable(proccessInstanceId, "addReviewersDto");
		// runtimeService.removeVariable(proccessInstanceId, "addReviewersDto");
		
		TaskFormData tfd = formService.getTaskFormData(requestDto.getTaskId());
		
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
			requestDto.getFormFields().add(fp);
		}
		
		
		System.out.println("STOP");
		
		
        return new ResponseEntity<AddReviewersDto>(requestDto, HttpStatus.OK);
    }
	
	@PostMapping(path = "/addReviewer/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> analizeTextResult(@PathVariable String taskId, @RequestBody List<String> response) {
		HashMap<String, Object> map = new HashMap<>();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String proccessInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance =  runtimeService.createProcessInstanceQuery().processInstanceId(proccessInstanceId).singleResult();
		
		//u reviewers ce biti id-ovi editora by science area
		runtimeService.setVariable(proccessInstanceId, "reviewers", response);
		
		taskService.complete(task.getId());
		//otkomentarisati submitTaskForm da vidimo kako se ponasa
		//formService.submitTaskForm(task.getId(),map);
		

		
        return new ResponseEntity<ArticleDto>(new ArticleDto(), HttpStatus.OK);
    }
	
	@GetMapping(path = "/start/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity<ReviewingDto> review (@PathVariable String taskId){
		
		ReviewingDto dto = createReviewingDto(taskId);
		
		return new ResponseEntity<ReviewingDto>(dto, HttpStatus.OK);
	}
	
	private ReviewingDto createReviewingDto(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String proccessInstanceId = task.getProcessInstanceId();
		
		ArticleProcessDto articleProcessDto = (ArticleProcessDto) runtimeService.getVariable(proccessInstanceId, "articleProcessDto");

		Article article = articleRepo.getOne(articleProcessDto.getArticleId());
		Magazine magazine = magazineRepo.getOne(articleProcessDto.getMagazineId());
		MyUser author = userRepo.findByusername(articleProcessDto.getAuthor());
		Set<MyUser> coAuthors = article.getCoAuthors();
		List<UserDto> coAuthorsDto = new ArrayList<UserDto>();
		
		coAuthors.stream().forEach(c -> {
			coAuthorsDto.add(new UserDto(c.getId(), c.getFirstName(), c.getLastName(), c.getMail(), c.getCity(), c.getCountry(), c.getUsername(), c.getTitle()));
		});
		
		ScienceArea scienceArea = article.getScienceArea();
		ScienceAreaDto scienceAreaDto = new ScienceAreaDto(scienceArea.getScienceAreaId(), scienceArea.getScienceAreaName(), scienceArea.getScienceAreaCode());
		
		UserDto authorDto = new UserDto(author.getId(), author.getFirstName(), author.getLastName(), author.getMail(), author.getCity(), author.getCountry(), author.getUsername(), author.getTitle());
		
		Set<Term> terms = article.getKeyTerms();		
		List<TermDto> termsDto = new ArrayList<TermDto>();
		terms.stream().forEach(t -> {
			termsDto.add( new TermDto(t.getTermId(), t.getTermName()));
		});
		
		//Article article = articleRepo.getOne(article.getArticleId());
		
		String encoded = Base64Utility.encode(article.getFile());
		
		String document = encoded;

		ArticleDto articleDto = new ArticleDto(task.getId(), proccessInstanceId, article.getArticleId(), article.getArticleTitle(), article.getArticleAbstract(), 
				scienceAreaDto, article.getPublishingDate(), authorDto, coAuthorsDto, termsDto, article.getArticlePrice(), document,"");
		
		Set<ScienceArea> scienceAreas = magazine.getScienceAreas();
		List<ScienceAreaDto> scienceAreasDto = new ArrayList<ScienceAreaDto>();
		scienceAreas.stream().forEach(sc -> {
			scienceAreasDto.add(new ScienceAreaDto(sc.getScienceAreaId(), sc.getScienceAreaName(), sc.getScienceAreaCode()));
		});
		
		MagazineDto magazineDto = new MagazineDto(magazine.getMagazineId(), magazine.getUsername(), magazine.getName(), scienceAreasDto);
		
		VariableInstance user = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(proccessInstanceId)
                .variableName("subProcessData")
                .list()
                .stream()
                .filter(v -> ((ReviewArticleMfDto) v.getValue()).getTaskInitiator().equals(task.getAssignee())).findFirst().orElse(null);
		
		if(user == null) {}
		
		String personOpinionId = ((ReviewArticleMfDto)user.getValue()).getTaskInitiator();
		
		OpinionAboutArticle opinion = new OpinionAboutArticle(article.getArticleId(), personOpinionId, ReviewingType.REVIEWING, ArticleStatus.ACCEPTED, "", "","", articleProcessDto.getIteration());
		
		ReviewingDto reviewingDto = new ReviewingDto(articleDto, magazineDto, opinion, articleProcessDto.getAuthorsMessages(), true);
		
		
//		VariableInstance user = runtimeService.createVariableInstanceQuery()
//                .processInstanceIdIn(proccessInstanceId)
//                .variableName("task_initiator")
//                .list()
//                .stream()
//                .filter(v -> v.getValue().toString().equals(task.getAssignee())).findFirst().orElse(null);
//		String userId = user.getValue().toString();
//		
//		Set<String> subProcessExecutionIds = runtimeService.createVariableInstanceQuery()
//                .processInstanceIdIn(proccessInstanceId)
//                .variableName("task_initiator")
//                .list()
//                .stream()
//                .map(VariableInstance::getExecutionId)
//                .collect(Collectors.toSet());
		
		
		
		Set<String> subProcessExecutionIds = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(proccessInstanceId)
                .variableName("task_initiator")
                .list()
                .stream()
                .map(VariableInstance::getExecutionId)
                .collect(Collectors.toSet());
		
		return reviewingDto;
	}
	
	@PostMapping(path = "/reviewPost/{taskId}", produces = "application/json")
	public @ResponseBody ResponseEntity<ReviewingDto> reviewPost(@PathVariable String taskId, @RequestBody List<FormSubmissionDto2> requestBody){
		HashMap<String, Object> map = this.mapListToDto(requestBody);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		VariableInstance reviewVariableMf = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(proccessInstanceId)
                .variableName("subProcessData")
                .list()
                .stream()
                .filter(v -> ((ReviewArticleMfDto) v.getValue()).getTaskInitiator().equals(task.getAssignee())).findFirst().orElse(null);
		
		if(reviewVariableMf == null) {}
		
		ReviewArticleMfDto before = (ReviewArticleMfDto) reviewVariableMf.getValue();
		
		String subProcessExecutionId = reviewVariableMf.getExecutionId();
		
		ReviewArticleMfDto reviewArticleMfDto = (ReviewArticleMfDto) reviewVariableMf.getValue();
		//reviewArticleMfDto.setReviewerOpinion(requestBody);
		
		reviewArticleMfDto.setOpinion((String) map.get("komentar"));
		reviewArticleMfDto.setOpinionForEditors((String) map.get("komentarZaUrednike"));
		reviewArticleMfDto.setPreporuka((String) map.get("preporuka"));
		
		
		runtimeService.setVariable(subProcessExecutionId, "subProcessData", reviewArticleMfDto);
		
		taskService.complete(taskId);
		//formService.submitTaskForm(taskId, map);
		
		return new ResponseEntity<ReviewingDto>(new ReviewingDto(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/editorReview/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ReviewingEditorDto> editorReview(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		ReviewingEditorDto requestDto = (ReviewingEditorDto) runtimeService.getVariable(proccessInstanceId, "editorsReviewing");
		// runtimeService.removeVariable(proccessInstanceId, "addReviewersDto");
		
        return new ResponseEntity<ReviewingEditorDto>(requestDto, HttpStatus.OK);
    }
	
	@PostMapping(path = "/reviewByEditorPost/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ReviewingEditorDto> editorReviewPost(@PathVariable String taskId, @RequestBody List<FormSubmissionDto2> response) {
		HashMap<String, Object> map = this.mapListToDto(response);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String proccessInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance =  runtimeService.createProcessInstanceQuery().processInstanceId(proccessInstanceId).singleResult();
		
		runtimeService.setVariable(proccessInstanceId, "editorsReviewing", response);
		
		//Map<String, Object> properties = new HashMap<String, Object>();
		//properties.put("article_decision", response.getEditorOpinion().getOpinion().toString());
		formService.submitTaskForm(task.getId(), map);
		// taskService.complete(task.getId());


		
        return new ResponseEntity<ReviewingEditorDto>(new ReviewingEditorDto(), HttpStatus.OK);
    }
	
	@GetMapping(path = "/addReviewerWhenError/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<AddReviewersDto> addReviewerWhenError(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		AddReviewersDto requestDto = (AddReviewersDto) runtimeService.getVariable(proccessInstanceId, "addReviewersDto");
		// runtimeService.removeVariable(proccessInstanceId, "addReviewersDto");
		
        return new ResponseEntity<AddReviewersDto>(requestDto, HttpStatus.OK);
    }
	
	@PostMapping(path = "/addReviewerWhenError/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> addReviewerWhenErrorPost(@PathVariable String taskId, @RequestBody AddReviewersDto response) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String proccessInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance =  runtimeService.createProcessInstanceQuery().processInstanceId(proccessInstanceId).singleResult();
		
		VariableInstance reviewVariableMf = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(proccessInstanceId)
                .variableName("subProcessData")
                .list()
                .stream()
                .filter(v -> ((ReviewArticleMfDto) v.getValue()).getSubProcessExecutionId().equals(response.getSubProcessMfExecutionId())).findFirst().orElse(null);
		
		List<VariableInstance> reviewVariablseMf = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(proccessInstanceId)
                .variableName("subProcessData")
                .list();
		
		String subProcessExecutionId = reviewVariableMf.getExecutionId();
		
		ReviewArticleMfDto reviewArticleMfDto = (ReviewArticleMfDto) reviewVariableMf.getValue();
		reviewArticleMfDto.setTaskInitiator(response.getEditorsReviewersDto().get(0).getEditorReviewer().getUserUsername());
		
		runtimeService.setVariable(subProcessExecutionId, "subProcessData", reviewArticleMfDto);
		
		taskService.complete(taskId);
		
		
		// formService.submitTaskForm(task.getId(), properties);
		

		
        return new ResponseEntity<ArticleDto>(new ArticleDto(), HttpStatus.OK);
    }
	
	@PostMapping(path = "/addAdditionalReviewer/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArticleDto> addAdditionalReviewerPost(@PathVariable String taskId, @RequestBody AddReviewersDto response) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String proccessInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(proccessInstanceId, "addReviewersDto", response);
		// runtimeService.removeVariable(proccessInstanceId, "addReviewersDto");
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("additional_reviewer", response.getEditorsReviewersDto().get(0).getEditorReviewer().getUserUsername());
		formService.submitTaskForm(task.getId(), properties);
		
        return new ResponseEntity<ArticleDto>(new ArticleDto(), HttpStatus.OK);
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
