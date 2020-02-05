package root.demo.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.repo.Magazine;
import root.demo.repository.MagazineRepository;

@Service
public class RetrieveMagazinePaymentType implements JavaDelegate {

	@Autowired
	private MagazineRepository magRepo;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub

		Long magazineId = (Long) execution.getVariable("select_magazine_id");
		Magazine magazine = magRepo.getOne(magazineId);
		
		System.out.println("MAGAZINE: " + magazine.getWayOfPayment().toString());
		execution.setVariable("payment_type", magazine.getWayOfPayment().toString());
		
		
		System.out.println("Provera");
		String s = (String) execution.getVariable("payment_type");
		System.out.println("S: " + s);
		
		System.out.println(".........");
	}

}
