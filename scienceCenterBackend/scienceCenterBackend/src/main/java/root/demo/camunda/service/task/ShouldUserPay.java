package root.demo.camunda.service.task;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.repo.Magazine;
import root.demo.model.repo.MyUser;
import root.demo.model.user.tx.Membership;
import root.demo.repository.MagazineRepository;
import root.demo.repository.MembershipRepository;
import root.demo.repository.UserRepository;



@Service
public class ShouldUserPay implements JavaDelegate {

	@Autowired
	private MagazineRepository magRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MembershipRepository memRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

		Long magazineId = (Long) execution.getVariable("select_magazine_id");
		Magazine magazine = magRepo.getOne(magazineId);
		
		Long userUsername = (Long) execution.getVariable("user");
		MyUser user = userRepo.getOne(userUsername);
		
		Membership membership = memRepo.findByMagazineAndEndAtBefore(magazine, new Date());
		//obrnuo sam true i false zbog testiranja lakseg
		boolean shouldPay = false;
		if( membership != null ) {
			shouldPay = true;
		}
		
		execution.setVariable("should_pay", shouldPay);
	}

}
