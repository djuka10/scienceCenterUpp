package root.demo.util;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



import root.demo.model.repo.EditorByScienceArea;
import root.demo.model.repo.EditorReviewerByScienceArea;
import root.demo.model.repo.Magazine;
import root.demo.model.repo.MagazineEdition;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.Role;
import root.demo.model.repo.RoleName;
import root.demo.model.repo.ScienceArea;
import root.demo.model.repo.Term;
import root.demo.model.repo.WayOfPayment;
import root.demo.repository.EditorReviewerByScienceAreaRepository;
import root.demo.repository.MagazineEditionRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.RoleRepository;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.TermRepository;
import root.demo.repository.UserRepository;


@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private ScienceAreaRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private TermRepository termRepo;
	
	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private MagazineEditionRepository magEdRepo;
	
	@Autowired
	private EditorReviewerByScienceAreaRepository editorReviewerRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		// createCardAccount();
		createScienceArea();
		createRoles();
		createUsers();
		
		initTermData();
		initMagazines();
		
	}
	

	private void createScienceArea() {
		/*ScienceArea sbi = new ScienceArea(1l,"NO1");
		ScienceArea sbi2 = new ScienceArea(2l,"NO2");
		ScienceArea sbi3 = new ScienceArea(3l,"NO3");
		repo.save(sbi);
		repo.save(sbi2);
		repo.save(sbi3);*/
		ScienceArea scArea1 = ScienceArea.builder().scienceAreaName("Computer Science").scienceAreaCode("300").build();
		ScienceArea scArea2 = ScienceArea.builder().scienceAreaName("Artificial Intelligence").scienceAreaCode("301").build();
		ScienceArea scArea3 = ScienceArea.builder().scienceAreaName("Computer Programming").scienceAreaCode("302").build();
		ScienceArea scArea4 = ScienceArea.builder().scienceAreaName("Information System").scienceAreaCode("308").build();
		ScienceArea scArea5 = ScienceArea.builder().scienceAreaName("Architecture").scienceAreaCode("120").build();
		ScienceArea scArea6 = ScienceArea.builder().scienceAreaName("Agriculture").scienceAreaCode("100").build();
		ScienceArea scArea7 = ScienceArea.builder().scienceAreaName("Business Managment").scienceAreaCode("200").build();
		ScienceArea scArea8 = ScienceArea.builder().scienceAreaName("Pulmonary diseases").scienceAreaCode("6021").build();

		repo.save(scArea1);
		repo.save(scArea2);
		repo.save(scArea3);
		repo.save(scArea4);
		repo.save(scArea5);
		repo.save(scArea6);
		repo.save(scArea7);
		repo.save(scArea8);
	}
	
	private void createRoles() {

	}
	
	private void createUsers() {
		Role adminRole = new Role(RoleName.ROLE_ADMIN);
		Role reviewerRole = new Role(RoleName.ROLE_REVIEWER);
		Role editorRole = new Role(RoleName.ROLE_EDITOR);
		Role chRole = new Role(RoleName.ROLE_CHIEF_EDITOR);
		Role basic = new Role(RoleName.ROLE_USER);

		MyUser admin = MyUser.builder()
				.firstName("Adam")
				.lastName("Petrovic")
				.city("Novi Saf")
				.country("Srbija")
				.id(1l)
				.username("admin")
				.password("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra")
				.mail("vikac@gmail.com")
				.title("Dipling")
				.reviewer(false)
				.activate(true)
				.role(adminRole)
				.build();
		
		MyUser editor = MyUser.builder()
				.firstName("Petar EDITOR")
				.lastName("Petrovic")
				.city("Novi Saf")
				.country("Srbija")
				.id(2l)
				.username("editor")
				.password("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra")
				.mail("vikac.parkic@gmail.com")
				.title("Dipling")
				.reviewer(false)
				.activate(true)
				.role(editorRole)
				.build();
		
		
		MyUser reviewer = MyUser.builder()
				.firstName("Petar REVIEWER")
				.lastName("Petrovic")
				.city("Novi Saf")
				.country("Srbija")
				.id(3l)
				.username("reviewer")
				.password("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra")
				.mail("parkic@gmail.com")
				.title("Dipling")
				.reviewer(true)
				.activate(true)
				.role(reviewerRole)
				.build();
		
		MyUser cheifEditor = MyUser.builder()
				.firstName("Petar CHEIF")
				.lastName("Petrovic")
				.city("Novi Saf")
				.country("Srbija")
				.id(4l)
				.username("cheif")
				.password("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra")
				.mail("vik@gmail.com")
				.title("Dipling")
				.reviewer(false)
				.activate(true)
				.role(chRole)
				.build();
		
		MyUser basicUser = MyUser.builder()
				.firstName("Vitkor CHEIF")
				.lastName("Djuka")
				.city("Novi Sad")
				.country("Srbija")
				.id(5l)
				.username("user")
				.password("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra")
				.mail("viktordjuka10@gmail.com")
				.title("Dipling")
				.reviewer(false)
				.activate(true)
				.role(basic)
				.build();
		
		
		
		
//		MyUser admin = new MyUser(1l, "admin","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","vikac@gmail.com","Petar","Petrovic","Novi Sad", "Srbija","Titula",false,true,adminRole);
//		MyUser editor = new MyUser(2l, "editor","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","vikac.parkic@gmail.com","Petar EDITOR","Petrovic","Novi Sad", "Srbija","Titula",false,true,editorRole);
//		MyUser reviewer = new MyUser(3l, "reviewer","reviewer","parkic@gmail.com","Petar REVIEWER","Petrovic","Novi Sad", "Srbija","Titula",true,true,reviewerRole);
//		MyUser cheifEditor = new MyUser(4l, "cheif","cheif","vik@gmail.com","Petar","Petrovic","Novi Sad", "Srbija","Titula",false,true,chRole);
//		MyUser basicUser = new MyUser(5l, "user","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","viktordjuka10@gmail.com","Petar","Petrovic","Novi Sad", "Srbija","Titula",false,true,basic);

		// TODO dodati ovde jos po dva editora i reviewer-a kako bismo mogli da testiramo njihovo dodavanje kao i dodavanje casopisa
		
		
//		MyUser editor2 = new MyUser(6l, "editor2","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","parkc@gmail.com","Marko","Petrovic","Novi Sad", "Srbija","Titula",false,true,editorRole);
//		MyUser reviewer2 = new MyUser(7l, "reviewer2","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","rkic@gmail.com","Nikola","Petrovic","Novi Sad", "Srbija","Titula",true,true,reviewerRole);

		//EditorByScienceArea e = new EditorByScienceArea(1l,);
		
		
		//dodavanje roli
		editor.getRoles().add(editorRole);
		//editor2.getRoles().add(editorRole);
		reviewer.getRoles().add(reviewerRole);
		//reviewer2.getRoles().add(reviewerRole);
		admin.getRoles().add(adminRole);
		basicUser.getRoles().add(basic);
		
		roleRepo.save(adminRole);
		roleRepo.save(reviewerRole);
		roleRepo.save(editorRole);
		roleRepo.save(chRole);
		roleRepo.save(basic);
		
		userRepo.save(admin);
		userRepo.save(editor);
		userRepo.save(reviewer);
		userRepo.save(cheifEditor);
		userRepo.save(basicUser);
		//userRepo.save(editor2);
		//userRepo.save(reviewer2);

	}
	
	private void initTermData() {
		Term term1 = Term.builder().termName("SCIENCE").build();
		Term term2 = Term.builder().termName("DATA MINING").build();
		Term term3 = Term.builder().termName("AI").build();
		Term term4 = Term.builder().termName("ICT").build();
		Term term5 = Term.builder().termName("OWASP").build();
		Term term6 = Term.builder().termName("NLP").build();
		Term term7 = Term.builder().termName("PCI DSS").build();
		Term term8 = Term.builder().termName("BUSINESS PROCESS").build();
		Term term9 = Term.builder().termName("CODE GENERATORS").build();
		Term term10 = Term.builder().termName("MDE").build();

		
		termRepo.save(term1);
		termRepo.save(term2);
		termRepo.save(term3);
		termRepo.save(term4);
		termRepo.save(term5);
		termRepo.save(term6);
		termRepo.save(term7);
		termRepo.save(term8);
		termRepo.save(term9);
		termRepo.save(term10);

	}

	private void initMagazines() {
		
		
		MyUser chiefEditor = userRepo.findByusername("editor");
		List<ScienceArea> scienceAreas = repo.findAllById(Arrays.asList(new Long[] {1l, 2l}));
		ScienceArea computerScience = scienceAreas.get(0);
		ScienceArea artificialIntelligence = scienceAreas.get(1);
		
		MyUser computerScienceEditor = userRepo.findByusername("editorDemo1");
		MyUser artificialInteligenceEditor = userRepo.findByusername("editorDemo2");
		
		MyUser computerScienceReviewer1 = userRepo.findByusername("reviewerDemo1");
		MyUser computerScienceReviewer2 = userRepo.findByusername("reviewerDemo2");
		MyUser artificialIntelligence1 = userRepo.findByusername("reviewerDemo3");

		
		Magazine magazine1 = Magazine.builder()
									.active(true)
									.username("4563-1232")
									.membershipPrice(1250f)
									.name("Computer science informer")
									.wayOfPayment(WayOfPayment.PAID_ACCESS)
									.chiefEditor(chiefEditor)
									.scienceAreas(new HashSet<ScienceArea>(scienceAreas))
									.build();
		
		Magazine persistedMagazine1 = magRepo.save(magazine1);
		
		MagazineEdition magazineEdition1 = MagazineEdition.builder()
													.magazineEditionPrice(100f)
													.publishingDate(new Date())
													.magazine(persistedMagazine1)
													.build();
		
		MagazineEdition magazineEdition2 = MagazineEdition.builder()
				.magazineEditionPrice(200f)
				.publishingDate(new Date(119, 11, 30))  //2019 godina
				.magazine(persistedMagazine1)
				.build();
		
		magEdRepo.save(magazineEdition1);
		magEdRepo.save(magazineEdition2);
		
		
		EditorReviewerByScienceArea editor1 = EditorReviewerByScienceArea.builder()
																		.editor(true)
																		.magazine(persistedMagazine1)
																		.scienceArea(computerScience)
																		.editorReviewer(computerScienceEditor)
																		.build();
		
		EditorReviewerByScienceArea editor2 = EditorReviewerByScienceArea.builder()
																		.editor(true)
																		.magazine(persistedMagazine1)
																		.scienceArea(artificialIntelligence)
																		.editorReviewer(artificialInteligenceEditor)
																		.build();
		
		EditorReviewerByScienceArea reviewer1 = EditorReviewerByScienceArea.builder()
																		.editor(false)
																		.magazine(persistedMagazine1)
																		.scienceArea(computerScience)
																		.editorReviewer(computerScienceReviewer1)
																		.build();
		
		EditorReviewerByScienceArea reviewer2 = EditorReviewerByScienceArea.builder()
																		.editor(false)
																		.magazine(persistedMagazine1)
																		.scienceArea(computerScience)
																		.editorReviewer(computerScienceReviewer2)
																		.build();
		
		EditorReviewerByScienceArea reviewer3 = EditorReviewerByScienceArea.builder()
																		.editor(false)
																		.magazine(persistedMagazine1)
																		.scienceArea(artificialIntelligence)
																		.editorReviewer(artificialIntelligence1)
																		.build();
		
		editorReviewerRepo.save(editor1);
		editorReviewerRepo.save(editor2);
		editorReviewerRepo.save(reviewer1);
		editorReviewerRepo.save(reviewer2);
		editorReviewerRepo.save(reviewer3);

		
		
	}
	
}
