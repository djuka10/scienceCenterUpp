//package root.demo.services;
//
//import java.util.List;
//
//import org.camunda.bpm.engine.IdentityService;
//import org.camunda.bpm.engine.RepositoryService;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
//import org.camunda.bpm.engine.form.FormField;
//import org.camunda.bpm.engine.identity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import root.demo.model.FormSubmissionDto;
//import root.demo.repository.UserRepository;
//import root.demo.model.repo.*;
//
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//@Service
//public class ActivateMailService  implements JavaDelegate{
//
//	@Autowired
//	private JavaMailSender sender;
//	
//	@Autowired
//	private Environment env;
//	
//	@Override
//	public void execute(DelegateExecution execution) throws Exception {
//		// TODO Auto-generated method stub
//		String mail = (String) execution.getVariable("mail");
//		String proccesInstance = execution.getProcessInstanceId();
//		
//		String ret = sendVerMail(mail,proccesInstance);
//		
//		System.out.println("RET: " + ret);
//		
//		
//	}
//	
//	public String sendVerMail(String mail, String processInstance) throws MailException, InterruptedException, MessagingException {
//		MimeMessage mime = sender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(mime, false, "utf-8");
//
//		String link = "<html>" + "					  \r\n" + "					  <head>"
//				+ "					    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
//				+ "\r\n" + "					    <title>Welcome to airflights</title>"
//				+ "					  </head>\r\n" + "					  \r\n" + " 					  <body>"
//				+ "					<br><br><a href=\"http://localhost:4200/verification/" + processInstance
//				+ "\" style=\"display:block; padding:15px 25px; background-color:#0087D1; color:#ffffff; border-radius:3px; text-decoration:none;\">Verify Email Address</a>"
//				+ "</body></html>";
//
//		try {
//			mime.setContent(link, "text/html");
//			helper.setTo(mail);
//			/*
//			 * helper.setText("Pozdrav "+ user.getFirstName() + "\n Ovde ce " +
//			 * "kasnije biti aktivacioni link");
//			 */
//			helper.setSubject("Verifikacioni e-mail");
//			helper.setFrom(env.getProperty("spring.mail.username"));
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		sender.send(mime);
//
//		return "Mail je poslat";
//
//	}
//
//}
