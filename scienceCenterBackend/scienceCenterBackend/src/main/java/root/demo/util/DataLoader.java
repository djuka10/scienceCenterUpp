package root.demo.util;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import root.demo.model.repo.EditorByScienceArea;
import root.demo.model.repo.MyUser;
import root.demo.model.repo.Role;
import root.demo.model.repo.RoleName;
import root.demo.model.repo.ScienceArea;
import root.demo.repository.RoleRepository;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.UserRepository;


@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	private ScienceAreaRepository repo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		// createCardAccount();
		createScienceArea();
		createRoles();
		createUsers();
	}
	

	private void createScienceArea() {
		ScienceArea sbi = new ScienceArea(1l,"NO1");
		ScienceArea sbi2 = new ScienceArea(2l,"NO2");
		ScienceArea sbi3 = new ScienceArea(3l,"NO3");
		repo.save(sbi);
		repo.save(sbi2);
		repo.save(sbi3);
	}
	
	private void createRoles() {

	}
	
	private void createUsers() {
		Role adminRole = new Role(RoleName.ROLE_ADMIN);
		Role reviewerRole = new Role(RoleName.ROLE_REVIEWER);
		Role editorRole = new Role(RoleName.ROLE_EDITOR);
		Role chRole = new Role(RoleName.ROLE_CHIEF_EDITOR);
		Role basic = new Role(RoleName.ROLE_USER);
		
		MyUser admin = new MyUser(1l, "admin","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","vikac@gmail.com","Petar","Petrovic","Novi Sad", "Srbija","Titula",false,true,adminRole);
		MyUser editor = new MyUser(2l, "editor","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","vikac.parkic@gmail.com","Petar EDITOR","Petrovic","Novi Sad", "Srbija","Titula",false,true,editorRole);
		MyUser reviewer = new MyUser(3l, "reviewer","reviewer","parkic@gmail.com","Petar REVIEWER","Petrovic","Novi Sad", "Srbija","Titula",true,true,reviewerRole);
		MyUser cheifEditor = new MyUser(4l, "cheif","cheif","vik@gmail.com","Petar","Petrovic","Novi Sad", "Srbija","Titula",false,true,chRole);
		MyUser basicUser = new MyUser(5l, "user","user","viic@gmail.com","Petar","Petrovic","Novi Sad", "Srbija","Titula",false,true,basic);
		// TODO dodati ovde jos po dva editora i reviewer-a kako bismo mogli da testiramo njihovo dodavanje kao i dodavanje casopisa
		
		
		MyUser editor2 = new MyUser(6l, "editor2","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","parkc@gmail.com","Marko","Petrovic","Novi Sad", "Srbija","Titula",false,true,editorRole);
		MyUser reviewer2 = new MyUser(7l, "reviewer2","$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","rkic@gmail.com","Nikola","Petrovic","Novi Sad", "Srbija","Titula",true,true,reviewerRole);

		//EditorByScienceArea e = new EditorByScienceArea(1l,);
		
		editor.getRoles().add(editorRole);
		editor2.getRoles().add(editorRole);
		reviewer.getRoles().add(reviewerRole);
		reviewer2.getRoles().add(reviewerRole);
		admin.getRoles().add(adminRole);
		
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
		userRepo.save(editor2);
		userRepo.save(reviewer2);
		
		
		
	}

}
