package root.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


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
import root.demo.model.repo.ScienceArea;
import root.demo.repository.ScienceAreaRepository;
import root.demo.repository.UserRepository;
import root.demo.repository.UserScienceRepository;
import root.demo.request.LoginForm;
import root.demo.response.ErrorResponse;
import root.demo.response.JwtResponse;
import root.demo.security.JwtProvider;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private UserRepository userRepository;
	
	 @Autowired
	    AuthenticationManager authenticationManager;
	 @Autowired
	    PasswordEncoder encoder;

	    @Autowired
	    JwtProvider jwtProvider;
	
	 @PostMapping("/login")
	    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

	    	System.out.println("DFKLJF " + loginRequest.getEmail());
	    	
	    	Optional<MyUser> u = userRepository.findByMail(loginRequest.getEmail());
	    	
	    	if(u != null) {
	    		System.out.println("Postoji user");
	    		Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            loginRequest.getEmail(),
	                            loginRequest.getPassword()
	                    )
	            );

	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            System.out.println("Get auth: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	            String jwt = jwtProvider.generateJwtToken(authentication);
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            Optional<MyUser> user = userRepository.findByMail(loginRequest.getEmail());
	            
	            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), user.get().getId()));
	    	} else {
	    		System.out.println("Nije verifikovan");
	    		  return  ResponseEntity.ok(new ErrorResponse("Ne valja","Ne valja","Ne valja"));
	    	}
	    	
	        
	       
	    }
}
