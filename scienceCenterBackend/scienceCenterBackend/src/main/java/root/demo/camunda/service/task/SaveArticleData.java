package root.demo.camunda.service.task;


import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.xml.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import camundafeel.de.odysseus.el.tree.impl.Parser.ParseException;
import root.demo.model.ArticleProcessDto;
import root.demo.model.FormSubmissionDto;
import root.demo.model.FormSubmissionDto2;
import root.demo.model.NewArticleResponseDto;
import root.demo.model.TermDto;
import root.demo.model.UserDto;
import root.demo.model.repo.Article;
import root.demo.model.repo.ArticleStatus;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MagazineEdition;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.OpinionAboutArticle;
import root.demo.model.repo.Role;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.repository.ArticleRepository;
import root.demo.repository.MagazineEditionRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.RoleRepository;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.TermRepository;
import root.demo.repository.UserRepository;
import root.demo.util.Base64Utility;


@Service
public class SaveArticleData implements JavaDelegate {

	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private MagazineEditionRepository magazineEditionRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TermRepository termRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private ScienceAreaRepository scienceRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		//ovde ce mozda puci prilikom kastovanja u newArticleDto
		//trebace se izmeniti newArticleResponseDto
		
		NewArticleResponseDto newArticleDto = new NewArticleResponseDto(); //(NewArticleResponseDto) execution.getVariable("newArticleDto");
		
		List<FormSubmissionDto2> newArticle = (List<FormSubmissionDto2>)execution.getVariable("newArticleDto");
		
		for (FormSubmissionDto2 formSubmissionDto2 : newArticle) {
			
			if(formSubmissionDto2.getFieldId().equals("naslov")) {
				newArticleDto.setArticleTitle((String)formSubmissionDto2.getFieldValue());
				continue;
			}
			if(formSubmissionDto2.getFieldId().equals("apstrakt")) {
				newArticleDto.setArticleAbstract((String)formSubmissionDto2.getFieldValue());
				continue;
			}
			if(formSubmissionDto2.getFieldId().equals("naucnaOblast")) {
				newArticleDto.setArticleScienceArea((String)formSubmissionDto2.getFieldValue());
				continue;
			}
			if(formSubmissionDto2.getFieldId().equals("file_choose")) {
				newArticleDto.setFile((String)formSubmissionDto2.getFieldValue());
				continue;
			}
			if(formSubmissionDto2.getFieldId().equals("cena")) {
					Integer ii = (Integer) formSubmissionDto2.getFieldValue();
					String s = Integer.toString(ii);
					Float f = Float.parseFloat(s);
					newArticleDto.setArticlePrice(f);
					continue;

			}
			
			if(formSubmissionDto2.getFieldId().equals("koautor")) {
				List<UserDto> coauthors = new ArrayList<UserDto>();
				List<UserDto> newcoauthors = new ArrayList<UserDto>();
				coauthors = (List<UserDto>)formSubmissionDto2.getFieldValue();
				
				for (Object obj : coauthors) {
					UserDto tdto = new UserDto();
					
					System.out.println("WTF: " + coauthors.toString());
					String trim = obj.toString().replace("{", "");
					String trim2 = trim.replace("}", "");
					String[] parts = trim2.toString().split(",");
					//Character c = parts[0].charAt(parts[0].length());
					//Integer termId = Integer.parseInt(String.valueOf(c));
					
					for (String p : parts) {
						String[] part = p.split("=");
						if(part[0].equals("userId")) {
							tdto.setUserId(Long.parseLong(part[1]));
						} else if(part[0].equals(" firstName")) {
							tdto.setFirstName(part[1]);
						} else if(part[0].equals(" lastName")) {
							tdto.setLastName(part[1]);
						} else if(part[0].equals(" email")) {
							tdto.setEmail(part[1]);
						} else if(part[0].equals(" city")) {
							tdto.setCity(part[1]);
						} else if(part[0].equals(" country")) {
							tdto.setCountry(part[1]);
						} else if(part[0].equals(" userUsername")) {
							tdto.setUserUsername(part[1]);
						} else if(part[0].equals(" vocation")) {
							tdto.setVocation(part[1]);
						}
					}

					
					newcoauthors.add(tdto);
					
					
				}
				
				
				newArticleDto.setArticleCoAuthors(newcoauthors);
				continue;
			}
			if(formSubmissionDto2.getFieldId().equals("kljucniPojmovi")) {
				List<TermDto> terms = new ArrayList<TermDto>();
				List<TermDto> newterms = new ArrayList<TermDto>();
				terms = (List<TermDto>)formSubmissionDto2.getFieldValue();
				
				for (Object obj : terms) {
					System.out.println("WTF: " + obj.toString());
					String[] parts = obj.toString().split(",");
					//Character c = parts[0].charAt(parts[0].length());
					//Integer termId = Integer.parseInt(String.valueOf(c));
					
					String[] partsId = parts[0].split("=");
					String id = partsId[1];
					
					Integer termId2 = Integer.parseInt(id);
					
					String[] partsName = parts[1].split("=");
					
					String name = partsName[1].substring(0, partsName[1].length()-1);
					
					String termName = parts[1].substring(parts[1].length()-1, parts[1].length());
					
					TermDto tdto = new TermDto();
					tdto.setTermId(Long.valueOf(termId2));
					tdto.setTermName(name);
					
					newterms.add(tdto);
					
					
				}
				
				newArticleDto.setArticleTerm(newterms);
				continue;
			}
	
		}
		
		
		//ovde smo stali sa debagovanjem, zavrsiti danas.. nadam se!
		Long magazineId = (Long) execution.getVariable("select_magazine_id");
		Magazine magazine = magRepo.getOne(magazineId);
		
		MagazineEdition latestEdition = magazineEditionRepo.findByMagazineOrderByPublishingDateDesc(magazine).stream().findFirst().orElse(null);
		if (latestEdition == null) { }
		
		//ovde ce puci jer nemamo nigde varijablu user
		Long userUsername = (Long) execution.getVariable("user");
		MyUser author = userRepo.getOne(userUsername);
		
		Long scienceId = Long.parseLong(newArticleDto.getArticleScienceArea());
		ScienceArea scienceArea = scienceRepo.getOne(scienceId);
		
		//ovde razvrstavamo koje su stvarne, a koje custom!
		//VAZNO!! ZA SAD RADI SAMO ZA DODAVANJE KOJE POSTOJE U BAZI!
		List<TermDto> addedTermsDto = newArticleDto.getArticleTerm();
		List<Long> familiarTermsDto = new ArrayList<Long>();
		//List<TermDto> newAddedTermsDto = new ArrayList<TermDto>();
		
		/*for (Object obj : newArticleDto.getArticleTerm()) {
			//familiarTermsDto.add(obj);
		}*/
		
       // List<Long> familiarTermsDto	= addedTermsDto.stream().filter(tDto -> tDto.getTermId() != null).map(TermDto::getTermId).collect(Collectors.toList());
        
        for (TermDto obj : addedTermsDto) {
        	familiarTermsDto.add(obj.getTermId());
		}
        
        //List<TermDto> newAddedTermsDto	= addedTermsDto.stream().filter(tDto -> tDto.getTermId() == null).collect(Collectors.toList());

		Set<Term> familiarTerms = termRepo.findAllById(familiarTermsDto).stream().collect(Collectors.toSet());
       
		//OVDE CE biti dodavanje novih pojmova u bazu
		/*
		List<Term> newAddedTerms = new ArrayList<Term>();
		newAddedTermsDto.stream().forEach(tDto -> {
			Term t = new Term();
			t.setTermName(tDto.getTermName());
			newAddedTerms.add(t);
		});*/
		
		//ovde bismo trebali kao da dodajemo nove ali NECEMO!!!
		/*List<Term> newAddedTerms = new ArrayList<Term>();
		for (TermDto termDto : addedTermsDto) {
			Term t = new Term();
			t.setTermName(termDto.getTermName());
			newAddedTerms.add(t);
		}
		
		Set<Term> persistedNewAddedTerms = termRepo.saveAll(newAddedTerms).stream().collect(Collectors.toSet());
		familiarTerms.addAll(persistedNewAddedTerms);*/
		//ovde ce puci jer link hashmap zeza!
		List<UserDto> coauthorsDto = newArticleDto.getArticleCoAuthors();
		List<Long> familiarCoAuthors = coauthorsDto.stream().filter(uDto -> uDto.getUserId() != null).map(UserDto::getUserId).collect(Collectors.toList());
		List<UserDto> newAddedCoauthorsDto = coauthorsDto.stream().filter(uDto -> uDto.getUserId() == null).collect(Collectors.toList());
		
		Set<MyUser> familiarCoauthors = userRepo.findAllById(familiarCoAuthors).stream().collect(Collectors.toSet());
		
		Role role = roleRepo.getOne(5l);
		
		
		List<MyUser> newAddedCoauthors = new ArrayList<MyUser>();
		newAddedCoauthorsDto.stream().forEach(cDto -> {
			MyUser u = MyUser.builder()
									   .activate(true)
									   .firstName(cDto.getFirstName())
									   .lastName(cDto.getLastName())
									   .mail(cDto.getEmail())
									   .city(cDto.getCity())
									   .country(cDto.getCountry())
									   .role(role)
									   .username(cDto.getFirstName() + cDto.getLastName())
									   .build();
			newAddedCoauthors.add(u);
		});
		
		Set<MyUser> persistedNewAddedCoauthors = userRepo.saveAll(newAddedCoauthors).stream().collect(Collectors.toSet());
		familiarCoauthors.addAll(persistedNewAddedCoauthors);
		
		//ovde bi trebalo splitovati po '.'
		String[] fileParts = newArticleDto.getFile().split(",");
		byte[] decodedByte = Base64Utility.decode(fileParts[1]);
		String fileFormat = fileParts[0];
		
		Article article = Article.builder()
								.articleTitle(newArticleDto.getArticleTitle())
								.articleAbstract(newArticleDto.getArticleAbstract())
								.articlePrice(newArticleDto.getArticlePrice())
								//.file(newArticleDto.getFile())   //value to long String(255)
								.fileFormat(fileFormat)
								.file(decodedByte)
								.magazineEdition(latestEdition)
								.author(author)
								.scienceArea(scienceArea)
								.keyTerms(familiarTerms)
								.coAuthors(familiarCoauthors)
								.publishingDate(new Date())
								.status(ArticleStatus.SUBMITTED)
								.build();
		
		ArticleProcessDto articleProcessIfUpdate = (ArticleProcessDto) execution.getVariable("articleProcessDto");
		
		if(articleProcessIfUpdate != null) {
			article.setArticleId(articleProcessIfUpdate.getArticleId());
			articleRepo.save(article);
		} else {
			Article persistedArticle = articleRepo.save(article);
			
			//puci ce, jer nema authos.getUsername, srediti kad se vratis sa training-a!!!
			ArticleProcessDto articleProcessDto = new ArticleProcessDto(execution.getProcessInstanceId(), 
					magazineId, persistedArticle.getArticleId(), author.getUsername(), "", "",
					new ArrayList<String>(), new ArrayList<OpinionAboutArticle>(), new ArrayList<OpinionAboutArticle>() , new ArrayList<OpinionAboutArticle>(), 1);
			
			
			
			execution.setVariable("articleProcessDto", articleProcessDto);
		}
		
		
	}

}

